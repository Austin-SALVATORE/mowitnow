package application;

import adapters.FileInputAdapter;
import config.LocalizationConfig;
import domain.Lawn;
import domain.Mower;
import domain.MowerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.InputPort;
import ports.OutputPort;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class MowerService {
    private static final Logger logger = LoggerFactory.getLogger(MowerService.class);
    private final Lawn lawn;
    private final InputPort inputPort;
    private final OutputPort outputPort;
    private final int threadCount;
    private final LocalizationConfig localization;

    public MowerService(Lawn lawn, InputPort inputPort, OutputPort outputPort,
                        int threadCount, LocalizationConfig localization) {
        this.lawn = lawn;
        this.inputPort = inputPort;
        this.outputPort = outputPort;
        this.threadCount = threadCount;
        this.localization = localization;
    }

    public void execute() {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        logger.info(localization.getMessage("service.execution.start"));
        try {
            List<MowerData> mowerDataList = inputPort.loadMowers();
            List<Callable<String>> tasks = mowerDataList.stream()
                    .map(mowerData -> (Callable<String>) () -> processMower(mowerData))
                    .collect(Collectors.toList());
            List<Future<String>> results = executorService.invokeAll(tasks);
            List<String> finalPositions = results.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            logger.error(localization.getMessage("service.mower.processing.error",e));
                            return localization.getMessage("service.mower.processing.error");
                        }
                    })
                    .collect(Collectors.toList());
            outputPort.writeResults(finalPositions);
            logger.info(localization.getMessage("service.execution.complete"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(localization.getMessage("service.execution.error"), e);
        } finally {
            executorService.shutdown();
        }
    }

    private String processMower(MowerData mowerData) {
        logger.info(localization.getMessage("service.mower.processing.start",
                mowerData.getX(), mowerData.getY(), mowerData.getOrientation()));
        Mower mower = new Mower(
                mowerData.getX(),
                mowerData.getY(),
                mowerData.getOrientation(),
                lawn
        );
        mower.executeInstructions(mowerData.getInstructions());
        String finalPosition = mower.getPosition();
        logger.info(localization.getMessage("service.mower.processing.complete", finalPosition));
        return finalPosition;
    }
}
package application;

import domain.Lawn;
import domain.Mower;
import domain.MowerData;
import ports.InputPort;
import ports.OutputPort;

import java.util.List;
import java.util.stream.Collectors;

public class MowerService {
    private final Lawn lawn;
    private final InputPort inputPort;
    private final OutputPort outputPort;

    public MowerService(Lawn lawn, InputPort inputPort, OutputPort outputPort) {
        this.lawn = lawn;
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    public void execute() {
        List<MowerData> mowerDataList = inputPort.loadMowers();
        List<String> results = mowerDataList.stream()
                .map(this::processMower)
                .collect(Collectors.toList());
        outputPort.writeResults(results);
    }

    private String processMower(MowerData mowerData) {
        Mower mower = new Mower(
                mowerData.getX(),
                mowerData.getY(),
                mowerData.getOrientation(),
                lawn
        );
        mower.executeInstructions(mowerData.getInstructions());
        return mower.getPosition();
    }
}
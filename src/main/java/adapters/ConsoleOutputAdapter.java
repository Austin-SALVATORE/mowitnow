package adapters;

import config.LocalizationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.OutputPort;

import java.util.List;

public class ConsoleOutputAdapter implements OutputPort {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutputAdapter.class);
    private final LocalizationConfig localization;

    public ConsoleOutputAdapter(LocalizationConfig localization) {
        this.localization = localization;
    }

    @Override
    public void writeResults(List<String> results) {
        for (String result : results) {
            String finalMessage = localization.getMessage("final.mower.position", result);
            logger.info(finalMessage);
        }
    }
}

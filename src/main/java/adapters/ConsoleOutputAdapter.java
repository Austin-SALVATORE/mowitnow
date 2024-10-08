package adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.OutputPort;

import java.util.List;

public class ConsoleOutputAdapter implements OutputPort {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutputAdapter.class);

    @Override
    public void writeResults(List<String> results) {
        for (String result : results) {
            logger.info("Mower final position: {}", result);
        }
    }
}

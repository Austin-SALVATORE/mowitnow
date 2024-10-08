package config;

import adapters.ConsoleOutputAdapter;
import adapters.FileInputAdapter;
import application.MowerService;
import domain.Lawn;
import ports.InputPort;
import ports.OutputPort;

public class MowerAppConfig {
    public MowerService getMowerService() {
        InputPort fileInput = new FileInputAdapter("input.txt");
        OutputPort consoleOutput = new ConsoleOutputAdapter();
        Lawn lawn = new Lawn(5, 5);

        return new MowerService(lawn, fileInput, consoleOutput);
    }
}

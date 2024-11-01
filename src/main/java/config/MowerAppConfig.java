package config;

import adapters.ConsoleOutputAdapter;
import adapters.FileInputAdapter;
import application.MowerService;
import domain.Lawn;
import ports.InputPort;
import ports.OutputPort;

import java.util.Locale;

public class MowerAppConfig {
    private final LocalizationConfig localization;

    public MowerAppConfig(LocalizationConfig localization) {
        this.localization = localization;
    }

    public MowerService getMowerService(String language) {
        AppConfig appConfig = new AppConfig();
        String inputFile = appConfig.getProperty("input.file");
        int maxX = appConfig.getIntProperty("lawn.maxX");
        int maxY = appConfig.getIntProperty("lawn.maxY");
        int threadCount = appConfig.getIntProperty("threads");
        InputPort fileInput = new FileInputAdapter(inputFile, localization);
        OutputPort consoleOutput = new ConsoleOutputAdapter(localization);
        Lawn lawn = new Lawn(maxX, maxY);
        return new MowerService(lawn, fileInput, consoleOutput, threadCount,localization);
    }
}

package config;

import adapters.ConsoleOutputAdapter;
import adapters.FileInputAdapter;
import application.MowerService;
import domain.Lawn;
import ports.InputPort;
import ports.OutputPort;

import java.util.Locale;

public class MowerAppConfig {
    public MowerService getMowerService(String language) {
        Locale locale = switch (language.toLowerCase()) {
            case "fr" -> Locale.FRENCH;
            default -> Locale.ENGLISH;
        };
        LocalizationConfig localization = new LocalizationConfig(locale);
        InputPort fileInput = new FileInputAdapter("input.txt", localization);
        OutputPort consoleOutput = new ConsoleOutputAdapter(localization);
        Lawn lawn = new Lawn(5, 5);

        return new MowerService(lawn, fileInput, consoleOutput);
    }
}

package config;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationConfig {

    private final ResourceBundle resourceBundle;

    public LocalizationConfig(Locale locale) {
        // Load the appropriate resource bundle based on the Locale
        this.resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    public String getMessage(String key, Object... params) {
        String message = resourceBundle.getString(key);
        if (params.length > 0) {
            return java.text.MessageFormat.format(message, params);
        }
        return message;
    }
}

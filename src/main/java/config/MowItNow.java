package config;

import application.MowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class MowItNow {
    private static final Logger logger = LoggerFactory.getLogger(MowItNow.class);
    public static void main(String[] args) {
        String language = "en";
        final LocalizationConfig localization;
        if (args.length > 0) {
            language = args[0].toLowerCase();
            if (!language.equals("en") && !language.equals("fr")) {
                logger.warn("Invalid language option: '{}'. Using default language: English.", language);
                language = "en";
            }
        }
        localization = new LocalizationConfig(Locale.forLanguageTag(language));

        try {
            logger.info(localization.getMessage("app.start", language));
            MowerAppConfig config = new MowerAppConfig();
            MowerService mowerService = config.getMowerService(language);
            mowerService.execute();
            logger.info(localization.getMessage("app.success"));
        } catch (Exception e) {
            logger.error(localization.getMessage("app.error"), e);
        }
    }
}

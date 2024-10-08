package config;

import application.MowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MowItNow {
    private static final Logger logger = LoggerFactory.getLogger(MowItNow.class);
    public static void main(String[] args) {
        String language = "en";
        if (args.length > 0) {
            language = args[0].toLowerCase();
            if (!language.equals("en") && !language.equals("fr")) {
                logger.error("Invalid language option. Using default language: English.");
                language = "en";
            }
        }

        try {
            MowerAppConfig config = new MowerAppConfig();
            MowerService mowerService = config.getMowerService(language);
            mowerService.execute();
        } catch (Exception e) {
            logger.error("An error occurred while running the application:{}", e.getMessage());
        }
    }
}

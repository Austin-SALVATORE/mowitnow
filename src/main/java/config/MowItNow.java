package config;

import application.MowerService;

public class MowItNow {
    public static void main(String[] args) {
        String language = "en";
        if (args.length > 0) {
            language = args[0];
        }
        MowerAppConfig config = new MowerAppConfig();
        MowerService mowerService = config.getMowerService(language);
        mowerService.execute();
    }
}

package config;

import application.MowerService;

public class MowItNow {
    public static void main(String[] args) {
        MowerAppConfig config = new MowerAppConfig();
        MowerService mowerService = config.getMowerService();
        mowerService.execute();
    }
}

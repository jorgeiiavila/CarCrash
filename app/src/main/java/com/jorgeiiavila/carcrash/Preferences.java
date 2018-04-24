package com.jorgeiiavila.carcrash;

public class Preferences {

    private static final String fileName = "carCrashPreferences";

    // Settings
    private static final String musicKey = "music";
    private static final String fxSoundsKey = "fxSounds";

    // Car Image
    private static final String carImageKey = "carImage";

    // Getters
    public String getFileName() {
        return fileName;
    }

    public String getMusicKey() {
        return musicKey;
    }

    public String getFxSoundsKey() {
        return fxSoundsKey;
    }

    public String getCarImageKey() {
        return carImageKey;
    }
}

package com.jorgeiiavila.carcrash;

public class Preferences {
    private String fileName = "carCrashPreferences";

    // Settings
    private String musicKey = "music";
    private String fxSoundsKey = "fxSounds";

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
}

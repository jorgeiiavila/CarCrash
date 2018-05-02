package com.jorgeiiavila.carcrash;

public class Preferences {

    // User score
    public final String highScore = "highScore";
    private final String fileName = "carCrashPreferences";
    // Settings
    private final String musicKey = "music";
    private final String fxSoundsKey = "fxSounds";
    // Car Image
    private final String carImageKey = "carImage";
    // First time playing
    private final String firstTimePlaying = "firstTimePlating";

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

    public String getFirstTimePlaying() {
        return firstTimePlaying;
    }
}

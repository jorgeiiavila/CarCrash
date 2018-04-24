package com.jorgeiiavila.carcrash;

import android.content.SharedPreferences;

public class Preferences {

    private String fileName = "carCrashPreferences";

    // Settings
    private String musicKey = "music";
    private String fxSoundsKey = "fxSounds";

    // Car Image
    private String carImageKey = "carImage";

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

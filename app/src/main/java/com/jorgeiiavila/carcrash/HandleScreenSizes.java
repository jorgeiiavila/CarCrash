package com.jorgeiiavila.carcrash;

/**
 * Created by jorge on 4/30/2018.
 */

public class HandleScreenSizes {
    public static int numOfEnemies(int screenWidth) {
        if (screenWidth >= 720 && screenWidth <= 800) {
            return 5;
        }

        if (screenWidth >= 1080 && screenWidth <= 1200) {
            return 8;
        }

        if (screenWidth >= 1440 && screenWidth <= 1600) {
            return 12;
        }
        return 5;
    }
}

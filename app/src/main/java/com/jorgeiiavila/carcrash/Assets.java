package com.jorgeiiavila.carcrash;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by jorge on 4/24/2018.
 */

public class Assets {
    public static Bitmap enemiesUp[];
    public static Bitmap enemiesDown[];

    public static void init(Resources resources) {
        enemiesUp = new Bitmap[3];
        enemiesDown = new Bitmap[3];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        // Init enemies up assets
        enemiesUp[0] = BitmapFactory.decodeResource(resources, R.drawable.police_white, options);
        enemiesUp[1] = BitmapFactory.decodeResource(resources, R.drawable.police_blue, options);
        enemiesUp[2] = BitmapFactory.decodeResource(resources, R.drawable.police_red, options);

        // Init enemies down assets
        enemiesDown[0] = BitmapFactory.decodeResource(resources, R.drawable.police_down_white, options);
        enemiesDown[1] = BitmapFactory.decodeResource(resources, R.drawable.police_down_blue, options);
        enemiesDown[2] = BitmapFactory.decodeResource(resources, R.drawable.police_down_red, options);
    }
}

package com.jorgeiiavila.carcrash;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by jorge on 4/9/2018.
 */

public class Player extends Item{

    int screenWidth; // width of the phone screen
    int screenHeight; // height of the phone screen
    private boolean touched; // Determines if there was a touch on the screen

    /**
     * Player constructor
     * @param bitmap image of the character
     * @param speed speed of the character
     */
    public Player(Bitmap bitmap, int speed) {
        super(bitmap, speed);
        this.height = bitmap.getHeight();
        this.width = bitmap.getWidth();
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        this.y = screenHeight / 2 - this.height / 2;
        this.x = screenWidth / 2 - this.width / 2;
    }

    /**
     * Draws the player character
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    /**
     * Updates player status
     */
    @Override
    public void update() {
        if (x < 0 || y < 0 || x + bitmap.getHeight() > screenWidth || y > screenHeight) {

        } else {

        }
    }
}

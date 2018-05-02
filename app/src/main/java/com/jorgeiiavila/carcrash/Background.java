package com.jorgeiiavila.carcrash;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by jorge on 4/9/2018.
 */

public class Background extends Item {

    private Bitmap background2; // second image for the infinite loop
    private int y2; // y position of the second image

    /**
     * Background contructor
     * @param bitmap image for the background
     * @param x position
     * @param y position
     * @param width of image
     * @param height of image
     * @param speed of floor
     */
    public Background(Bitmap bitmap, int x, int y, int width, int height, int speed) {
        super(bitmap, x, y, width, height, 0, speed);
        background2 = bitmap;
        y2 = -background2.getHeight();
    }

    /**
     * Draws background
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
        canvas.drawBitmap(background2, x, y2, null);
    }

    /**
     * Updates background
     */
    @Override
    public void update() {
        y += speedY;
        y2 += speedY;

        if (y > bitmap.getHeight()) {
            y = -bitmap.getHeight();
        }

        if (y2 > bitmap.getHeight()) {
            y2 = -background2.getHeight();
        }
    }
}

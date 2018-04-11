package com.jorgeiiavila.carcrash;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by jorge on 4/9/2018.
 */

public class Background extends Item {

    private Bitmap background2;
    private int y2;

    public Background(Bitmap bitmap, int x, int y, int width, int height, int speed) {
        super(bitmap, x, y, width, height, speed);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        background2 = Bitmap.createScaledBitmap(bitmap, width, height, false);
        y2 = -background2.getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
        canvas.drawBitmap(background2, x, y2, null);
    }

    @Override
    public void update() {
        y += speed;
        y2 += speed;

        if (y > bitmap.getHeight()) {
            y = -bitmap.getHeight();
        }

        if (y2 > bitmap.getHeight()) {
            y2 = -background2.getHeight();
        }
    }
}

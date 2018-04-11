package com.jorgeiiavila.carcrash;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by fernandosalazar on 4/10/18.
 */

public class Enemy extends Item {

    int screenWidth; // width of the phone screen
    int screenHeight; // height of the phone screen
    boolean goesUp; // Determines if the enemy goues up or down
    int initialSpeed;

    public Enemy(Bitmap bitmap, int speed) {
        super(bitmap, speed);
        this.initialSpeed = speed;
        this.height = bitmap.getHeight();
        this.width = bitmap.getWidth();
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        restoreEnemy();
    }

    public void restoreEnemy() {
        goesUp = getRandomBoolean();
        if (goesUp) {
            this.y = (int)(Math.random() * screenHeight) + screenHeight;
        } else {
            this.y = -(int)(Math.random() * screenHeight) - height;
            this.speed = this.initialSpeed * 2;
        }
        this.x = (int) (Math.random() * (screenWidth)) - this.width/2;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    @Override
    public void update() {
        if (goesUp) {
            y -= speed;
            if (y <= -this.height) {
                restoreEnemy();
            }
        } else {
            y += speed;
            if (y > screenHeight) {
                restoreEnemy();
            }
        }
    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}

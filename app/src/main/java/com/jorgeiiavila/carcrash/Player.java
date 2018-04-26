package com.jorgeiiavila.carcrash;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import static java.lang.Math.round;

/**
 * Created by jorge on 4/9/2018.
 */

public class Player extends Item {

    private boolean moved; // Determines if there was a touch on the screen
    private int screenX;
    private int direction;
    private int initialSpeed;

    /**
     * Player constructor
     *
     * @param bitmap image of the character
     * @param speed  speed of the character
     */
    public Player(Bitmap bitmap, int speed) {
        super(bitmap, speed);
        this.height = bitmap.getHeight();
        this.width = bitmap.getWidth();
        this.y = screenHeight / 2 - this.height / 2;
        this.x = screenWidth / 2 - this.width / 2;
        this.direction = 1;
        initialSpeed = speed;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    /**
     * Draws the player character
     *
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
        // Move the player
        if (moved) {
            // Check if player is onscreen
            if (screenX < screenWidth / 2) {
                if (x + ((int)(0.47*width)) > 0) {
                    if (direction > 0) {
                        resetSpeedAndDirection(-1);
                    }
                    setSpeed(getSpeed() + (int) round(screenWidth * (1.0 / 720.0)));
                    setX(getX() - getSpeed());
                } else {
                    setX(-width / 2);
                }
            } else {
                if (x + width - ((int)(0.47*width)) < screenWidth) {
                    if (direction < 0) {
                        resetSpeedAndDirection(1);
                    }
                    setSpeed(getSpeed() + (int) round(screenWidth * (1.0 / 720.0)));
                    setX(getX() + getSpeed());
                } else {
                    setX(screenWidth - width / 2);
                }
            }
        } else {
            resetSpeedAndDirection(1);
        }
    }

    private void resetSpeedAndDirection(int direction) {
        this.direction = direction;
        setSpeed(initialSpeed);
    }
}

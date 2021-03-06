package com.jorgeiiavila.carcrash;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import static java.lang.Math.round;

/**
 * Created by jorge on 4/9/2018.
 */

public class Player extends Item {

    private boolean moved; // Determines if there was a touch on the screen
    private int screenX; // X of the position touched by the user
    private int direction; // Direction in which the player is moving
    private int initialSpeed; // Original speedX
    private Bitmap immune; // Immune Bitmap
    private Bitmap damaged2; // damaged Bitmap
    private Bitmap damaged1; // damaged Bitmap
    private boolean immuneB; // Check if immunity is active
    private int lives;


    /**
     * Player constructor
     *
     * @param bitmap image of the character
     * @param speed  speedX of the character
     */
    public Player(Bitmap bitmap, Bitmap immune, Bitmap damaged2, Bitmap damaged1, int speed, int screenHeight) {
        super(bitmap, speed, 0, screenHeight);
        this.height = bitmap.getHeight();
        this.width = bitmap.getWidth();
        this.y = screenHeight / 2 - this.height / 2;
        this.x = screenWidth / 2 - this.width / 2;
        this.direction = 1;
        initialSpeed = this.speedX;
        this.immune = immune;
        this.damaged2 = damaged2;
        this.damaged1 = damaged1;
        immuneB = false;
        lives = 3;
    }

    /**
     * Set lives
     * @param lives
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Set immunity state
     *
     * @param immuneB
     */
    public void setImmuneB(boolean immuneB) {
        this.immuneB = immuneB;
    }

    /**
     * Set moved
     * @param moved set whether the player should be moved or not
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    /**
     * Set screenX
     * @param screenX x position of the touch of the user
     */
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
        if (immuneB) {
            canvas.drawBitmap(immune, x, y, null);
        } else {
            switch (lives) {
                case 2:
                    canvas.drawBitmap(damaged2, x, y, null);
                    break;
                case 1:
                    canvas.drawBitmap(damaged1, x, y, null);
                    break;
                default:
                    canvas.drawBitmap(bitmap, x, y, null);
                    break;
            }
        }
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
                if (x >  (int)(screenWidth*6.0/412.0)) {
                    if (direction > 0) {
                        resetSpeedAndDirection(-1);
                    }
                    setSpeedX(getSpeedX() + (int) round(screenWidth * (1.0 / 720.0)));
                    setX(getX() - getSpeedX());
                } else {
                    setX((int)(screenWidth*6.0/412.0));
                }
            } else {
                if (x + width < screenWidth - (int)(screenWidth*21.0/412.0)) {
                    if (direction < 0) {
                        resetSpeedAndDirection(1);
                    }
                    setSpeedX(getSpeedX() + (int) round(screenWidth * (1.0 / 720.0)));
                    setX(getX() + getSpeedX());
                } else {
                    setX(screenWidth - width - (int)(screenWidth*21.0/412.0));
                }
            }
        } else {
            resetSpeedAndDirection(1);
        }
    }

    /**
     * Reset speedX and direction if the player
     * @param direction Direction in which the player is moving
     */
    private void resetSpeedAndDirection(int direction) {
        this.direction = direction;
        setSpeedX(initialSpeed);
    }
}

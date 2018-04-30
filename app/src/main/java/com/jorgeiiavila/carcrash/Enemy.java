package com.jorgeiiavila.carcrash;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import static java.lang.Math.round;

/**
 * Created by fernandosalazar on 4/10/18.
 */

public class Enemy extends Item {

    boolean goesUp; // Determines if the enemy goes up or down
    int initialSpeed; // Its the base speed of the
    Animation animationDown; // Animation of the enemy down
    Animation animationUp; // Animation of the enemy up

    /**
     * Constructor of the enemy
     * @param bitmapUp image of the character going up
     * @param bitmapDown image of the character going down
     * @param speed speed of the enemy
     */
    public Enemy(Bitmap bitmapUp, Bitmap bitmapDown, int speed) {
        super(bitmapUp, speed);
        this.initialSpeed = speed;
        this.height = bitmap.getHeight();
        this.width = bitmap.getWidth();
        restoreEnemy();
        if (!goesUp) {
            this.bitmap = bitmapDown;
        }
        animationDown = new Animation(Assets.enemiesDown, 100);
        animationUp = new Animation(Assets.enemiesUp, 100);
        this.speed = (int) round(screenHeight * (speed / 1280.0));
    }

    /**
     * Gets a random boolean
     *
     * @return boolean variable
     */
    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    /**
     * Sets the enemy to an starting position
     */
    public void restoreEnemy() {
        this.goesUp = getRandomBoolean();
        if (this.goesUp) {
            this.y = (int)(Math.random() * screenHeight) + screenHeight;
            this.speed = this.initialSpeed;
        } else {
            this.y = -(int)(Math.random() * screenHeight) - height;
            this.speed = this.initialSpeed * 2;
        }
        this.x = (int) (Math.random() * (screenWidth)) - this.width/2;
    }

    /**
     * Draws enemy
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        if (goesUp) {
            canvas.drawBitmap(animationUp.getCurrentFrame(), x, y, null);
        } else {
            canvas.drawBitmap(animationDown.getCurrentFrame(), x, y, null);
        }
    }

    /**
     * Ticks enemy
     */
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
        animationUp.tick();
        animationDown.tick();
    }

    /**
     * Return close bounds
     */
    public Rect getCloseBounds() {
        return new Rect(x + ((int) (0.47 * width)), y + ((int) (0.44 * width)), x + width - ((int) (0.47 * width)), y + height - ((int) (0.44 * width)));
    }
}

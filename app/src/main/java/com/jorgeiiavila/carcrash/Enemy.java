package com.jorgeiiavila.carcrash;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by fernandosalazar on 4/10/18.
 */

public class Enemy extends Item {

    boolean goesUp; // Determines if the enemy goes up or down
    int initialSpeed; // Its the base speedY of the enemy
    Animation animationDown; // Animation of the enemy down
    Animation animationUp; // Animation of the enemy up

    /**
     * Constructor of the enemy
     * @param bitmapUp image of the character going up
     * @param bitmapDown image of the character going down
     * @param speed speedY of the enemy
     */
    public Enemy(Bitmap bitmapUp, Bitmap bitmapDown, int speed) {
        super(bitmapUp, 0, speed);
        this.height = bitmap.getHeight();
        this.width = bitmap.getWidth();
        this.initialSpeed = speedY;
        restoreEnemy();
        if (!goesUp) {
            this.bitmap = bitmapDown;
        }
        animationDown = new Animation(Assets.enemiesDown, 1000);
        animationUp = new Animation(Assets.enemiesUp, 1000);
    }

    public int getInitialSpeed() {
        return initialSpeed;
    }

    public void setInitialSpeed(int initialSpeed) {
        this.initialSpeed = initialSpeed;
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
            this.speedY = this.initialSpeed;
        } else {
            this.y = -(int)(Math.random() * screenHeight) - height;
            this.speedY = this.initialSpeed * 2;
        }
        this.x = (int) (Math.random() * (screenWidth - width - (screenWidth*6/732))) + (screenWidth*6/732);
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
            y -= speedY;
            if (y <= -this.height) {
                restoreEnemy();
            }
        } else {
            y += speedY;
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
        int distX = (int) (screenWidth * 25.0 / 720.0);
        int distY = (int) (screenHeight * 25.0 / 1198.0);
        return new Rect(x - distX, y - distY, x + width + distX, y + width + distY);
    }
}

package com.jorgeiiavila.carcrash;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Powerup extends Item {

    boolean isActive;   //
    int powerUpType;    // Power up type
    // 1: Extra Life
    // 2: Points x2
    // 3: Immunity
    Bitmap extraLifeBitmap; // Extra life image
    Bitmap pointsX2Bitmap; // x2 image
    Bitmap immunityBitmap; // immunity image

    /**
     * Constructor
     *
     * @param bitmap    Bitmap of the power up
     * @param speed     // Speed of the power up
     * @param resources // Resources to access pictures
     */
    public Powerup(Bitmap bitmap, int speed, Resources resources, int screenHeight) {
        super(bitmap, 0, speed, screenHeight);
        this.height = bitmap.getHeight();
        this.width = bitmap.getWidth();
        this.isActive = false;
        this.x = (int) (Math.random() * (screenWidth)) - this.width / 2;
        this.y = -(int) (Math.random() * screenHeight) - height;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        this.extraLifeBitmap = BitmapFactory.decodeResource(resources, R.drawable.powerup_extra_life, options);
        this.pointsX2Bitmap = BitmapFactory.decodeResource(resources, R.drawable.powerup_x2, options);
        this.immunityBitmap = BitmapFactory.decodeResource(resources, R.drawable.powerup_inmune, options);
    }

    /**
     * Set if the power up is active and reset the position
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
        x = -100;
        y = -100;
    }

    /**
     * Get Power Up type
     * @return powerUpType
     */
    public int getPowerUpType() {
        return powerUpType;
    }

    /**
     * Generate Power Up
     */
    public void generatePowerUp() {
        this.x = (int) (Math.random() * (screenWidth)) - this.width / 2;
        this.y = -(int) (Math.random() * screenHeight) - height;
        Random random = new Random();
        this.powerUpType = random.nextInt((3 - 1) + 1) + 1;
        switch (this.powerUpType) {
            case 1:
                setBitmap(extraLifeBitmap);
                break;
            case 2:
                setBitmap(pointsX2Bitmap);
                break;
            case 3:
                setBitmap(immunityBitmap);
                break;
            default:
                break;
        }
        this.isActive = true;
    }

    /**
     * Get bounds of the power up
     * @return Rect
     */
    @Override
    public Rect getBounds() {
        return new Rect(x, y, x + width, y + height);
    }

    /**
     * Draw on canvas
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        if (isActive) {
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    /**
     * Update position
     */
    @Override
    public void update() {
        y += speedY;
        if (this.x >= screenHeight) {
            this.isActive = false;
        }
    }
}

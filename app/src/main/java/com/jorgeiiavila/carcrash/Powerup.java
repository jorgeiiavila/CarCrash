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
    Bitmap extraLifeBitmap;
    Bitmap pointsX2Bitmap;
    Bitmap immunityBitmap;

    public Powerup(Bitmap bitmap, int speed, Resources resources) {
        super(bitmap, speed);
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        x = -100;
        y = -100;
    }

    public int getPowerUpType() {
        return powerUpType;
    }

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

    @Override
    public Rect getBounds() {
        return new Rect(x, y, x + width, y + height);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isActive) {
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    @Override
    public void update() {
        y += speed;
        if (this.x >= screenHeight) {
            this.isActive = false;
        }
    }
}

package com.jorgeiiavila.carcrash;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by jorge on 4/9/2018.
 */

public abstract class Item {

    protected Bitmap bitmap;
    protected int x; // x position of item
    protected int y; // y position of item
    protected int width; // Width of the object
    protected int height; // Height of the object
    protected int speed; // speed of the item

    public Item(Bitmap bitmap, int x, int y, int width, int height, int speed) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();
}

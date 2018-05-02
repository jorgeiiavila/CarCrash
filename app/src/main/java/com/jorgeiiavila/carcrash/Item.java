package com.jorgeiiavila.carcrash;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import static java.lang.Math.round;

/**
 * Created by jorge on 4/9/2018.
 */

public abstract class Item {

    protected Bitmap bitmap; // Bitmap if the item
    protected Rect bounds; // Bounds of the item
    protected int x; // x position of item
    protected int y; // y position of item
    protected int width; // Width of the object
    protected int height; // Height of the object
    protected int speedX; // speedX of the item
    protected int speedY; // speedY of the item
    protected int screenWidth; // Width of the device's screen
    protected int screenHeight; // Height of the device's screen

    /**
     * Item full constructor
     * @param bitmap
     * @param x
     * @param y
     * @param width
     * @param height
     * @param speedX
     */
    public Item(Bitmap bitmap, int x, int y, int width, int height, int speedX, int speedY) {
        this.bitmap = bitmap;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = height;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speedX = (int) round(screenWidth * (speedX / 720.0));
        this.speedY = (int) round(screenHeight * (speedY / 1280.0));
        this.bounds = new Rect(x,y,x+width,y+height);
    }

    /**
     * Item partial constructor
     * @param bitmap image of the character
     */
    public Item(Bitmap bitmap, int speedX, int speedY, int screenHeight) {
        this.bitmap = bitmap;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        this.screenHeight = screenHeight;
        this.speedX = (int) round(screenWidth * (speedX / 720.0));
        this.speedY = (int) round(screenHeight * (speedY / 1198.0));
    }

    /**
     * Get the x position of the item
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x position of the player
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Return speedX
     * @return speedX of the item
     */
    public int getSpeedX() {
        return speedX;
    }

    /**
     * Set speedX of the the item
     * @param speedX speedX of the item
     */
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    /**
     * Get speed in Y
     *
     * @return speedY
     */
    public int getSpeedY() {
        return speedY;
    }

    /**
     * Set speed in Y
     *
     * @param speedY speedY
     */
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    /**
     * Get the item character bounds
     * @return
     */
    public Rect getBounds() {
        return new Rect(x, y, x + width, y + height);
    }

    /**
     * Checks if the item intrsecter the param
     * @param bounds of another item
     * @return if the items intersected
     */
    public boolean intersects(Rect bounds) {
        return this.getBounds().intersect(bounds);
    }

    /**
     * Draws the item
     * @param canvas
     */
    public abstract void draw(Canvas canvas);

    /**
     * Ticks item
     */
    public abstract void update();
}

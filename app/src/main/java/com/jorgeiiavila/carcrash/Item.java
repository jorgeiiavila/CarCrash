package com.jorgeiiavila.carcrash;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by jorge on 4/9/2018.
 */

public abstract class Item {

    protected Bitmap bitmap;
    protected Rect bounds;
    protected int x; // x position of item
    protected int y; // y position of item
    protected int width; // Width of the object
    protected int height; // Height of the object
    protected int speed; // speed of the item
    protected int acceleration; // acceleration of the item

    /**
     * Item full constructor
     * @param bitmap
     * @param x
     * @param y
     * @param width
     * @param height
     * @param speed
     */
    public Item(Bitmap bitmap, int x, int y, int width, int height, int speed) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.bounds = new Rect(x,y,x+width,y+height);
    }

    /**
     * Item partial constructor
     * @param bitmap image of the character
     * @param speed speed of the character
     */
    public Item(Bitmap bitmap, int speed) {
        this.bitmap = bitmap;
        this.speed = speed;
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

    /**
     * Return acceleration
     * @return acceleration
     */
    public int getAcceleration() {
        return acceleration;
    }

    /**
     * Set acceleration
     * @param acceleration acceleration of the item
     */
    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Return speed
     * @return speed of the item
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Set speed of the the item
     * @param speed speed of the item
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Get the item character bounds
     * @return
     */
    public Rect getBounds () {
        return new Rect(x+((int)(0.47*width)),y+((int)(0.44*width)),x+width-((int)(0.47*width)),y+height-((int)(0.44*width)));
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

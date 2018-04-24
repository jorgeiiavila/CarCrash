package com.jorgeiiavila.carcrash;

import android.graphics.Bitmap;

public class Animation {
    private int speed; // Speed of every frame
    private int index; // Get the index of the next frame to paint
    private long lastTime; // Save the previous time of the animation
    private long timer; // Accumulate the time of the animation
    private Bitmap[] frames; // Store every image/frame

    public Animation(Bitmap[] frames, int speed) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public Bitmap getCurrentFrame() {
        return frames[index];
    }

    public void tick() {
        // Accumulating the time of previous tick on this one
        timer += System.currentTimeMillis();
        // Updating the lastTime for the next tick;
        lastTime = System.currentTimeMillis();
        // Check the timer to increase the index
        if (timer > speed) {
            index++;
            timer = 0;
            // Check the index to not get out of bounds
            if (index >= frames.length) {
                index = 0;
            }
        }
    }
}

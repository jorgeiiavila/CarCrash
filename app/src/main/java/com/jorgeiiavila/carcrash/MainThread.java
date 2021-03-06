package com.jorgeiiavila.carcrash;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by jorge on 4/9/2018.
 */

public class MainThread extends Thread {

    public static Canvas canvas; // Variable where everything is drawn;
    private SurfaceHolder surfaceHolder; // Contains the canvas
    private GameView gameView; //
    private boolean running; // Check if the thread is running
    private int targetFPS; // FPS wanted
    private double averageFPS; // Average FPS
    private int frameCount; // Count the frames

    /**
     * Constructor of the class
     * @param surfaceHolder
     * @param gameView
     */
    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        targetFPS = 60;
    }

    /**
     * Get the current frame count
     *
     * @return frameCount
     */
    public int getFrameCount() {
        return frameCount;
    }

    /**
     * Handles the running of the thread
     */
    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        frameCount = 0;
        long targetTime = 1000 / targetFPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {       }
            finally {
                if (canvas != null)            {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                sleep(waitTime);
            } catch (Exception e) {}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == targetFPS)        {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }

    /**
     * Sets if thread is running
     * @param isRunning
     */
    public void setRunning(boolean isRunning) {
        this.running = isRunning;
    }
}

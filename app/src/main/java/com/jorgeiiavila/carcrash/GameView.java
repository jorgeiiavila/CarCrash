package com.jorgeiiavila.carcrash;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by jorge on 4/9/2018.
 */

class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private com.jorgeiiavila.carcrash.MainThread thread; // Thread
    private Player player; // Player object
    private Background background; // Background object
    private boolean move; // Indicates if the player must move
    private int x; // Position in x where the user clicked
    int screenWidth; // Device screen width
    int screenHeight; // Device screen height


    /**
     * Contructor of GameView
     * @param context Context of the activity
     */
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new com.jorgeiiavila.carcrash.MainThread(getHolder(), this);
        setFocusable(true);
    }

    /**
     * Creates the surface
     * @param surfaceHolder Object containing the surface
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.car), screenWidth / 2 - 100 / 2, screenHeight - 100 - 200, 100, 147, 2);
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.highway), 0, 0, 720, 1280, 10);
    }

    /**
     * Handles changes on surface
     * @param surfaceHolder
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    /**
     * Handles when the surface is destroyed
     * @param surfaceHolder Object containing the surface
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    /**
     * Update the objects values
     */
    public void update() {
        // Move the player if asked
        if (move) {
            if (x < screenWidth / 2) {
                player.setX(player.getX() - 10);
            } else {
                player.setX(player.getX() + 10);
            }
        }

        // Update values
        player.update();
        background.update();
    }

    /**
     * Draw on screen
     * @param canvas Canvas object that allows the draw on screen
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        background.draw(canvas);
        player.draw(canvas);
    }

    /**
     * Handles the touch events
     * @param event Gives information about the touches the user does
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX();

        // Check if the user clicked the screen or released it
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            move = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            move = false;
        }

        return true;
    }
}

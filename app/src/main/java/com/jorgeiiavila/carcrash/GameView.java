package com.jorgeiiavila.carcrash;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import static android.graphics.Bitmap.Config.ARGB_8888;

/**
 * Created by jorge on 4/9/2018.
 */

class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private com.jorgeiiavila.carcrash.MainThread thread; // Thread
    private Player player; // Player object
    private ArrayList<Enemy> enemies; // Enemies arraylist
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

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_red, options);
        player = new Player(playerBitmap, 6);
        enemies = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.police_blue, options), BitmapFactory.decodeResource(getResources(), R.drawable.police_down_blue, options), 10));
        }
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background, options), 0, 0, screenWidth, screenHeight, 10);
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

        // Moves the player if asked
        if (move) {
            if (x < screenWidth / 2) {
                player.setX(player.getX() - 10);
            } else {
                player.setX(player.getX() + 10);
            }
        }

        // Checks player collision with enemies
        for (int i = 0; i < enemies.size(); i++) {
            if (player.intersects(enemies.get(i).getBounds())) {
                enemies.get(i).restoreEnemy();
            }
        }

        // Updates values
        player.update();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }
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
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(canvas);
        }
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

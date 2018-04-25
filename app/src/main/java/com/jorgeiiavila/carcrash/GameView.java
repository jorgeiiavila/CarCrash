package com.jorgeiiavila.carcrash;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by jorge on 4/9/2018.
 */

class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private com.jorgeiiavila.carcrash.MainThread thread; // Thread

    private SoundPool soundPool;

    private Player player; // Player object
    private ArrayList<Enemy> enemies; // Enemies arraylist
    private Background background; // Background object
    private boolean move; // Indicates if the player must move
    private int x; // Position in x where the user clicked
    private int screenWidth; // Device screen width
    private int screenHeight; // Device screen height
    private int score; // Score of the game
    private boolean paused; // Controls if the game is paused
    private double lives; // Lives of the player
    private boolean gameOver; // Check if game is over

    // Power Ups variables
    private Powerup powerup;
    private int timeToDisplayPowerUp;
    private int limitToDisplayPowerUp = 1200;
    private boolean powerupPointsX2;
    private boolean powerupImmunity;
    private int powerupActiveTime;
    private int powerupLimitTime = 800;

    /**
     * Contructor of GameView
     *
     * @param context Context of the activity
     */
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        Assets.init(getResources());
        // Audio configuration for fx sound
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

    }

    /**
     * Creates the surface
     *
     * @param surfaceHolder Object containing the surface
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        resetGame();
    }

    /**
     * Restart the game, restoring everything to the initial state
     */
    public void resetGame() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_red, options);
        player = new Player(playerBitmap, 6);
        enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.police_blue, options), BitmapFactory.decodeResource(getResources(), R.drawable.police_down_blue, options), 10));
        }
        powerup = new Powerup(BitmapFactory.decodeResource(getResources(), R.drawable.powerup_extra_life, options), 10, getResources());
        this.powerupPointsX2 = false;
        this.powerupImmunity = false;
        this.timeToDisplayPowerUp = 0;
        this.powerupActiveTime = 0;
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background, options), 0, 0, screenWidth, screenHeight, 10);
        lives = 3;
        gameOver = false;
        score = 0;
    }

    /**
     * Handles changes on surface
     *
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
     *
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
        if (!gameOver) {
            if (thread.getFrameCount() == 30) {
                if (powerupPointsX2) {
                    score+=2;
                } else {
                    score++;
                }
            }

            // Power Up
            timeToDisplayPowerUp++;
            if (timeToDisplayPowerUp >= limitToDisplayPowerUp) {
                timeToDisplayPowerUp = 0;
                powerup.generatePowerUp();
            }

            if (powerupPointsX2 || powerupImmunity) {
                powerupActiveTime++;
                if (powerupActiveTime >= powerupLimitTime) {
                    powerupActiveTime = 0;
                    powerupPointsX2 = false;
                    powerupImmunity = false;
                }
            }

            // Moves the player if asked
            if (move) {
                player.setMoved(true);
                player.setScreenX(x);
            } else {
                player.setMoved(false);
            }

            // Checks player collision with enemies
            for (int i = 0; i < enemies.size(); i++) {
                if (player.intersects(enemies.get(i).getBounds()) && !this.powerupImmunity) {
                    enemies.get(i).restoreEnemy();
                    lives--;
                }
            }

            if (player.intersects(powerup.getBounds())) {
                switch (powerup.getPowerUpType()) {
                    case 1:
                        if (lives < 3) {
                            lives++;
                        }
                        Log.v("POWER UP","Extra life");
                        break;
                    case 2:
                        this.powerupPointsX2 = true;
                        Log.v("POWER UP","Points x2");
                        break;
                    case 3:
                        this.powerupImmunity = true;
                        Log.v("POWER UP","Immunity");
                        break;
                    default:
                        break;
                }
                powerup.setActive(false);
            }

            // Updates values
            player.update();
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();
            }
            powerup.update();
            background.update();

            // Check if game ended
            if (lives == 0) {
                gameOver = true;
            }
        } else {
            if (move) {
                resetGame();
            }
        }
    }

    /**
     * Draw on screen
     *
     * @param canvas Canvas object that allows the draw on screen
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        background.draw(canvas);
        powerup.draw(canvas);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(canvas);
        }
        player.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create("Calibri", Typeface.BOLD));
        canvas.drawText("SCORE " + score, screenWidth - 50, 80, paint);
        paint.setAlpha(50);
        if (powerupPointsX2) {
            canvas.drawText("POINTS X2", screenWidth - 50, 160, paint);
        }
        if (powerupImmunity) {
            canvas.drawText("IMMUNITY", screenWidth - 50, 240, paint);
        }
    }

    /**
     * Handles the touch events
     *
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

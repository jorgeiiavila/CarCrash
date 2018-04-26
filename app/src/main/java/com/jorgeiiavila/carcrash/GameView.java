package com.jorgeiiavila.carcrash;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by jorge on 4/9/2018.
 */

class GameView extends SurfaceView implements SurfaceHolder.Callback {

    Bitmap pauseBitmap; // Pause image
    Bitmap button; // Pause/GameOver menu buttons
    SharedPreferences sharedPreferences; // to save the high score
    // Buttons positions
    double xPosPause;
    double yPosPause;
    // Game Activity
    Activity activity;
    private com.jorgeiiavila.carcrash.MainThread thread; // Thread
    private Player player; // Player object
    private ArrayList<Enemy> enemies; // Enemies arraylist
    private Background background; // Background object
    private boolean move; // Indicates if the player must move
    private int x; // Position in x where the user clicked
    private int y; // Position in y where the user clicked
    private int screenWidth; // Device screen width
    private int screenHeight; // Device screen height
    private int score; // Score of the game
    private boolean paused; // Controls if the game is paused
    private int lives; // Lives of the player
    private boolean gameOver; // Check if game is over
    // Power Ups variables
    private Powerup powerup;
    private int timeToDisplayPowerUp;
    private int limitToDisplayPowerUp = 1200;
    private boolean powerupPointsX2;
    private boolean powerupImmunity;
    private int powerupActiveTime;
    private int powerupLimitTime = 800;
    // Buttons positions
    private int exitX;
    private int exitY;
    private int retryX;
    private int retryY;

    /**
     * Contructor of GameView
     *
     * @param context Context of the activity
     */
    public GameView(Context context, Activity activity) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        Assets.init(getResources());
        Preferences preferences = new Preferences();
        sharedPreferences = context.getSharedPreferences(preferences.getFileName(), Context.MODE_PRIVATE);
        this.activity = activity;
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
     * Load car according to user settings
     */
    public void loadCar() {
        Preferences preferences = new Preferences();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap playerBitmap = null;
        int car = sharedPreferences.getInt(preferences.getCarImageKey(), 0);
        switch (car) {
            case 0:
                playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_red, options);
                break;
            case 1:
                playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_black, options);
                break;
            case 2:
                playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_orange, options);
                break;
            default:
                break;
        }
        player = new Player(playerBitmap, 6);
    }

    /**
     * Restart the game, restoring everything to the initial state
     */
    public void resetGame() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        loadCar();
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
        paused = false;
        button = BitmapFactory.decodeResource(getResources(), R.drawable.exit_cta, options);
        pauseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resume_cta, options);
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
            if (!paused) {
                if (thread.getFrameCount() == 30) {
                    if (powerupPointsX2) {
                        score += 2;
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
                            break;
                        case 2:
                            this.powerupPointsX2 = true;
                            break;
                        case 3:
                            this.powerupImmunity = true;
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
            }
        } else {
            Preferences preferences = new Preferences();
            int highScore = sharedPreferences.getInt(preferences.highScore, 0);
            if (score > highScore) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(preferences.highScore, score);
                editor.commit();
            }
        }
    }

    /**
     * Draw button on the center of the screen
     *
     * @param canvas
     */
    public void drawButtonCenter(Canvas canvas, int x, int y, String type) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        if (type == "EXIT") {
            button = BitmapFactory.decodeResource(getResources(), R.drawable.exit_cta, options);
            exitX = x - button.getWidth() / 2;
            exitY = y;
            canvas.drawBitmap(button, exitX, exitY, null);
        } else {
            button = BitmapFactory.decodeResource(getResources(), R.drawable.retry_cta, options);
            retryX = x - button.getWidth() / 2;
            retryY = y;
            canvas.drawBitmap(button, retryX, retryY, null);
        }
    }

    /**
     * Draw pause button
     *
     * @param canvas
     */
    public void drawPauseButton(Canvas canvas) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        if (paused) {
            pauseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resume_cta, options);
        } else {
            pauseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pause_cta, options);
        }
        xPosPause = screenWidth * (24.0 * 100.0 / 412.0) / 100.0;
        yPosPause = screenHeight * (24.0 * 100.0 / 732.0) / 100.0;
        canvas.drawBitmap(pauseBitmap, (int) xPosPause, (int) yPosPause, null);
    }

    /**
     * Draw lives onscreen
     *
     * @param canvas
     */
    public void drawLives(Canvas canvas) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap livesBm = null;
        switch (lives) {
            case 3:
                livesBm = BitmapFactory.decodeResource(getResources(), R.drawable.lives_3, options);
                break;
            case 2:
                livesBm = BitmapFactory.decodeResource(getResources(), R.drawable.lives_2, options);
                break;
            case 1:
                livesBm = BitmapFactory.decodeResource(getResources(), R.drawable.lives_1, options);
                break;
            case 0:
                livesBm = BitmapFactory.decodeResource(getResources(), R.drawable.lives_0, options);
            default:
                break;
        }
        double posX = screenWidth - livesBm.getWidth() - screenWidth * (24.0 / 412.0);
        double posY = screenHeight * (28.0 * 100.0 / 732.0) / 100.0;
        canvas.drawBitmap(livesBm, (int) posX, (int) posY + 50 + 10, null);
    }

    /**
     * Draw the pause menu
     * @param canvas
     */
    public void drawPauseMenu(Canvas canvas) {
        // Draw pause background
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap pauseBg = BitmapFactory.decodeResource(getResources(), R.drawable.background_shade, options);
        canvas.drawBitmap(pauseBg, 0, 0, null);
        drawScore(canvas); // Draw Score

        // Draw resume button
        drawPauseButton(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(80);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Calibri", Typeface.BOLD));
        canvas.drawText("PAUSED GAME", screenWidth / 2, screenHeight / 2, paint);

        // Draw exit button
        drawButtonCenter(canvas, screenWidth / 2, screenHeight / 2 + (screenHeight * 90 / 720), "EXIT");

    }

    /**
     * Draw score of the player
     * @param canvas
     */
    public void drawScore(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.create("Calibri", Typeface.BOLD));
        double posY = screenHeight * (28.0 * 100.0 / 732.0) / 100.0;
        canvas.drawText("SCORE " + score, screenWidth - 50, (int) posY + 50, paint);
    }

    /**
     * Draw game over screen
     *
     * @param canvas
     */
    public void drawGameOver(Canvas canvas) {
        // Draw Game Over background
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap gameOverBg = BitmapFactory.decodeResource(getResources(), R.drawable.background_shade, options);
        canvas.drawBitmap(gameOverBg, 0, 0, null);
        drawScore(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(80);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Calibri", Typeface.BOLD));
        canvas.drawText("GAME OVER", screenWidth / 2, screenHeight / 2, paint);
        drawButtonCenter(canvas, screenWidth / 2, screenHeight / 2 + (int) (screenHeight * 90.0 / 720.0), "EXIT");
        drawButtonCenter(canvas, screenWidth / 2, screenHeight / 2 + (int) (screenHeight * 90.0 / 720.0) + button.getHeight() + (int) (screenHeight * 10.0 / 720.0), "RETRY");
    }

    /**
     * Draw on screen
     *
     * @param canvas Canvas object that allows the draw on screen
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Game elements draw
        background.draw(canvas);
        powerup.draw(canvas);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(canvas);
        }
        player.draw(canvas);

        // check if the game is over, paused or on going, and draw things accordingly
        if (gameOver) {
            drawGameOver(canvas);
        } else if (paused) {
            drawPauseMenu(canvas);
        } else {
            drawScore(canvas);
            drawPauseButton(canvas);
            drawLives(canvas);
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
        y = (int) event.getY();

        // Check if the user clicked the screen or released it
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Check if pause/resume was touched
            if (x >= xPosPause && xPosPause <= xPosPause + pauseBitmap.getWidth() &&
                    y >= yPosPause && y <= yPosPause + pauseBitmap.getHeight()) {
                paused = !paused;
            } else if ((paused || gameOver) && x >= exitX && x <= exitX + button.getWidth() &&
                    y >= exitY && y <= exitY + button.getHeight()) {
                activity.finish();

            } else if (gameOver && x >= retryX && x <= retryX + button.getWidth() &&
                    y >= retryY && y <= retryY + button.getHeight()) {
                resetGame();
            } else {
                move = true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            move = false;
        }

        return true;
    }
}

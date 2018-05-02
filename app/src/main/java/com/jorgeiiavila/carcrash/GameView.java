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
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by jorge on 4/9/2018.
 */

class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final int updateTime = 500; // Update score every half second
    Bitmap pauseBitmap; // Pause image
    Bitmap resumeBitmap; // Resume image
    Bitmap button; // Pause/GameOver menu buttons
    Bitmap enemyBitmapUp; // Enemy Up Bitmap
    Bitmap enemyBitmapDown; // Enemy Down Bitmap
    SharedPreferences sharedPreferences; // to save the high score
    // Buttons positions
    double xPosPause; // Pause/Resume x
    double yPosPause; // Pause/Resume y
    // Game Activity
    Activity activity; // to end the activity
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
    private int scorePosY; // Score position in Y
    // Power Ups variables
    private Powerup powerup; // Power Up object
    private int timeToDisplayPowerUp; // Tells when to display power up
    private int limitToDisplayPowerUp = 800; // Limit to display powerup
    private boolean powerupPointsX2; // Check if power up is on
    private boolean powerupImmunity; // Check if power up is on
    private int powerupActiveTime; // Check if power up is on
    private int powerupLimitTime = 300; // Power Up time limit
    // Buttons positions
    private int exitX; // Exit button x
    private int exitY; // Exit button y
    private int retryX; // Retry button x
    private int retryY; // Retry button y
    // Bitmap
    private BitmapFactory.Options options; // Bitmap options
    private Bitmap livesBm3; // Three lives bitmap
    private Bitmap livesBm2; // Two lives bitmap
    private Bitmap livesBm1; // One live bitmap
    private Bitmap livesBm0; // No lives bitmap
    private Paint paint; // Paint
    private Paint paint2; // Paint2
    private Bitmap bg; // Pause/GameOver background
    private Bitmap plus10; // +10 Bitmap
    private Bitmap doublePoints; // Double points Bitmap
    private Bitmap damagedCar2; // Damaged car
    private Bitmap damagedCar1; // Damaged car
    private int doublePointsX; // X position of double points
    private int doublePointsY; // Y position of double points
    // Positions
    private int livesPosX; // Lives image position in x
    private int livesPosY; // Lives image position in y
    private int currentUpdateTime;
    private long lastTime; // Last time checked
    private boolean extra;
    private int enemyClose;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate;
    private boolean showPlus10;
    private long plusTenTime;

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
        options = new BitmapFactory.Options();
        options.inScaled = false;
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.background_shade, options);
        livesBm3 = BitmapFactory.decodeResource(getResources(), R.drawable.lives_3, options);
        livesBm2 = BitmapFactory.decodeResource(getResources(), R.drawable.lives_2, options);
        livesBm1 = BitmapFactory.decodeResource(getResources(), R.drawable.lives_1, options);
        livesBm0 = BitmapFactory.decodeResource(getResources(), R.drawable.lives_0, options);
        doublePoints = BitmapFactory.decodeResource(getResources(), R.drawable.double_points, options);
        damagedCar2 = BitmapFactory.decodeResource(getResources(), R.drawable.player_black_2, options);
        damagedCar1 = BitmapFactory.decodeResource(getResources(), R.drawable.player_black_3, options);
        scorePosY = (int) (screenHeight * (28.0 / 732.0)) + 50;
        livesPosX = (int) (screenWidth - livesBm3.getWidth() - screenWidth * (24.0 / 412.0));
        livesPosY = scorePosY + (int) (screenHeight * (10.0 / 732.0));
        doublePointsX = screenWidth / 2 - doublePoints.getWidth() / 2;
        doublePointsY = screenHeight - doublePoints.getHeight();
        resumeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resume_cta, options);
        pauseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pause_cta, options);
        xPosPause = screenWidth * (24.0 / 412.0);
        yPosPause = screenHeight * (24.0 / 732.0);
        plus10 = BitmapFactory.decodeResource(getResources(), R.drawable.plus10, options);
        initPaint();
        resetGame();
    }

    /**
     * Load car according to user settings
     */
    public void loadCar() {
        Preferences preferences = new Preferences();
        Bitmap playerBitmap;
        Bitmap immuneCar; // Immune car Bitmap
        int car = sharedPreferences.getInt(preferences.getCarImageKey(), 0);
        switch (car) {
            case 0:
                playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_red, options);
                immuneCar = BitmapFactory.decodeResource(getResources(), R.drawable.immune_player_red, options);
                break;
            case 1:
                playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_black, options);
                immuneCar = BitmapFactory.decodeResource(getResources(), R.drawable.immune_player_black, options);
                break;
            case 2:
                playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_orange, options);
                immuneCar = BitmapFactory.decodeResource(getResources(), R.drawable.immune_player_orange, options);
                break;
            default:
                playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_red, options);
                immuneCar = BitmapFactory.decodeResource(getResources(), R.drawable.immune_player_red, options);
                break;
        }
        player = new Player(playerBitmap, immuneCar, damagedCar2, damagedCar1, 6);
    }

    /**
     * Restart the game, restoring everything to the initial state
     */
    public void resetGame() {
        loadCar();
        enemyBitmapUp = BitmapFactory.decodeResource(getResources(), R.drawable.police_red, options);
        enemyBitmapDown = BitmapFactory.decodeResource(getResources(), R.drawable.police_down_red, options);
        enemies = new ArrayList<>();
        int numOfEnemies = HandleScreenSizes.numOfEnemies(screenWidth);
        for (int i = 0; i < numOfEnemies; i++) {
            enemies.add(new Enemy(enemyBitmapUp, enemyBitmapDown, 10));
        }
        powerup = new Powerup(BitmapFactory.decodeResource(getResources(), R.drawable.powerup_extra_life, options), 10, getResources());
        this.powerupPointsX2 = false;
        this.powerupImmunity = false;
        this.timeToDisplayPowerUp = 0;
        this.powerupActiveTime = 0;
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.game_background, options), 0, 0, screenWidth, screenHeight, 10);
        lives = 3;
        gameOver = false;
        score = 0;
        paused = false;
        button = BitmapFactory.decodeResource(getResources(), R.drawable.exit_cta, options);
        lastTime = System.currentTimeMillis();
        extra = false;
//        senSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
//        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();
        currentUpdateTime = updateTime;
        showPlus10 = false;
        plusTenTime = System.currentTimeMillis();
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
        if (!gameOver) { // Check if game is over
            if (!paused) { // Check if game is paused
                // Update score
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTime >= currentUpdateTime) {
                    score++;
                    lastTime = currentTime;
                }

                // Power Up
                timeToDisplayPowerUp++;
                if (timeToDisplayPowerUp >= limitToDisplayPowerUp) {
                    timeToDisplayPowerUp = 0;
                    powerup.generatePowerUp();
                }

                // Turn off the power ups
                if (powerupPointsX2 || powerupImmunity) {
                    powerupActiveTime++;
                    if (powerupActiveTime >= powerupLimitTime) {
                        powerupActiveTime = 0;
                        if (powerupPointsX2) {
                            currentUpdateTime = updateTime;
                        }
                        powerupPointsX2 = false;
                        powerupImmunity = false;
                        player.setImmuneB(false);
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
                        if (extra) {
                            extra = false;
                        }
                    } else if (!extra && player.intersects(enemies.get(i).getCloseBounds())) {
                        // Activate extra if player is very close to an enemy
                        extra = true;
                        enemyClose = i;
                    }
                }

                // Check if player earned the extra
                if (extra && !player.intersects(enemies.get(enemyClose).getCloseBounds())) {
                    score += 10;
                    extra = false;
                    showPlus10 = true;
                    plusTenTime = System.currentTimeMillis();
                }

                if (showPlus10) {
                    if (System.currentTimeMillis() - plusTenTime >= 1000) {
                        showPlus10 = false;
                    }
                }

                // Check player intersection with the power ups
                if (player.intersects(powerup.getBounds())) {
                    switch (powerup.getPowerUpType()) {
                        case 1:
                            if (lives < 3) {
                                lives++;
                            }
                            break;
                        case 2:
                            this.powerupPointsX2 = true;
                            currentUpdateTime = currentUpdateTime / 2;
                            break;
                        case 3:
                            this.powerupImmunity = true;
                            player.setImmuneB(true);
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

                // Add more enemies
//                if (score % 100 == 0 && enemies.size() <= 7) {
//                    enemies.add(new Enemy(enemyBitmapUp, enemyBitmapDown, 10));
//                }
            }
        } else { // Update highest score when game is over
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
     * Init paint object
     */
    void initPaint() {
        paint = new Paint();
        paint2 = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(screenWidth * 50 / 720);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Roboto", Typeface.BOLD));
        paint2.setColor(Color.WHITE);
        paint2.setTextSize(screenWidth * 50 / 720);
        paint2.setTextAlign(Paint.Align.RIGHT);
        paint2.setTypeface(Typeface.create("Roboto", Typeface.BOLD));
    }

    public void drawDoublePoints(Canvas canvas) {
        canvas.drawBitmap(doublePoints, doublePointsX, doublePointsY, null);
    }

    public void drawPlusTen(Canvas canvas) {
        if (showPlus10) {
            canvas.drawBitmap(plus10, screenWidth / 2 - plus10.getWidth() / 2, screenHeight / 4, null);
        }
    }

    /**
     * Draw button on the center of the screen
     *
     * @param canvas
     */
    public void drawButtonCenter(Canvas canvas, int x, int y, String type) {
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
        if (paused) {
            canvas.drawBitmap(resumeBitmap, (int) xPosPause, (int) yPosPause, null);
        } else {
            canvas.drawBitmap(pauseBitmap, (int) xPosPause, (int) yPosPause, null);
        }
    }

    /**
     * Draw lives onscreen
     *
     * @param canvas
     */
    public void drawLives(Canvas canvas) {
        switch (lives) {
            case 3:
                canvas.drawBitmap(livesBm3, livesPosX, livesPosY, null);
                break;
            case 2:
                canvas.drawBitmap(livesBm2, livesPosX, livesPosY, null);
                break;
            case 1:
                canvas.drawBitmap(livesBm1, livesPosX, livesPosY, null);
                break;
            case 0:
                canvas.drawBitmap(livesBm0, livesPosX, livesPosY, null);
            default:
                break;
        }
    }

    /**
     * Draw the pause menu
     * @param canvas
     */
    public void drawPauseMenu(Canvas canvas) {
        // Draw pause background
        canvas.drawBitmap(bg, 0, 0, null);
        drawScore(canvas); // Draw Score

        // Draw resume button
        drawPauseButton(canvas);
        canvas.drawText("PAUSED GAME", screenWidth / 2, screenHeight / 2, paint);

        // Draw exit button
        drawButtonCenter(canvas, screenWidth / 2, screenHeight / 2 + (screenHeight * 90 / 720), "EXIT");
    }

    /**
     * Draw score of the player
     * @param canvas
     */
    public void drawScore(Canvas canvas) {
        canvas.drawText("SCORE " + score, screenWidth - 50, scorePosY, paint2);
    }

    /**
     * Draw game over screen
     *
     * @param canvas
     */
    public void drawGameOver(Canvas canvas) {
        // Draw Game Over background
        canvas.drawBitmap(bg, 0, 0, null);
        drawScore(canvas);
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
            drawPauseButton(canvas);
            drawLives(canvas);
            drawScore(canvas);
            drawPlusTen(canvas);
            if (powerupPointsX2) {
                drawDoublePoints(canvas);
            }
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
//
//    @Override
//    public void onSensorChanged(SensorEvent sensorEvent) {
////        Sensor mySensor = sensorEvent.sensor;
////
////        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
////            float x = sensorEvent.values[0];
////            float y = sensorEvent.values[1];
////            float z = sensorEvent.values[2];
////
////            long curTime = System.currentTimeMillis();
////
////            if ((curTime - lastU
/// pdate) > 300) {
////                lastUpdate = curTime;
////
////                player.setScreenX((int) x);
////            }
////        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i) {
//
//    }
}

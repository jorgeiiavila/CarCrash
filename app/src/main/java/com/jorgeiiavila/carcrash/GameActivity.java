package com.jorgeiiavila.carcrash;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    private MediaPlayer backgroundMusic;
    private Boolean music;

    private Preferences preferences = new Preferences();
    private SharedPreferences sharedPreferences;

    /**
     * Runs Game View in this Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Removes status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Instantiate SharedPreferences
        sharedPreferences = getSharedPreferences(preferences.getFileName(), MODE_PRIVATE);

        // Get preferences
        this.music = sharedPreferences.getBoolean(preferences.getMusicKey(), true);

        // Start background music
        if (music) {
            backgroundMusic = MediaPlayer.create(GameActivity.this, R.raw.background_music);
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(10.0f, 3.0f);
            backgroundMusic.start();
        }

        // Runs game
        setContentView(new GameView(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop background music
        if (music) {
            backgroundMusic.stop();
        }

    }
}

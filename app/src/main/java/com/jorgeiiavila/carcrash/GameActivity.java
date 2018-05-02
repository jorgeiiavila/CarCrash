package com.jorgeiiavila.carcrash;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class GameActivity extends Activity {

    private MediaPlayer backgroundMusic;
    private Boolean music;

    private Preferences preferences = new Preferences();
    private SharedPreferences sharedPreferences;

    /**
     * Runs Game View in this Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable immersive mode
        int mUIFlag = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView()
                .setSystemUiVisibility(mUIFlag);
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
        setContentView(new GameView(this, this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop background music
        if (music) {
            backgroundMusic.stop();
        }

    }

    public Activity getActivity() {
        return this;
    }
}

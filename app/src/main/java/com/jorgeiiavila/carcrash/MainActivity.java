package com.jorgeiiavila.carcrash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    // Declaring buttons variables
    private Button playBtn; // play button
    private Button settingsBtn; // settings button
    private Button changeCarBtn; // change car button

    /**
     * Creates main activity and listen for clicks on buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Removes status bar
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Relating declared buttons to xml buttons
        this.playBtn = findViewById(R.id.mainPlayBtn);
        this.settingsBtn = findViewById(R.id.mainSettingsBtn);
        this.changeCarBtn = findViewById(R.id.mainChangeCarBtn);

        // Play button listener
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences preferences = new Preferences();
                SharedPreferences sharedPreferences = getSharedPreferences(preferences.getFileName(), MODE_PRIVATE);
                boolean hadPlayedBefore = sharedPreferences.getBoolean(preferences.getFirstTimePlaying(), false);
                // Creates and execute relation between activities
                if (hadPlayedBefore) {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    MainActivity.this.startActivity(intent);
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(preferences.getFirstTimePlaying(), true);
                    editor.apply();
                    // Go to instructions
                    Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        });

        // Settings button listener
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creates and execute relation between activities
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        // Change Car button listener
        changeCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChangeCarActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Enable immersive mode
        int mUIFlag = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView()
                .setSystemUiVisibility(mUIFlag);

        // High Score Text View
        TextView highScore = findViewById(R.id.mainScoreTextView);
        Preferences preferences = new Preferences();
        SharedPreferences sharedPreferences = getSharedPreferences(preferences.getFileName(), MODE_PRIVATE);
        highScore.setText(sharedPreferences.getInt(preferences.highScore, 0) + "");

        // Car image
        ImageView carImage = findViewById(R.id.mainCarImg);
        int currentCar = sharedPreferences.getInt(preferences.getCarImageKey(), 0);
        switch (currentCar) {
            case 0:
                carImage.setImageDrawable(getDrawable(R.drawable.player_red_pretty));
                break;
            case 1:
                carImage.setImageDrawable(getDrawable(R.drawable.player_black_pretty));
                break;
            case 2:
                carImage.setImageDrawable(getDrawable(R.drawable.player_orange_pretty));
                break;
            default:
                break;
        }
    }
}

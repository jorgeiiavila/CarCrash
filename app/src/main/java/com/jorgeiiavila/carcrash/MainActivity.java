package com.jorgeiiavila.carcrash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Relating declared buttons to xml buttons
        this.playBtn = findViewById(R.id.mainPlayBtn);
        this.settingsBtn = findViewById(R.id.mainSettingsBtn);
        this.changeCarBtn = findViewById(R.id.mainChangeCarBtn);

        // Play button listener
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creates and execute relation between activities
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                MainActivity.this.startActivity(intent);
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
        // High Score Text View
        TextView highScore = findViewById(R.id.mainScoreTextView);
        Preferences preferences = new Preferences();
        SharedPreferences sharedPreferences = getSharedPreferences(preferences.getFileName(), MODE_PRIVATE);
        highScore.setText(sharedPreferences.getInt(preferences.highScore, 0) + "");
    }
}

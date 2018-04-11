package com.jorgeiiavila.carcrash;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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
    }

}

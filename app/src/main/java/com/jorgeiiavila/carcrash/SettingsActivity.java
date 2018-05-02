package com.jorgeiiavila.carcrash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class SettingsActivity extends Activity {

    private Button backBtn;
    private Button creditsBtn;
    private CheckBox musicCheckBox;
    private CheckBox fxSoundsCheckBox;

    private Preferences preferences = new Preferences();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable immersive mode
        int mUIFlag = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView()
                .setSystemUiVisibility(mUIFlag);

        setContentView(R.layout.activity_settings);

        // Instantiate SharedPreferences
        sharedPreferences = getSharedPreferences(preferences.getFileName(), MODE_PRIVATE);

        // Relating declared buttons to xml buttons
        this.backBtn = findViewById(R.id.settingsBackBtn);
        this.creditsBtn = findViewById(R.id.settings_credits_btn);
        this.musicCheckBox = findViewById(R.id.musicCheckBox);
        this.fxSoundsCheckBox = findViewById(R.id.fxSoundsCheckBox);

        // Setting music and fx sounds to preferences values
        this.musicCheckBox.setChecked(sharedPreferences.getBoolean(preferences.getMusicKey(), true));
        this.fxSoundsCheckBox.setChecked(sharedPreferences.getBoolean(preferences.getFxSoundsKey(), true));

        // Back button listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Credits button listener
        creditsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, CreditsActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        // CheckBoxes listeners
        musicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveSetting(preferences.getMusicKey(), b);
            }
        });
        fxSoundsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveSetting(preferences.getFxSoundsKey(), b);
            }
        });

    }

    // Updates de Shared Preferences Values
    private void saveSetting(String settingKey, Boolean newValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(settingKey, newValue);
        editor.apply();
    }

}

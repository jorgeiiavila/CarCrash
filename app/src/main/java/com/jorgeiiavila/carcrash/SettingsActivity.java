package com.jorgeiiavila.carcrash;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class SettingsActivity extends Activity {

    private Button backBtn;
    private CheckBox musicCheckBox;
    private CheckBox fxSoundsCheckBox;

    private Preferences preferences = new Preferences();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Removes status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);

        // Relating declared buttons to xml buttons
        this.backBtn = findViewById(R.id.settingsBackBtn);
        this.musicCheckBox = findViewById(R.id.musicCheckBox);
        this.fxSoundsCheckBox = findViewById(R.id.fxSoundsCheckBox);

        // Instantiate SharedPreferences
        sharedPreferences = getSharedPreferences(preferences.getFileName(), MODE_PRIVATE);

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

    private void saveSetting(String settingKey, Boolean newValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(settingKey, newValue);
        editor.apply();
    }

}

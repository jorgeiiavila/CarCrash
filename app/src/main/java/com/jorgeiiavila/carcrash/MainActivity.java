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
    private Button playBtn;
    private Button settingsBtn;
    private Button changeCarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Relating declared buttons to xml buttons
        this.playBtn = findViewById(R.id.playBtn);
        this.settingsBtn = findViewById(R.id.settingsBtn);
        this.changeCarBtn = findViewById(R.id.changeCarBtn);

        // Play button listener
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // C
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

}

package com.jorgeiiavila.carcrash;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstructionsActivity extends Activity {

    private Button skipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable immersive mode
        int mUIFlag = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView()
                .setSystemUiVisibility(mUIFlag);

        setContentView(R.layout.activity_instructions);

        this.skipBtn = findViewById(R.id.instructions_skip_btn);

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionsActivity.this, GameActivity.class);
                InstructionsActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}

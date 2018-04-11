package com.jorgeiiavila.carcrash;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;

import java.util.ArrayList;

public class ChangeCarActivity extends Activity {

    private Button backBtn;
    private ArrayList<Integer> playerIdCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Removes status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_change_car);

        // Initiate Variables
        playerIdCars = new ArrayList<>();

        // Creating posible options
        playerIdCars.add(R.drawable.player_red);
        playerIdCars.add(R.drawable.police);

        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.changeCarRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ChangeCarAdapter(playerIdCars));

        // Relating declared buttons to xml buttons
        this.backBtn = findViewById(R.id.changeCarBackBtn);

        // Back button listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}

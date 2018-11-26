package com.afeka.liadk.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private int mWidth;
    private int mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Bundle level = intent.getBundleExtra(MainActivity.LEVEL_MESSAGE);
        if (level != null) {

            mWidth = level.getInt(MainActivity.WIDTH);
            mHeight = level.getInt(MainActivity.HEIGHT);

            TextView textView = findViewById(R.id.textView2);
            textView.setText(String.valueOf(mWidth));
        }
    }
}

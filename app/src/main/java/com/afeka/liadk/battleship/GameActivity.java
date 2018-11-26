package com.afeka.liadk.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private int mWidth;
    private int mHeight;
    private int mNumberOfShips;
    private GridView mPlayerBoard;
    private GridView mComputerBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Bundle level = intent.getBundleExtra(MainActivity.LEVEL_MESSAGE);

        if (level != null) {
            mWidth = level.getInt(MainActivity.WIDTH);
            mHeight = level.getInt(MainActivity.HEIGHT);
            mNumberOfShips = level.getInt(MainActivity.NUMBER_OF_SHIPS);
        }
        mPlayerBoard = findViewById(R.id.playerBoard);
        mComputerBoard = findViewById(R.id.computerBoard);
    }

    private void changeTurn(){

    }
}

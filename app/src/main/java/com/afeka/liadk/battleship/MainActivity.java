package com.afeka.liadk.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.afeka.liadk.battleship.Logic.GameInterface;

public class MainActivity extends AppCompatActivity implements GameInterface {

    final static String LEVEL_MESSAGE = "LEVEL.MESSAGE";
    final static String WIDTH = "WIDTH";
    final static String HEIGHT = "HEIGHT";
    final static String NUMBER_OF_SHIPS = "SHIPS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLevelButtonClicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle level = new Bundle();
        if (view.getId() == R.id.button_easy) {
            level.putInt(WIDTH, widthEasy);
            level.putInt(HEIGHT, heightEasy);
            level.putInt(NUMBER_OF_SHIPS, numberOfShipsEasy);
        } else {
            if (view.getId() == R.id.button_medium) {
                level.putInt(WIDTH, widthMedium);
                level.putInt(HEIGHT, heightMedium);
                level.putInt(NUMBER_OF_SHIPS, numberOfShipsMedium);
            } else {
                level.putInt(WIDTH, widthHard);
                level.putInt(HEIGHT, heightHard);
                level.putInt(NUMBER_OF_SHIPS, numberOfShipsHard);
            }
        }
        intent.putExtra(LEVEL_MESSAGE, level);
        startActivity(intent);
    }
}

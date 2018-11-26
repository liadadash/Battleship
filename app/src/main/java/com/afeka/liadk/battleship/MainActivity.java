package com.afeka.liadk.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements GameInterface {

    final static String LEVEL_MESSAGE = "LEVEL.MESSAGE";
    final static String WIDTH = "WIDTH";
    final static String HEIGHT = "HEIGHT";

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
        } else {
            if (view.getId() == R.id.button_medium) {
                level.putInt(WIDTH, widthMedium);
                level.putInt(HEIGHT, heightMedium);
            } else {
                level.putInt(WIDTH, widthHard);
                level.putInt(HEIGHT, heightHard);
            }
        }
        intent.putExtra(LEVEL_MESSAGE, level);
        startActivity(intent);
    }
}

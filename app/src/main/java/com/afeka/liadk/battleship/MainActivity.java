package com.afeka.liadk.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.afeka.liadk.battleship.Logic.GameSettingsInterface;

public class MainActivity extends AppCompatActivity {

    final static String LEVEL_MESSAGE = "LEVEL";
    final static String LEVEL_CHOOSEN = "CHOOSEN LEVEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLevelButtonClicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle bundleLevel = new Bundle();
        if (view.getId() == R.id.button_easy) {
            bundleLevel.putSerializable(LEVEL_CHOOSEN, GameSettingsInterface.Level.Easy);
        } else {
            if (view.getId() == R.id.button_medium) {
                bundleLevel.putSerializable(LEVEL_CHOOSEN, GameSettingsInterface.Level.Medium);
            } else {
                bundleLevel.putSerializable(LEVEL_CHOOSEN, GameSettingsInterface.Level.Hard);
            }
        }
        intent.putExtra(LEVEL_MESSAGE, bundleLevel);
        startActivity(intent);
    }
}

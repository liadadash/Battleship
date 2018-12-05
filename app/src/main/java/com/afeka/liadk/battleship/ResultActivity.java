package com.afeka.liadk.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private Bundle mLevelBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        Bundle state = intent.getBundleExtra(GameActivity.GAME_STATUS);
        if (state != null) {
            String winner = state.getString(GameActivity.WHO_WIN);
            if (winner != null) {
                TextView whoWin = (TextView) findViewById(R.id.result);
                whoWin.setText(winner);
                if (winner.compareTo(getResources().getString(R.string.win)) == 0)
                    ((ImageView) findViewById(R.id.blood)).setVisibility(View.INVISIBLE);
                else
                    ((ImageView) findViewById(R.id.ship)).setVisibility(View.INVISIBLE);
            }
        }
        mLevelBundle = intent.getBundleExtra(MainActivity.LEVEL_MESSAGE);
    }

    public void createNewGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void tryAgainGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(MainActivity.LEVEL_MESSAGE, mLevelBundle);
        startActivity(intent);
        finish();
    }
}

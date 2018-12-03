package com.afeka.liadk.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        Bundle state = intent.getBundleExtra(GameActivity.WINNER);
        if (state != null) {
            String winner = state.getString(GameActivity.WINNER);
            if (winner != null) {
                TextView whoWin = (TextView) findViewById(R.id.result);
                whoWin.setText(winner);
                if (winner.compareTo(getResources().getString(R.string.win)) == 0)
                    ((ImageView) findViewById(R.id.blood)).setVisibility(View.INVISIBLE);
                else
                    ((ImageView) findViewById(R.id.ship)).setVisibility(View.INVISIBLE);
            }
        }
    }

    public void createNewGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

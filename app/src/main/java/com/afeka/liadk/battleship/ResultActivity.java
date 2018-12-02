package com.afeka.liadk.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afeka.liadk.battleship.Logic.GameSettingsInterface;

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
            }
        }
    }

    public void createNewGame(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

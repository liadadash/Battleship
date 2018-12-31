package com.afeka.liadk.battleship;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afeka.liadk.battleship.Logic.GameSettingsInterface;

public class ResultActivity extends AppCompatActivity {

    private Bundle mLevelBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        Bundle state = intent.getBundleExtra(GameActivity.GAME_STATUS);
        AnimationDrawable animationDrawable = null;
        if (state != null) {
            String winner = state.getString(GameActivity.WHO_WIN);
            if (winner != null) {
                TextView whoWin = (TextView) findViewById(R.id.result);
                whoWin.setText(winner);
                if (winner.compareTo(getResources().getString(R.string.win)) == 0) {
                    animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient__list_win, null);
                    findViewById(R.id.loseResult).setVisibility(View.INVISIBLE);
                } else {
                    animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient__list_lose, null);
                    findViewById(R.id.winResult).setVisibility(View.INVISIBLE);
                }
                ((RelativeLayout) findViewById(R.id.resultLayout)).setBackground(animationDrawable);
                animationDrawable.setEnterFadeDuration(1000);
                animationDrawable.setExitFadeDuration(2000);
                animationDrawable.start();
            }
        }
        mLevelBundle = intent.getBundleExtra(GameSettingsInterface.LEVEL_MESSAGE);

    }

    public void createNewGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void tryAgainGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameSettingsInterface.LEVEL_MESSAGE, mLevelBundle);
        startActivity(intent);
        finish();
    }
}

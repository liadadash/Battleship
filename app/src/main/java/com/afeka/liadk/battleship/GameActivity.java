package com.afeka.liadk.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

import com.afeka.liadk.battleship.Logic.Game;
import com.afeka.liadk.battleship.Logic.GameSettingsInterface;

public class GameActivity extends AppCompatActivity implements GameSettingsInterface {

    private Game mGame;
    private TextView turn;
    private GridView mPlayerBoard;
    private GridView mComputerBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Bundle level = intent.getBundleExtra(MainActivity.LEVEL_MESSAGE);

        if (level != null) {
            Level choosenLevel = (Level) level.getSerializable(MainActivity.LEVEL_CHOOSEN);
            if (choosenLevel != null) {
                mGame = new Game(choosenLevel);
            }
        }
        turn = (TextView)findViewById(R.id.playerTurn);
        turn.setText(R.string.your_turn);
        mPlayerBoard = findViewById(R.id.playerBoard);
        mPlayerBoard.setAdapter(new TileAdapter(getApplicationContext(), mGame.getPlayerBoard()));
        mPlayerBoard.setNumColumns(mGame.getNumColumns());
//        mComputerBoard = findViewById(R.id.computerBoard);
//        mComputerBoard.setAdapter(new TileAdapter(getApplicationContext(), mGame.getCoumputerBoard()));
//        mComputerBoard.setNumColumns(mGame.getNumColumns());
    }
}

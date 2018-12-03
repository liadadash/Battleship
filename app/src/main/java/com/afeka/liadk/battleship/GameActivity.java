package com.afeka.liadk.battleship;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afeka.liadk.battleship.Logic.ComputerPlayer;
import com.afeka.liadk.battleship.Logic.Game;
import com.afeka.liadk.battleship.Logic.GameSettingsInterface;

public class GameActivity extends AppCompatActivity implements GameSettingsInterface {

    final static String WINNER = "WINNER";

    private Game mGame;
    private TextView turn;
    private GridView mPlayerBoard;
    private GridView mComputerBoard;
    private ComputerPlayer mComputerPlayer;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final Intent intentResult = new Intent(this, ResultActivity.class);
        final Bundle bundleWinner = new Bundle();
        Intent intent = getIntent();
        Bundle level = intent.getBundleExtra(MainActivity.LEVEL_MESSAGE);
        if (level != null) {
            Level choosenLevel = (Level) level.getSerializable(MainActivity.LEVEL_CHOOSEN);
            if (choosenLevel != null) {
                mProgressBar = ((ProgressBar) findViewById(R.id.progressBar));
                turn = (TextView) findViewById(R.id.playerTurn);
                turn.setText(R.string.your_turn);
                mProgressBar.setVisibility(View.INVISIBLE);
                mGame = new Game(choosenLevel);
                mPlayerBoard = findViewById(R.id.playerBoard);
                mComputerPlayer = new ComputerPlayer(choosenLevel, mGame.getPlayerBoard());
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                mPlayerBoard.setAdapter(new TileAdapter(getApplicationContext(), mGame.getPlayerBoard(), width / (choosenLevel.getWidth() * 2), height / (choosenLevel.getHeight() * 4), Game.Player.HumanPlayer));
                mPlayerBoard.setNumColumns(mGame.getNumColumns());
                mComputerBoard = findViewById(R.id.computerBoard);
                mComputerBoard.setAdapter(new TileAdapter(getApplicationContext(), mGame.getCoumputerBoard(), (int) (width / (choosenLevel.getWidth() * 1.2)), height / (choosenLevel.getHeight() * 3), Game.Player.ComputerPlayer));
                mComputerBoard.setNumColumns(mGame.getNumColumns());
                mComputerBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int posotion, long id) {
                        if (mGame.getCurrentPlayer() == Game.Player.HumanPlayer) {
                            boolean playerHit = mGame.getCoumputerBoard().attackTheBoard(posotion);
                            if (playerHit) {
                                ((TileAdapter) mComputerBoard.getAdapter()).notifyDataSetChanged();
                                mProgressBar.setVisibility(View.VISIBLE);
                                turn.setText(R.string.computer_turn);
                                mGame.changeTurn();
                                if (mGame.getCoumputerBoard().checkWinner()) {
                                    bundleWinner.putString(WINNER, getResources().getString(R.string.win));
                                    intentResult.putExtra(WINNER, bundleWinner);
                                    startActivity(intentResult);
                                    finish();
                                } else {
                                    Thread t = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mComputerPlayer.play();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(), R.string.computer_finish_turn, Toast.LENGTH_LONG).show();
                                                    ((TileAdapter) mPlayerBoard.getAdapter()).notifyDataSetChanged();
                                                    if (mGame.getPlayerBoard().checkWinner()) {
                                                        bundleWinner.putString(WINNER, getResources().getString(R.string.lose));
                                                        intentResult.putExtra(WINNER, bundleWinner);
                                                        startActivity(intentResult);
                                                        finish();
                                                    }
                                                    turn.setText(R.string.your_turn);
                                                    mProgressBar.setVisibility(View.INVISIBLE);
                                                    mGame.changeTurn();
                                                }
                                            });
                                        }
                                    });
                                    t.start();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.wait_to_computer_finish, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }
}

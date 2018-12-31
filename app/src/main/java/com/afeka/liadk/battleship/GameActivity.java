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

    final static String GAME_STATUS = "STATUS";
    final static String WHO_WIN = "WHO WIN";

    private Game mGame;
    private TextView turn;
    private GridView mPlayerBoard;
    private GridView mComputerBoard;
    private ComputerPlayer mComputerPlayer;
    private ProgressBar mProgressBar;
    private Level mChoosenLevel;
    private int mLevelNumberOfShip;
    private TextView mGameStatusComputer, mGameStatusPlayer;
    private StringBuilder mShipComputer, mShipsPlayer;
    private Intent mIntentResult;
    private Bundle mBundleWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Bundle level = intent.getBundleExtra(GameSettingsInterface.LEVEL_MESSAGE);
        if (level != null) {
            mChoosenLevel = (Level) level.getSerializable(GameSettingsInterface.LEVEL_CHOOSEN);
            if (mChoosenLevel != null) {
                init();
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                mPlayerBoard.setAdapter(new TileAdapter(getApplicationContext(), mGame.getPlayerBoard(), width / (mChoosenLevel.getWidth() * 2), height / (mChoosenLevel.getHeight() * 4), Game.Player.HumanPlayer));
                mPlayerBoard.setNumColumns(mGame.getNumColumns());
                mComputerBoard.setAdapter(new TileAdapter(getApplicationContext(), mGame.getCoumputerBoard(), (int) (width / (mChoosenLevel.getWidth() * 1.2)), height / (mChoosenLevel.getHeight() * 3), Game.Player.ComputerPlayer));
                mComputerBoard.setNumColumns(mGame.getNumColumns());
                mComputerBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int posotion, long id) {
                        if (mGame.getCurrentPlayer() == Game.Player.HumanPlayer) {
                            boolean playerHit = mGame.getCoumputerBoard().attackTheBoard(posotion);
                            if (playerHit) {
                                ((TileAdapter) mComputerBoard.getAdapter()).notifyDataSetChanged();
                                mShipsPlayer = new StringBuilder(mLevelNumberOfShip - mGame.getCoumputerBoard().getNumberOfShips() + "/" + mLevelNumberOfShip);
                                mGameStatusPlayer.setText(mShipsPlayer);
                                mGame.changeTurn();
                                if (mGame.getCoumputerBoard().checkWinner()) {
                                    mBundleWinner.putString(WHO_WIN, getResources().getString(R.string.win));
                                    mIntentResult.putExtra(GAME_STATUS, mBundleWinner);
                                    startActivity(mIntentResult);
                                    finish();
                                } else {
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    turn.setText(R.string.computer_turn);
                                    Thread t = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mComputerPlayer.play();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ((TileAdapter) mPlayerBoard.getAdapter()).notifyDataSetChanged();
                                                    mShipComputer = new StringBuilder(mLevelNumberOfShip - mGame.getPlayerBoard().getNumberOfShips() + "/" + mLevelNumberOfShip);
                                                    mGameStatusComputer.setText(mShipComputer);
                                                    if (mGame.getPlayerBoard().checkWinner()) {
                                                        mBundleWinner.putString(WHO_WIN, getResources().getString(R.string.lose));
                                                        mIntentResult.putExtra(GAME_STATUS, mBundleWinner);
                                                        startActivity(mIntentResult);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), R.string.computer_finish_turn, Toast.LENGTH_SHORT).show();
                                                        turn.setText(R.string.your_turn);
                                                        mProgressBar.setVisibility(View.INVISIBLE);
                                                        mGame.changeTurn();
                                                    }
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

    private void init() {
        mIntentResult = new Intent(this, ResultActivity.class);
        mBundleWinner = new Bundle();
        Bundle bundleGameLevel = new Bundle();
        bundleGameLevel.putSerializable(GameSettingsInterface.LEVEL_CHOOSEN, mChoosenLevel);
        mIntentResult.putExtra(GameSettingsInterface.LEVEL_MESSAGE, bundleGameLevel);
        mProgressBar = ((ProgressBar) findViewById(R.id.progressBar));
        mProgressBar.setVisibility(View.INVISIBLE);
        turn = (TextView) findViewById(R.id.playerTurn);
        turn.setText(R.string.your_turn);
        mGame = new Game(mChoosenLevel);
        mLevelNumberOfShip = mGame.getPlayerBoard().getNumberOfShips();
        StringBuilder str = new StringBuilder("0/" + mLevelNumberOfShip);
        mGameStatusComputer = ((TextView) findViewById(R.id.shipsStatusComputer));
        mGameStatusComputer.setText(str);
        mGameStatusPlayer = ((TextView) findViewById(R.id.shipsStatusPlayer));
        mGameStatusPlayer.setText(str);
        mPlayerBoard = findViewById(R.id.playerBoard);
        mComputerBoard = findViewById(R.id.computerBoard);
        mComputerPlayer = new ComputerPlayer(mChoosenLevel, mGame.getPlayerBoard());
    }
}

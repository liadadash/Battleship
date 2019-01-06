package com.afeka.liadk.battleship;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afeka.liadk.battleship.Logic.Board;
import com.afeka.liadk.battleship.Logic.ComputerPlayer;
import com.afeka.liadk.battleship.Logic.Game;

public class GameActivity extends AppCompatActivity implements GameSettingsInterface, MovingDeviceService.onDeviceMovedListener {

    final static String GAME_STATUS = "STATUS";
    final static String WHO_WIN = "WHO WIN";
    final static String NUMBER_OF_STEPS = "STEPS";

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
    private int mSteps;
    private MovingDeviceService mService;
    boolean mBound = false;
    private MovingDeviceService.onDeviceMovedListener me = this;
    private AnimationDrawable animationDrawable;

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
                                mSteps++;
                                ((TileAdapter) mComputerBoard.getAdapter()).notifyDataSetChanged();
                                mShipsPlayer = new StringBuilder(mLevelNumberOfShip - mGame.getCoumputerBoard().getNumberOfShips() + "/" + mLevelNumberOfShip);
                                mGameStatusPlayer.setText(mShipsPlayer);
                                if (mGame.getCoumputerBoard().checkWinner()) {
                                    mBundleWinner.putString(WHO_WIN, getResources().getString(R.string.win));
                                    mBundleWinner.putInt(NUMBER_OF_STEPS, mSteps);
                                    mIntentResult.putExtra(GAME_STATUS, mBundleWinner);
                                    startActivity(mIntentResult);
                                    finish();
                                } else {
                                    mGame.changeTurn();
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

    private void animation(){
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.alert_animation, null);
        findViewById(R.id.game).setBackground(animationDrawable);
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
    }

    private void init() {
        animation();
        mSteps = 0;
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

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MovingDeviceService.MovingDeviceBinder binder = (MovingDeviceService.MovingDeviceBinder) service;
            mService = binder.getService();
            mService.registerListener(me);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MovingDeviceService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mService.unRegisterListener();
        mBound = false;
    }

    @Override
    public void onDeviceMoved() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animationDrawable.start();
            }
        });
    }

    @Override
    public void onDeviceStillNotBack() {

    }

    @Override
    public void onDeviceBack() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animationDrawable.stop();
                animation();
            }
        });
    }
}

package com.afeka.liadk.battleship;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afeka.liadk.battleship.Logic.GameSettingsInterface;

public class MainActivity extends AppCompatActivity {

    final static String LEVEL_MESSAGE = "LEVEL";
    final static String LEVEL_CHOOSEN = "CHOOSEN LEVEL";
    private static String lastChosenLevelKey = "last level";
    private static int lastChosenLevel;
    private SharedPreferences mSharedPref;
    private boolean mHasLastGameLevel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_list, null);
        findViewById(R.id.mainActivity).setBackground(animationDrawable);
        animationDrawable.start();
        mSharedPref = getPreferences(Context.MODE_PRIVATE);
        lastChosenLevel = mSharedPref.getInt(lastChosenLevelKey, GameSettingsInterface.Level.Unknown.ordinal());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lastChosenLevel == GameSettingsInterface.Level.Unknown.ordinal())
            findViewById(R.id.button_last_game).setVisibility(View.GONE);
        else {
            mHasLastGameLevel = true;
            findViewById(R.id.button_last_game).setVisibility(View.VISIBLE);
        }
    }

    public void onLevelButtonClicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle bundleLevel = new Bundle();
        switch (view.getId()) {
            case R.id.button_easy: {
                bundleLevel.putSerializable(LEVEL_CHOOSEN, GameSettingsInterface.Level.Easy);
                lastChosenLevel = GameSettingsInterface.Level.Easy.ordinal();
                break;
            }
            case R.id.button_medium: {
                bundleLevel.putSerializable(LEVEL_CHOOSEN, GameSettingsInterface.Level.Medium);
                lastChosenLevel = GameSettingsInterface.Level.Medium.ordinal();
                break;
            }
            case R.id.button_hard: {
                bundleLevel.putSerializable(LEVEL_CHOOSEN, GameSettingsInterface.Level.Hard);
                lastChosenLevel = GameSettingsInterface.Level.Hard.ordinal();
                break;
            }
            case R.id.button_last_game: {
                bundleLevel.putSerializable(LEVEL_CHOOSEN, GameSettingsInterface.Level.values()[lastChosenLevel]);
                break;
            }
        }
        intent.putExtra(LEVEL_MESSAGE, bundleLevel);
        startActivity(intent);
        if (!mHasLastGameLevel)
            mHasLastGameLevel = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor sharedPrefEditor = mSharedPref.edit();
        sharedPrefEditor.putInt(lastChosenLevelKey, lastChosenLevel);
        sharedPrefEditor.apply(); // commit()
    }
}


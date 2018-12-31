package com.afeka.liadk.battleship;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements MainStartFragment.onButtonTouchedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_list, null);
        findViewById(R.id.mainActivity).setBackground(animationDrawable);
        animationDrawable.start();
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
//            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        MainStartFragment newFragment = new MainStartFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.selectButtons, newFragment).commit();
    }

    @Override
    public void onButtonTouchedPlay() {
        Fragment newFragment = new ChooseLevelFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.selectButtons, newFragment).addToBackStack(null).commit();
    }

    @Override
    public void onButtonTouchedHighScore() {
        HighscoreFragment newFragment = new HighscoreFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.selectButtons, newFragment).addToBackStack(null).commit();
    }
}


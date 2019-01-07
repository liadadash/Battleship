package com.afeka.liadk.battleship;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afeka.liadk.battleship.Logic.GameSettingsInterface;

public class ChooseLevelFragment extends Fragment implements View.OnClickListener, GameSettingsInterface {

    private final String LAST_LEVEL_CHOOSEN_Key = "LAST LEVEL";
    private Level mLastLevel;
    private SharedPreferences mSharedPref;
    private boolean mHasLastGameLevel = false;
    private Button mEasy;
    private Button mMedium;
    private Button mHard;
    private Button mLast;

    public ChooseLevelFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        mLastLevel = Level.values()[mSharedPref.getInt(LAST_LEVEL_CHOOSEN_Key, Level.Unknown.ordinal())];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_level, container, false);
        mEasy = ((Button) view.findViewById(R.id.button_easy));
        mEasy.setOnClickListener(this);
        mMedium = ((Button) view.findViewById(R.id.button_medium));
        mMedium.setOnClickListener(this);
        mHard = ((Button) view.findViewById(R.id.button_hard));
        mHard.setOnClickListener(this);
        mLast = ((Button) view.findViewById(R.id.button_last_game));
        mLast.setOnClickListener(this);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mLastLevel == Level.Unknown)
            mLast.setVisibility(View.GONE);
        else {
            mHasLastGameLevel = true;
            mLast.setVisibility(View.VISIBLE);
            setLastLevelColor();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor sharedPrefEditor = mSharedPref.edit();
        sharedPrefEditor.putInt(LAST_LEVEL_CHOOSEN_Key, mLastLevel.ordinal());
        sharedPrefEditor.commit();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        Bundle bundleLevel = new Bundle();
        removeLastLevelColor();
        switch (view.getId()) {
            case R.id.button_easy: {
                bundleLevel.putSerializable(LEVEL_CHOOSEN, Level.Easy);
                mLastLevel = Level.Easy;
                break;
            }
            case R.id.button_medium: {
                bundleLevel.putSerializable(LEVEL_CHOOSEN, Level.Medium);
                mLastLevel = Level.Medium;
                break;
            }
            case R.id.button_hard: {
                bundleLevel.putSerializable(LEVEL_CHOOSEN, Level.Hard);
                mLastLevel = Level.Hard;
                break;
            }
            case R.id.button_last_game: {
                bundleLevel.putSerializable(LEVEL_CHOOSEN, mLastLevel);
                break;
            }
        }
        intent.putExtra(LEVEL_MESSAGE, bundleLevel);
        setLastLevelColor();
        startActivity(intent);
        if (!mHasLastGameLevel)
            mHasLastGameLevel = true;
    }

    private void setLastLevelColor() {
        switch (mLastLevel) {
            case Easy:
                mEasy.setTextColor(getResources().getColor(R.color.lastLevel));
                break;
            case Medium:
                mMedium.setTextColor(getResources().getColor(R.color.lastLevel));
                break;
            case Hard:
                mHard.setTextColor(getResources().getColor(R.color.lastLevel));
                break;
        }
    }

    private void removeLastLevelColor() {
        switch (mLastLevel) {
            case Easy:
                mEasy.setTextColor(getResources().getColor(R.color.black));
                break;
            case Medium:
                mMedium.setTextColor(getResources().getColor(R.color.black));
                break;
            case Hard:
                mHard.setTextColor(getResources().getColor(R.color.black));
                break;
        }
    }
}

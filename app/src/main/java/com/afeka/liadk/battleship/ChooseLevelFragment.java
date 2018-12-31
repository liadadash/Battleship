package com.afeka.liadk.battleship;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afeka.liadk.battleship.Logic.GameSettingsInterface;

public class ChooseLevelFragment extends Fragment implements View.OnClickListener{

    private static String lastChosenLevelKey = "last level";
    private static int lastChosenLevel;
    private SharedPreferences mSharedPref;
    private boolean mHasLastGameLevel = false;

    public ChooseLevelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        lastChosenLevel = mSharedPref.getInt(lastChosenLevelKey, GameSettingsInterface.Level.Unknown.ordinal());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_level, container, false);
        ((Button) view.findViewById(R.id.button_easy)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_medium)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_hard)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_last_game)).setOnClickListener(this);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (lastChosenLevel == GameSettingsInterface.Level.Unknown.ordinal())
            getActivity().findViewById(R.id.button_last_game).setVisibility(View.GONE);
        else {
            mHasLastGameLevel = true;
            getActivity().findViewById(R.id.button_last_game).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor sharedPrefEditor = mSharedPref.edit();
        sharedPrefEditor.putInt(lastChosenLevelKey, lastChosenLevel);
        sharedPrefEditor.apply(); // commit()
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        Bundle bundleLevel = new Bundle();
        switch (view.getId()) {
            case R.id.button_easy: {
                bundleLevel.putSerializable(GameSettingsInterface.LEVEL_CHOOSEN, GameSettingsInterface.Level.Easy);
                lastChosenLevel = GameSettingsInterface.Level.Easy.ordinal();
                break;
            }
            case R.id.button_medium: {
                bundleLevel.putSerializable(GameSettingsInterface.LEVEL_CHOOSEN, GameSettingsInterface.Level.Medium);
                lastChosenLevel = GameSettingsInterface.Level.Medium.ordinal();
                break;
            }
            case R.id.button_hard: {
                bundleLevel.putSerializable(GameSettingsInterface.LEVEL_CHOOSEN, GameSettingsInterface.Level.Hard);
                lastChosenLevel = GameSettingsInterface.Level.Hard.ordinal();
                break;
            }
            case R.id.button_last_game: {
                bundleLevel.putSerializable(GameSettingsInterface.LEVEL_CHOOSEN, GameSettingsInterface.Level.values()[lastChosenLevel]);
                break;
            }
        }
        intent.putExtra(GameSettingsInterface.LEVEL_MESSAGE, bundleLevel);
        startActivity(intent);
        if (!mHasLastGameLevel)
            mHasLastGameLevel = true;
    }
}

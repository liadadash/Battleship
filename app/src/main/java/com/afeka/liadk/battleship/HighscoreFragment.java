package com.afeka.liadk.battleship;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.afeka.liadk.battleship.Logic.GameSettingsInterface;

import java.util.ArrayList;

public class HighscoreFragment extends Fragment implements View.OnClickListener {

    public HighscoreFragment() {
        // Required empty public constructor
    }

    private DataBaseHandler mDb;
    private ListView mList;
    private ArrayList mWinners;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highscore, container, false);
        mDb = new DataBaseHandler(view.getContext());
        mList = (ListView) view.findViewById(R.id.userData);
        setWinners(view, R.id.button_easy);
        view.findViewById(R.id.button_easy).setOnClickListener(this);
        view.findViewById(R.id.button_medium).setOnClickListener(this);
        view.findViewById(R.id.button_hard).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        setWinners(view, view.getId());
    }

    private void setWinners(View view, int id) {
        switch (id) {
            case R.id.button_easy:
                mWinners = mDb.getData(GameSettingsInterface.Level.Easy.toString(), 10);
                mList.setBackgroundColor(getResources().getColor(R.color.easyLevel));
                mList.setAdapter(new UserAdapter(view.getContext(), mWinners));
                break;
            case R.id.button_medium:
                mWinners = mDb.getData(GameSettingsInterface.Level.Medium.toString(), 10);
                mList.setBackgroundColor(getResources().getColor(R.color.mediumLevel));
                mList.setAdapter(new UserAdapter(view.getContext(), mWinners));
                break;
            case R.id.button_hard:
                mWinners = mDb.getData(GameSettingsInterface.Level.Hard.toString(), 10);
                mList.setBackgroundColor(getResources().getColor(R.color.hardLevel));
                mList.setAdapter(new UserAdapter(view.getContext(), mWinners));
                break;
        }
    }
}

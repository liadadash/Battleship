package com.afeka.liadk.battleship;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoreFragment extends Fragment implements View.OnClickListener, GameSettingsInterface {

    final int TOP_PLAYERS = 10;

    public HighscoreFragment() {
        // Required empty public constructor
    }

    private DataBaseHandler mDb;
    private ListView mList;
    private ArrayList mWinners;
    private View mViewNoData;

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
        ((TextView) view.findViewById(R.id.textViewTop)).setText(getResources().getString(R.string.top_users) + " " + TOP_PLAYERS);
        mViewNoData = view.findViewById(R.id.noScore);
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
                setData(view, Level.Easy, R.color.easyLevel);
                break;
            case R.id.button_medium:
                setData(view, Level.Medium, R.color.mediumLevel);
                break;
            case R.id.button_hard:
                setData(view, Level.Hard, R.color.hardLevel);
                break;
        }
    }

    private void setData(View view, Level level, int color) {
        mWinners = mDb.getData(level, TOP_PLAYERS);
        mList.setAdapter(new UserAdapter(view.getContext(), mWinners));
        mList.setBackgroundColor(getResources().getColor(color));
        if (mWinners.size() != 0) {
            if (mViewNoData.getVisibility() != View.GONE)
                mViewNoData.setVisibility(View.GONE);
        } else {
            if (mViewNoData.getVisibility() != View.VISIBLE)
                mViewNoData.setVisibility(View.VISIBLE);
        }
    }
}

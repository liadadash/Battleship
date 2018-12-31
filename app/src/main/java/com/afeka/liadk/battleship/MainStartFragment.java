package com.afeka.liadk.battleship;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainStartFragment extends Fragment {

    public MainStartFragment() {
        // Required empty public constructor
    }

    interface onButtonTouchedListener {
        void onButtonTouchedPlay();
        void onButtonTouchedHighScore();
    }

    private onButtonTouchedListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_start, container, false);

        Button b = (Button)view.findViewById(R.id.chooseLevelButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainStartFragment.this.mListener != null)
                    MainStartFragment.this.mListener.onButtonTouchedPlay();
            }
        });

        b = (Button)view.findViewById(R.id.highscores);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainStartFragment.this.mListener != null)
                    MainStartFragment.this.mListener.onButtonTouchedHighScore();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onButtonTouchedListener listener = (onButtonTouchedListener) context;
            if (listener != null)
                this.registerListener(listener);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onButtonTouchedListener");
        }
    }

    public void registerListener(onButtonTouchedListener listener) {
        this.mListener = listener;
    }

}

package com.afeka.liadk.battleship;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserView extends LinearLayout {

    private TextView mText;

    public UserView(Context context, int pos, String name, int points) {
        super(context);
        mText = new TextView(context);
        mText.setTextSize(20);
        mText.setTextColor(getResources().getColor(R.color.black));
        setText(pos, name, points);
        addView(mText);
    }

    public void setText(int pos, String name, int points) {
        mText.setText((pos + 1) + ". " + getContext().getString(R.string.name) + ": " + name + ", " + getContext().getString(R.string.points) + ": " + points);
    }
}

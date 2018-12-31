package com.afeka.liadk.battleship;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String[]> mUsers;
    private UserView[] mUserAdapter;

    public UserAdapter(Context context, ArrayList<String[]> users) {
        this.mContext = context;
        mUsers = users;
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return mUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UserView userView;
        if (view == null) {
            String[] s = (String[]) mUsers.get(i);
            userView = new UserView(mContext, i, s[0], Integer.parseInt(s[1]));
            userView.setPadding(8, 8, 8, 8);
        } else
            userView = (UserView) view;
        return userView;
    }
}

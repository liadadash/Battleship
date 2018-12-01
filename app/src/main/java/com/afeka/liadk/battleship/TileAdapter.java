package com.afeka.liadk.battleship;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.afeka.liadk.battleship.Logic.Board;
import com.afeka.liadk.battleship.Logic.Tile;

public class TileAdapter extends BaseAdapter {

    private Context mContext;
    private Board mBoard;

    public TileAdapter(Context context, Board board) {
        mBoard = board;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mBoard.getBoardSize();
    }

    @Override
    public Object getItem(int i) {
        return mBoard.getTile(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        TileView tileView;
        if (convertView == null) {
            tileView = new TileView(mContext);
            tileView.setLayoutParams(new GridView.LayoutParams(200, 200));
            //tileView.setPadding(8, 8, 8, 8);
        } else {
            tileView = (TileView) convertView;
        }

        checkTileStatus(tileView, position);

        return tileView;
    }

    private void checkTileStatus(TileView tileView, int position) {
        if (mBoard.getTile(position).getStatus() == Tile.TileState.NONE)
            tileView.setBackgroundResource(R.color.NONE);
        else if (mBoard.getTile(position).getStatus() == Tile.TileState.INJURED)
            tileView.setBackgroundResource(R.color.INJURED);
        else if (mBoard.getTile(position).getStatus() == Tile.TileState.INJURED_WITH_SHIPS)
            tileView.setBackgroundResource(R.color.INJURED_WITH_SHIPS);
        else if (mBoard.getTile(position).getStatus() == Tile.TileState.DROWNED)
            tileView.setBackgroundResource(R.color.DROWNED);
        else if (mBoard.getTile(position).getStatus() == Tile.TileState.MY_SHIP)
            tileView.setBackgroundResource(R.color.MY_SHIP);
    }

}

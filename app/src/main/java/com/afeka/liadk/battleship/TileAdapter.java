package com.afeka.liadk.battleship;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.afeka.liadk.battleship.Logic.Board;
import com.afeka.liadk.battleship.Logic.Game;
import com.afeka.liadk.battleship.Logic.Tile;

public class TileAdapter extends BaseAdapter implements GameSettingsInterface {

    private Context mContext;
    private Board mBoard;
    private int mWidth, mHeight;
    private Game.Player mPlayer;

    public TileAdapter(Context context, Board board, int width, int height, Game.Player player) {
        mBoard = board;
        mContext = context;
        mWidth = width;
        mHeight = height;
        mPlayer = player;
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
            tileView.setLayoutParams(new GridView.LayoutParams(mWidth, mHeight));
        } else {
            tileView = (TileView) convertView;
        }

        checkTileStatus(tileView, position);

        return tileView;
    }

    private void checkTileStatus(TileView tileView, int position) {
        if (mBoard.getTile(position).getStatus() == Tile.TileState.NONE && mPlayer == Game.Player.HumanPlayer) {
            tileView.setBackgroundResource(R.color.colorUnknown);
            return;
        }
        if (mBoard.getTile(position).getStatus() == Tile.TileState.SHIP && mPlayer == Game.Player.HumanPlayer) {
            tileView.setBackgroundResource(R.color.colorOccupied);
            return;
        }
        switch (mBoard.getTile(position).getStatus()) {
            case MISS:
                if (!tileView.isClicked())
                    tileView.ClickMe(Tile.TileState.MISS);
                else
                    tileView.setBackgroundResource(R.color.colorMissed);
                break;
            case INJURED_WITH_SHIPS:
                if (!tileView.isClicked())
                    tileView.ClickMe(Tile.TileState.INJURED_WITH_SHIPS);
                else
                    tileView.setBackgroundResource(R.color.colorHit);
                break;
            case DROWNED:
                if (!tileView.isDrowned())
                    tileView.ClickMe(Tile.TileState.DROWNED);
                else
                    tileView.setBackgroundResource(R.color.colorShipDrowned);
                break;
            default:
                tileView.setBackgroundResource(R.color.colorVacant);
                break;
        }
    }
}

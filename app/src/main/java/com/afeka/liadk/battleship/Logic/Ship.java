package com.afeka.liadk.battleship.Logic;

public class Ship {
    private int mSize, mFullSize;
    private Tile mTiles[];

    public Ship(int size) {
        mFullSize = mSize = size;
    }

    public boolean setShipToTile(Tile tiles[]) {
        boolean succeeded;
        for (int i = 0; i < mSize; i++) {
            succeeded = tiles[i].setShip(this);
            if (!succeeded) {
                for (int j = i - 1; j >= 0; j--) {
                    tiles[j].removeShip();
                }
                return false;
            }
        }
        mTiles = tiles;
        return true;
    }

    public void unsubscribeTile() {
        for (int i = 0; i < mSize; i++)
            mTiles[i].removeShip();
        mTiles = null;
    }

    public void injured() {
        mSize--;
        if (mSize == 0) {
            for (int i = 0; i < mTiles.length; i++) {
                mTiles[i].shipDead();
                mTiles[i] = null;
            }
        }
    }

    public int getSizeWithHit() {
        return mSize;
    }

    public int getFullSize() {
        return mFullSize;
    }


    public Tile getTile(int pos) {
        return mTiles[pos];
    }

    public Tile.TileState[] getTileHelperStatus() {
        Tile.TileState[] state = new Tile.TileState[mTiles.length];
        for (int i = 0; i < mTiles.length; i++) {
            state[i] = mTiles[i].getStatus();
            mTiles[i].setStatus(Tile.TileState.NONE);
        }
        return state;
    }
}

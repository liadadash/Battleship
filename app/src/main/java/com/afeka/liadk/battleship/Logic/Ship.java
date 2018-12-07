package com.afeka.liadk.battleship.Logic;

public class Ship {
    private int mSize;
    private boolean isDead;
    private Tile mTiles[];

    public Ship(int size) {
        isDead = false;
        mSize = size;
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
}

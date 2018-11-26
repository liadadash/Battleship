package com.afeka.liadk.battleship.Logic;

public class Ship {
    private int mSize;
    private boolean isDead;
    private Tile mTiles[];

    public Ship(int size, Tile tiles[]) {
        isDead = false;
        mSize = size;
        mTiles = new Tile[tiles.length];
        for (int i = 0; i < tiles.length; i++)
            mTiles[i] = tiles[i];
    }

    public void injured(){
        mSize--;
        if (mSize==0)
        {
            for (int i=0;i<mTiles.length;i++)
                mTiles[i].shipDead();
        }
    }
}

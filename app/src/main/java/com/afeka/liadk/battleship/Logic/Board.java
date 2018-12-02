package com.afeka.liadk.battleship.Logic;

public class Board {

    private boolean mDidAllMyShipsDied = false;
    private int mNumberOfShips;
    private Tile mTiles[];
    private Ship mShips[];

    public Board(int weight, int height, int[] ships) {

        mTiles = new Tile[weight * height];
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i] = new Tile();
        }
        mNumberOfShips = ships.length;
        mShips = new Ship[mNumberOfShips];
        setShips(ships, weight, height);
    }

    public int getBoardSize() {
        return mTiles.length;
    }

    public Tile getTile(int position) {
        return mTiles[position];
    }

    public void setShips(int[] ships, int weight, int height) {
        Tile shipTile[];
        for (int i = 0; i < ships.length; i++) {
            shipTile = new Tile[ships[i]];
            for (int j = 0; j < ships[i]; j++) {
                shipTile[j] = mTiles[i * weight + j];
            }
            mShips[i] = new Ship(ships[i], shipTile);
        }
    }

    public boolean attackTheBoard(int position) {
        boolean hit = mTiles[position].hit();
        if (hit && mTiles[position].getStatus() == Tile.TileState.DROWNED) {
            mNumberOfShips--;
            if (mNumberOfShips == 0)
                mDidAllMyShipsDied = true;
        }
        return hit;
    }

    public boolean checkWinner() {
        return mDidAllMyShipsDied;
    }
}

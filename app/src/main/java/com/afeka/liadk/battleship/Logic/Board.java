package com.afeka.liadk.battleship.Logic;

import java.util.Random;

public class Board {

    private Tile mTiles[];
    private Ship mShips[];

    public Board(int weight, int height, int[] ships) {

        mTiles = new Tile[weight * height];
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i] = new Tile();
        }
        mShips = new Ship[ships.length];
        setShips(ships, weight, height);
    }

    public int getBoardSize() {
        return mTiles.length;
    }

    public Tile getTile(int position) {
        return mTiles[position];
    }

    public void setShips(int[] ships, int weight, int height) {
//        Random random = new Random();
//        int row, column;
//        for (int i = 0; i < ships.length; i++) {
//            boolean found = false;
//            do {
//                row = random.nextInt(height);
//                column = random.nextInt(weight);
//                if (height-row)
//                for (int j = 0; j < ships[i]; j++) {
//
//                }
//            } while (!found)
//        }
        Tile shipTile[];
        for (int i = 0; i < ships.length; i++) {
            shipTile = new Tile[ships[i]];
            for (int j = 0; j < ships[i]; j++) {
                shipTile[j] = mTiles[i * weight + j];
            }
            mShips[i]=new Ship(ships[i],shipTile);
        }
    }

    public void attackTheBoard(int position) {
        mTiles[position].hit();
    }
}

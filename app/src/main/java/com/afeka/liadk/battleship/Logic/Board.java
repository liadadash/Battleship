package com.afeka.liadk.battleship.Logic;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    private Tile mTiles[];
    private ArrayList<Ship> mShips;

    public Board(int weight, int height, int numberOfShips, int shipSize) {

        mTiles = new Tile[weight * height];
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i] = new Tile();
        }
        setShips(numberOfShips, shipSize, weight);
    }

    public void setShips(int numberOfShips, int shipSize, int weight) {
        mShips = new ArrayList<>();
        Tile temp[] = new Tile[numberOfShips];
        for (int i = 0; i < numberOfShips; i++) {
            for (int j = 0; j < shipSize; j++) {
                temp[j] = mTiles[i * weight + j];
            }
            mShips.add(new Ship(shipSize, temp));
            for (int j = 0; j < numberOfShips; j++) {
                temp[j].setShip(mShips.get(i));
            }
        }
    }

    public void attackTheBoard(int position) {
        mTiles[position].hit();
    }
}

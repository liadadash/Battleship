package com.afeka.liadk.battleship.Logic;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    private final int SHIPS_TRY = 2;

    private boolean mDidAllMyShipsDied = false;
    private int mNumberOfShips;
    private Tile mTiles[];
    private Ship mShips[];
    private int mWeight, mHeight;

    public Board(int weight, int height, int[] ships) {
        mWeight = weight;
        mHeight = height;
        mTiles = new Tile[weight * height];
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i] = new Tile();
        }
        setShips(ships, weight, height);
    }

    public int getNumberOfShips() {
        return mNumberOfShips;
    }

    public int getBoardSize() {
        return mTiles.length;
    }

    public Tile getTile(int position) {
        return mTiles[position];
    }

    public void setShips(int[] ships, int weight, int height) {
        mNumberOfShips = ships.length;
        mShips = new Ship[mNumberOfShips];
        Tile shipTile[][] = new Tile[ships.length][];
        boolean succeeded;
        Random random = new Random();
        int temp, x, y;
        for (int i = 0; i < ships.length; i++) {
            mShips[i] = new Ship(ships[i]);
            shipTile[i] = new Tile[ships[i]];
        }
        int count;
        do {
            succeeded = true;
            for (int i = 0; i < ships.length; i++) {
                count = 0;
                do {
                    temp = random.nextInt(2);
                    if (temp == 0) {
                        x = random.nextInt(weight);
                        y = random.nextInt(height - ships[i]);
                        for (int j = 0; j < ships[i]; j++)
                            shipTile[i][j] = mTiles[(y + j) * weight + x];
                        succeeded = mShips[i].setShipToTile(shipTile[i]);
                        if (succeeded)
                            count++;
                    } else {
                        x = random.nextInt(weight - ships[i]);
                        y = random.nextInt(height);
                        for (int j = 0; j < ships[i]; j++)
                            shipTile[i][j] = mTiles[y * weight + x + j];
                        succeeded = mShips[i].setShipToTile(shipTile[i]);
                        count++;
                    }
                } while (!succeeded && count < SHIPS_TRY);
                if (!succeeded) {
                    for (int j = i - 1; j >= 0; j--)
                        mShips[j].unsubscribeTile();
                    break;
                }
            }
        } while (!succeeded);
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

    public void cleanMiss() {
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i].cleanMiss();
        }
    }

    public void boardMove() {
        cleanMiss();
        ArrayList<Ship> ship = new ArrayList<>();
        for (int i = 0; i < mShips.length; i++) {
            if (mShips[i].getSizeWithHit() > 0)
                ship.add(mShips[i]);
        }
        Tile.TileState[][] state = new Tile.TileState[ship.size()][];
        for (int i = 0; i < ship.size(); i++) {
            state[i] = ship.get(i).getTileHelperStatus();
            for (int j = 0; j < ship.get(i).getFullSize(); j++) {
                ship.get(i).getTile(j).removeShip();
            }
        }
        int[] shipsLen = new int[ship.size()];
        for (int i = 0; i < ship.size(); i++) {
            shipsLen[i] = ship.get(i).getFullSize();
        }
        setShips(shipsLen, mWeight, mHeight);
        for (int i = 0; i < mNumberOfShips; i++) {
            for (int j = 0; j < mShips[i].getFullSize(); j++) {
                if (state[i][j] == Tile.TileState.INJURED_WITH_SHIPS) {
                    mShips[i].getTile(j).hit();
                    mShips[i].getTile(j).clicked();
                }
            }
        }
    }
}

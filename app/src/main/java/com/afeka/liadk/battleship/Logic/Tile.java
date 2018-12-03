package com.afeka.liadk.battleship.Logic;

public class Tile {

    public enum TileState {NONE, SHIP, MISS, INJURED_WITH_SHIPS, DROWNED;}

    private TileState mStatus;
    private Ship mShip;

    public Tile() {
        mStatus = TileState.NONE;
    }

    public boolean setShip(Ship ship) {
        if (mStatus == TileState.NONE) {
            mStatus = TileState.SHIP;
            mShip = ship;
            return true;
        }
        return false;
    }

    public void removeShip() {
        mStatus = TileState.NONE;
        mShip = null;
    }

    public boolean hit() {
        if (mStatus == TileState.NONE || mStatus == TileState.SHIP) {
            if (mShip != null) {
                mStatus = TileState.INJURED_WITH_SHIPS;
                mShip.injured();
            } else mStatus = TileState.MISS;
            return true;
        }
        return false;
    }

    public void shipDead() {
        mStatus = TileState.DROWNED;
    }

    public TileState getStatus() {
        return mStatus;
    }
}

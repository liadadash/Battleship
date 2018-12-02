package com.afeka.liadk.battleship.Logic;

public class Tile {

    public enum TileState {NONE, SHIP, INJURED, INJURED_WITH_SHIPS, DROWNED;}

    private TileState mStatus;
    private Ship mShip;

    public Tile() {
        mStatus = TileState.NONE;
    }

    public void setShip(Ship ship) {
        mStatus = TileState.SHIP;
        mShip = ship;
    }

    public boolean hit() {
        if (mStatus == TileState.NONE || mStatus == TileState.SHIP) {
            if (mShip != null) {
                mStatus = TileState.INJURED_WITH_SHIPS;
                mShip.injured();
            } else mStatus = TileState.INJURED;
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

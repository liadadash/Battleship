package com.afeka.liadk.battleship.Logic;

public class Tile {

    public enum TileState {NONE, MY_SHIP, INJURED, INJURED_WITH_SHIPS, DROWNED;}

    private TileState mStatus;
    private Ship mShip;

    public Tile() {
        mStatus = TileState.NONE;
    }

    public void setShip(Ship ship) {
        mStatus = TileState.MY_SHIP;
        mShip = ship;
    }

    public boolean hit() {
        if (mStatus == TileState.NONE) {
            if (mShip != null) {
                mStatus = TileState.INJURED_WITH_SHIPS;
                mShip.injured();
            } else mStatus = TileState.INJURED;
            return true;
        } else return false;
    }

    public void shipDead() {
        mStatus = TileState.DROWNED;
    }
}

package com.afeka.liadk.battleship.Logic;

public class Tile {

    public enum TileState {NONE, SHIP, MISS, INJURED_WITH_SHIPS, DROWNED;}

    private TileState mStatus;
    private Ship mShip;
    private boolean clicked;

    public Tile() {
        mStatus = TileState.NONE;
    }

    public boolean setShip(Ship ship) {
        if (mStatus == TileState.NONE) {
            mStatus = TileState.SHIP;
            mShip = ship;
            clicked = false;
            return true;
        }
        return false;
    }

    public void removeShip() {
        mStatus = TileState.NONE;
        mShip = null;
        clicked = false;
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

    public void cleanMiss() {
        if (mStatus == TileState.MISS) {
            mStatus = TileState.NONE;
        }
    }

    public void setStatus(TileState state) {
        mStatus = state;
        if (state == TileState.NONE)
            clicked = false;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void clicked() {
        clicked = true;
    }
}

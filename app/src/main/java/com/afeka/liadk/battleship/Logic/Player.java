package com.afeka.liadk.battleship.Logic;

public abstract class Player {
    private boolean mDidAllMyShipsDied = false;
    private int mWidth;
    private int mHeight;

    public Player(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    abstract public void locateShip();
    abstract public void play();
    //abstract public void locateShip();
    //abstract public void locateShip();
}

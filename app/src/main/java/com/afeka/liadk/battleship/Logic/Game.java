package com.afeka.liadk.battleship.Logic;

public class Game implements GameSettingsInterface {

    private int mWidth;
    private int mHeight;
    private int[] mShips;
    private Board mPlayerBoard, mCoumputerBoard;

    public Game(Level level) {
        setLevelSettings(level);
        mPlayerBoard = new Board(mWidth, mHeight, mShips);
        mCoumputerBoard = new Board(mWidth, mHeight,mShips);
    }

    private void setLevelSettings(Level level) {
        mWidth = level.getWidth();
        mHeight = level.getHeight();
        mShips = level.getShips();
    }

    public int getNumColumns() {
        return mWidth;
    }

    public Board getPlayerBoard() {
        return mPlayerBoard;
    }

    public Board getCoumputerBoard() {
        return mCoumputerBoard;
    }
}

package com.afeka.liadk.battleship.Logic;

import com.afeka.liadk.battleship.GameSettingsInterface;

public class Game implements GameSettingsInterface {

    public enum Player {
        HumanPlayer, ComputerPlayer;

        public Player changeTurn() {
            switch (this) {
                case HumanPlayer:
                    return ComputerPlayer;
                case ComputerPlayer:
                    return HumanPlayer;
                default:
                    return HumanPlayer;
            }
        }
    }

    private int mWidth;
    private int mHeight;
    private int[] mShips;
    private Player mCurrentPlayer;
    private Board mPlayerBoard, mCoumputerBoard;

    public Game(Level level) {
        setLevelSettings(level);
        mPlayerBoard = new Board(mWidth, mHeight, mShips);
        mCoumputerBoard = new Board(mWidth, mHeight, mShips);
        mCurrentPlayer = Player.HumanPlayer;
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

    public Player getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void changeTurn() {
        mCurrentPlayer = mCurrentPlayer.changeTurn();
    }
}

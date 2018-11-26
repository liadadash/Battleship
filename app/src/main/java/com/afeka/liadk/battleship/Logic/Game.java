package com.afeka.liadk.battleship.Logic;

public class Game {
    private Player mCurrentPlayer;
    private Player mComputerPlayer;
    private Player mHumanPlayer;

    public Game(int weight, int height) {
        mComputerPlayer = new ComputerPlayer(weight, height);
        mHumanPlayer = new HumanPlayer(weight, height);
        mCurrentPlayer = mHumanPlayer;
    }

    public void changeTurn() {
        if (mCurrentPlayer == mHumanPlayer)
            mCurrentPlayer = mComputerPlayer;
        else
            mCurrentPlayer = mHumanPlayer;
    }

}

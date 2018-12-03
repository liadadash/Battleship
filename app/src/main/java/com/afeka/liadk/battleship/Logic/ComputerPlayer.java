package com.afeka.liadk.battleship.Logic;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer {

    private enum Direction {
        vertical, horizon;

        public Direction Change() {
            switch (this) {
                case vertical:
                    return horizon;
                case horizon:
                    return vertical;
            }
            return null;
        }
    }

    private int mWidth;
    private int mHeight;
    private Board mBorad;
    private boolean hitShip, goodDirection, endLine, changeDirection, missed;
    private int mHitX, mHitY, mIndex;
    private Direction mLastDirection;
    private ArrayList<Point> arrayList;

    public ComputerPlayer(GameSettingsInterface.Level level, Board board) {
        mWidth = level.getWidth();
        mHeight = level.getHeight();
        mBorad = board;
        hitShip = missed = goodDirection = false;
        arrayList = new ArrayList<>();
    }

    private void think() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean shoot(Direction direction, int index) {
        int x = mHitX, y = mHitY, pos;
        if (direction == Direction.vertical) {
            x += index;
            if (x > mWidth - 1 || x < 0)
                return false;
        } else {
            y += index;
            if (y > mHeight - 1 || y < 0)
                return false;
        }
        pos = y * mWidth + x;
        if (pos >= (mWidth * mHeight))
            return false;
        Tile tile = mBorad.getTile(pos);
        if (tile.getStatus() == Tile.TileState.NONE || tile.getStatus() == Tile.TileState.SHIP) {
            mBorad.attackTheBoard(pos);
            if (tile.getStatus() != Tile.TileState.MISS) {
                mLastDirection = direction;
                goodDirection = true;
                arrayList.add(new Point(x, y));
            }
            return true;
        }
        goodDirection = false;
        return false;
    }

    public void play() {
        removeDrowned();
        Tile tile;
        boolean isShoot = false;
        if (!hitShip) {
            if (arrayList.size() != 0) {
                hitShip = true;
                mHitY = arrayList.get(0).y;
                mHitX = arrayList.get(0).x;
                goodDirection = endLine = changeDirection = false;
                play();
                return;
            }
            Random random = new Random();
            do {
                mHitX = random.nextInt(mWidth);
                mHitY = random.nextInt(mHeight);
                tile = mBorad.getTile(mHitY * mWidth + mHitX);
            }
            while (tile.getStatus() != Tile.TileState.SHIP && tile.getStatus() != Tile.TileState.NONE);
            mBorad.attackTheBoard(mHitY * mWidth + mHitX);
            if (tile.getStatus() == Tile.TileState.INJURED_WITH_SHIPS) {
                hitShip = true;
                mIndex = 0;
                arrayList.add(new Point(mHitX, mHitY));
            }
            goodDirection = endLine = changeDirection = false;
            isShoot = true;
        } else {
            int pos;
            if (!goodDirection) {
                mIndex = 1;
                isShoot = shoot(Direction.vertical, mIndex);//left
                if (!isShoot)
                    isShoot = shoot(Direction.horizon, mIndex);//down
                if (!isShoot) {
                    mIndex = -1;
                    isShoot = shoot(Direction.vertical, mIndex);//right
                    if (!isShoot)
                        isShoot = shoot(Direction.horizon, mIndex);//up
                }
            } else {
                if (!missed) {
                    if (mIndex > 0)
                        mIndex++;
                    else
                        mIndex--;
                } else
                    missed = false;
                isShoot = shoot(mLastDirection, mIndex);
                int tempX = mHitX, tempY = mHitY;
                if (mLastDirection == Direction.vertical)
                    tempX += mIndex;
                else
                    tempY += mIndex;
                Tile.TileState state = mBorad.getTile(tempY * mWidth + tempX).getStatus();
                if (!isShoot || state == Tile.TileState.MISS) {
                    goodDirection = true;
                    missed = true;
                    if (!endLine) {
                        if (mIndex > 0)
                            mIndex = -1;
                        else
                            mIndex = 1;
                        endLine = true;
                        if (!isShoot) {
                            play();
                            return;
                        }
                    } else {
                        if (!changeDirection) {
                            mIndex = 1;
                            mLastDirection = mLastDirection.Change();
                            endLine = false;
                            changeDirection = true;
                            if (!isShoot) {
                                play();
                                return;
                            }
                        } else {
                            if (arrayList.size() != 0)
                                arrayList.remove(0);
                            hitShip = goodDirection = endLine = changeDirection = false;
                            if (!isShoot) {
                                play();
                                return;
                            }
                        }
                    }
                }
            }
        }
        if (isShoot) {
            think();
        }
    }

    public void removeDrowned() {
        Point point;
        for (int i = 0; i < arrayList.size(); i++) {
            point = arrayList.get(i);
            if (mBorad.getTile(point.y * mWidth + point.x).getStatus() == Tile.TileState.DROWNED) {
                arrayList.remove(i);
                if (mHitX == point.x && mHitY == point.y)
                    hitShip = false;
            }
        }
    }
}
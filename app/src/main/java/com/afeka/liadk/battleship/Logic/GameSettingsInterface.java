package com.afeka.liadk.battleship.Logic;

public interface GameSettingsInterface {

    enum Level {
        Easy, Medium, Hard;

        private int widthEasy = 4;
        private int heightEasy = 4;
        private int[] shipsEasys = {2, 3, 2};
        private int playerBoardWidthEasy = 140;
        private int playerBoardHeightEasy = 140;
        private int computerBoardWidthEasy = 180;
        private int computerBoardHeightEasy = 160;

        private int widthMedium = 6;
        private int heightMedium = 6;
        private int[] shipsMedium = {2, 3, 2, 2};
        private int playerBoardWidthMedium = 140;
        private int playerBoardHeightMedium = 150;
        private int computerBoardWidthMedium = 140;
        private int computerBoardHeightMedium = 150;

        private int widthHard = 8;
        private int heightHard = 8;
        private int[] shipsHard = {2, 2, 2, 2};
        private int playerBoardWidthHard = 140;
        private int playerBoardHeightHard = 150;
        private int computerBoardWidthHard = 140;
        private int computerBoardHeightHard = 150;

        public int getWidth() {
            switch (this) {
                case Easy:
                    return widthEasy;
                case Medium:
                    return widthMedium;
                case Hard:
                    return widthHard;
                default:
                    return widthEasy;
            }
        }

        public int getHeight() {
            switch (this) {
                case Easy:
                    return heightEasy;
                case Medium:
                    return heightMedium;
                case Hard:
                    return heightHard;
                default:
                    return heightEasy;
            }
        }

        public int[] getShips() {
            switch (this) {
                case Easy:
                    return shipsEasys;
                case Medium:
                    return shipsMedium;
                case Hard:
                    return shipsHard;
                default:
                    return shipsEasys;
            }
        }

        public int getPlayerBoardWidth() {
            switch (this) {
                case Easy:
                    return playerBoardWidthEasy;
                case Medium:
                    return playerBoardWidthMedium;
                case Hard:
                    return playerBoardWidthHard;
                default:
                    return playerBoardWidthEasy;
            }
        }

        public int getPlayerBoardHeight() {
            switch (this) {
                case Easy:
                    return playerBoardHeightEasy;
                case Medium:
                    return playerBoardHeightMedium;
                case Hard:
                    return playerBoardHeightHard;
                default:
                    return playerBoardHeightEasy;
            }
        }

        public int getComputerBoardWidth() {
            switch (this) {
                case Easy:
                    return computerBoardWidthEasy;
                case Medium:
                    return computerBoardWidthMedium;
                case Hard:
                    return computerBoardWidthHard;
                default:
                    return computerBoardWidthEasy;
            }
        }

        public int getComputerBoardHeight() {
            switch (this) {
                case Easy:
                    return computerBoardHeightEasy;
                case Medium:
                    return computerBoardHeightMedium;
                case Hard:
                    return computerBoardHeightHard;
                default:
                    return computerBoardHeightEasy;
            }
        }
    }
}

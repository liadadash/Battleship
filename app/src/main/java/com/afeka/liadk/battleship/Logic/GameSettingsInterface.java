package com.afeka.liadk.battleship.Logic;

public interface GameSettingsInterface {

    enum Level {
        Easy, Medium, Hard;

        private int widthEasy = 4;
        private int heightEasy = 4;
        private int[] shipsEasys = {2, 3, 2};

        private int widthMedium = 6;
        private int heightMedium = 6;
        private int[] shipsMedium = {2, 3, 2, 2};

        private int widthHard = 8;
        private int heightHard = 8;
        private int[] shipsHard = {2, 2, 2, 2};

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
    }
}

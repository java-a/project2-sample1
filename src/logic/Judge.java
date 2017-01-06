package logic;

/**
 * Created by cjy970910 on 2016/11/28.
 */
public  class Judge {

    public static boolean ifOutOfBoard(Animal animal, int[] direction) {
        return animal.getX() + direction[0] < 0 || animal.getX() + direction[0] > 6 || animal.getY() + direction[1] < 0 || animal.getY() + direction[1] > 8;
    }

    public static boolean ifRiver(Animal animal, int[] direction) {
        return Board.landForm[animal.getX() + direction[0]][animal.getY() + direction[1]] == 1;
    }

    public static boolean ifHome(Animal animal, int[] direction) {
        return Board.landForm[animal.getX() + direction[0]][animal.getY() + direction[1]] == (animal.getCamp() == -1 ? 3 : 5);
    }

    public static boolean ifVacant(Animal animal, int[] direction) {
        return Board.animalBoard[animal.getX() + direction[0]][animal.getY() + direction[1]].getValue() == 0;
    }

    /**
     * judge whether the aniaml on next location is enemy.
     * it is used only when the judgeIfVacant is false.
     *
     * @return true means yes and false means no.
     */
    public static boolean ifAnimalEn(Animal animal, int[] direction) {
        return Board.animalBoard[animal.getX() + direction[0]][animal.getY() + direction[1]].getCamp() != animal.getCamp();
    }

    /**
     * judge whether the aniaml on next location can be eaten.
     * it is used only when the judgeIfAnimalEn is true.
     *
     * @return true means yes and false means no.
     */
    public static boolean ifAnimalCanEat(Animal animal, int[] direction) {
        if (Board.landForm[animal.getX() + direction[0]][animal.getY() + direction[1]] == (animal.getCamp() == -1 ? 2 : 4)) {
            return true;
        }
        int animalValue = animal.getValue();
        int targetValue = Board.animalBoard[animal.getX() + direction[0]][animal.getY() + direction[1]].getValue();

        if (animalValue == 1) {
            return targetValue == 1 | (Board.landForm[animal.getX()][animal.getY()] != 1 & targetValue == 8);
        } else if (animalValue == 8) {
            return targetValue != 1;
        } else {
            return animalValue >= targetValue;
        }
    }

    /**
     * judge whether the tiger or lion can jump over the river.
     * this method only judge if there is a mouse in the route,not considering the condition on the opposite bank.
     *
     * @param direction the location the animal will arrive.
     * @return true means yes and false means no.
     */
    public static boolean ifCanJump(Animal animal, int[] direction) {
        if (direction[0] == 0 && direction[1] == 1) {
            return Board.animalBoard[animal.getX()][animal.getY() + 1].getCamp() != (animal.getCamp() == -1 ? 1 : -1) &&
                    Board.animalBoard[animal.getX()][animal.getY() + 2].getCamp() != (animal.getCamp() == -1 ? 1 : -1) &&
                    Board.animalBoard[animal.getX()][animal.getY() + 3].getCamp() != (animal.getCamp() == -1 ? 1 : -1);
        } else if (direction[0] == 0 && direction[1] == -1) {
            return Board.animalBoard[animal.getX()][animal.getY() - 1].getCamp() != (animal.getCamp() == -1 ? 1 : -1) &&
                    Board.animalBoard[animal.getX()][animal.getY() - 2].getCamp() != (animal.getCamp() == -1 ? 1 : -1) &&
                    Board.animalBoard[animal.getX()][animal.getY() - 3].getCamp() != (animal.getCamp() == -1 ? 1 : -1);
        } else if (direction[0] == 1 && direction[1] == 0) {
            return Board.animalBoard[animal.getX() + 1][animal.getY()].getCamp() != (animal.getCamp() == -1 ? 1 : -1) &&
                    Board.animalBoard[animal.getX() + 2][animal.getY()].getCamp() != (animal.getCamp() == -1 ? 1 : -1);
        } else if (direction[0] == -1 && direction[1] == 0) {
            return Board.animalBoard[animal.getX() - 1][animal.getY()].getCamp() != (animal.getCamp() == -1 ? 1 : -1) &&
                    Board.animalBoard[animal.getX() - 2][animal.getY()].getCamp() != (animal.getCamp() == -1 ? 1 : -1);
        } else {
            return false;
        }


    }

    public static boolean ifHomeOccupied() {
        return Board.animalBoard[3][0].getValue() != 0 || Board.animalBoard[3][8].getValue() != 0;
    }

    /**
     * end judge
     * judge if there is animal alive.
     * it scans the map from (0,0) to (6,8),when it found an animal of the enemy,it break.
     * when it go to (6,8) and still no animal has been found,it means the animal of the enemy has all died.
     *
     * @return true means yes and false means no.
     */
    public static boolean ifDead() {
        int leftAlive = 0;
        int rightAlive = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                if (Board.animalBoard[i][j].getCamp() == -1) {
                    leftAlive++;
                } else if (Board.animalBoard[i][j].getCamp() == 1) {
                    rightAlive++;
                }
            }
        }
        return leftAlive == 0 || rightAlive == 0;
    }

    public static boolean ifTrapped() {
        int leftTrapped = 0;
        int rightTrapped = 0;
        int leftAlive = 0;
        int rightAlive = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                int[] directionUp = {-1, 0};
                int[] directionDown = {1, 0};
                int[] directionLeft = {0, -1};
                int[] directionRight = {0, 1};
                if (Board.animalBoard[i][j].getCamp() == -1) {
                    leftAlive++;
                    if (!Board.animalBoard[i][j].move(directionUp) && !Board.animalBoard[i][j].move(directionDown) && !Board.animalBoard[i][j].move(directionLeft) && !Board.animalBoard[i][j].move(directionRight)) {
                        leftTrapped++;
                    }
                } else if (Board.animalBoard[i][j].getCamp() == 1) {
                    rightAlive++;
                    if (!Board.animalBoard[i][j].move(directionUp) && !Board.animalBoard[i][j].move(directionDown) && !Board.animalBoard[i][j].move(directionLeft) && !Board.animalBoard[i][j].move(directionRight)) {
                        rightTrapped++;
                    }
                }
            }
        }
        return leftTrapped == leftAlive || rightTrapped == rightAlive;
    }

    public static boolean ifDanger(boolean player) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                int[] directionUp = {-1, 0};
                int[] directionDown = {1, 0};
                int[] directionLeft = {0, -1};
                int[] directionRight = {0, 1};
                if (Board.animalBoard[i][j].getCamp() == (player ? 1 : -1)) {
                    try {
                        if (Board.animalBoard[i][j].move(directionUp) & Board.animalBoard[i + directionUp[0]][j + directionUp[1]].getCamp() == (player ? -1 : 1)) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (Board.animalBoard[i][j].move(directionDown) & Board.animalBoard[i + directionDown[0]][j + directionDown[1]].getCamp() == (player ? -1 : 1)) {
                            return true;
                        }
                    } catch (Exception e) {
                    }

                    try {
                        if (Board.animalBoard[i][j].move(directionLeft) & Board.animalBoard[i + directionLeft[0]][j + directionLeft[1]].getCamp() == (player ? -1 : 1)) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (Board.animalBoard[i][j].move(directionRight) & Board.animalBoard[i + directionRight[0]][j + directionRight[1]].getCamp() == (player ? -1 : 1)) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return false;
    }

}

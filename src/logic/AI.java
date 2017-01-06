package logic;

/**
 * Created by cjy970910 on 2017/1/3.
 */
public class AI {
    private Animal animalChosen = new Animal();

    protected boolean move() {
    if (GameController.historyBoard.animalBoardRecord.size() == 2) {
            animalChosen = Board.animalBoard[0][6];
            animalChosen.go(new int[]{0, -1});
            return true;
        }
        if (GameController.historyBoard.animalBoardRecord.size() == 4) {
            animalChosen = Board.animalBoard[0][5];
            animalChosen.go(new int[]{0, -1});
            return true;
        }
        if (GameController.historyBoard.animalBoardRecord.size() == 6) {
            animalChosen = Board.animalBoard[0][4];
            animalChosen.go(new int[]{0, -1});
            return true;
        }

        if (Eat()) {
            return true;
        }

        if (Escape()) {
            return true;
        }

        if (attack()) {
            return true;
        }

        randomMove();
        return true;
    }

    private boolean Eat() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                int[] directionUp = {-1, 0};
                int[] directionDown = {1, 0};
                int[] directionLeft = {0, -1};
                int[] directionRight = {0, 1};
                if (Board.animalBoard[i][j].getCamp() == 1) {
                    animalChosen = Board.animalBoard[i][j];
                    if (ifCanEat(animalChosen, directionUp)) {
                        animalChosen.go(directionUp);
                        return true;
                    } else if (ifCanEat(animalChosen, directionDown)) {
                        animalChosen.go(directionDown);
                        return true;
                    } else if (ifCanEat(animalChosen, directionLeft)) {
                        animalChosen.go(directionLeft);
                        return true;
                    } else if (ifCanEat(animalChosen, directionRight)) {
                        animalChosen.go(directionRight);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean Escape() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                int[] directionUp = {-1, 0};
                int[] directionDown = {1, 0};
                int[] directionLeft = {0, -1};
                int[] directionRight = {0, 1};
                if (Board.animalBoard[i][j].getCamp() == 1) {
                    animalChosen = Board.animalBoard[i][j];
                    if (ifDanger(animalChosen, directionUp)) {
                        if (animalChosen.move(directionLeft)) {
                            animalChosen.go(directionLeft);
                            return true;
                        }
                        if (animalChosen.move(directionDown)) {
                            animalChosen.go(directionDown);
                            return true;
                        }
                        if (animalChosen.move(directionRight)) {
                            animalChosen.go(directionRight);
                            return true;
                        }
                    }
                } else if (ifDanger(animalChosen, directionDown)) {
                    if (animalChosen.move(directionLeft)) {
                        animalChosen.go(directionLeft);
                        return true;
                    }
                    if (animalChosen.move(directionUp)) {
                        animalChosen.go(directionUp);
                        return true;
                    }
                    if (animalChosen.move(directionRight)) {
                        animalChosen.go(directionRight);
                        return true;

                    }
                } else if (ifDanger(animalChosen, directionLeft)) {
                    if (animalChosen.move(directionUp)) {
                        animalChosen.go(directionUp);
                        return true;
                    }
                    if (animalChosen.move(directionDown)) {
                        animalChosen.go(directionDown);
                        return true;
                    }
                    if (animalChosen.move(directionRight)) {
                        animalChosen.go(directionRight);
                        return true;
                    }
                } else if (ifDanger(animalChosen, directionRight)) {
                    if (animalChosen.move(directionLeft)) {
                        animalChosen.go(directionLeft);
                        return true;
                    }
                    if (animalChosen.move(directionDown)) {
                        animalChosen.go(directionDown);
                        return true;
                    }
                    if (animalChosen.move(directionUp)) {
                        animalChosen.go(directionUp);
                        return true;

                    }
                }
            }
        }
        return false;
    }

    private boolean ifDanger(Animal animal, int[] direction) {
        int[] direction2 = new int[2];
        direction2[0] = -direction[0];
        direction2[1] = -direction[1];
        return !Judge.ifOutOfBoard(animal, direction) && !Judge.ifVacant(animal, direction) && Judge.ifAnimalEn(animal, direction) && Board.animalBoard[animal.getX() + direction[0]][animal.getY() + direction[1]].move(direction2);
    }

    private boolean ifCanEat(Animal animal, int[] direction) {
        return !Judge.ifOutOfBoard(animal, direction) && !Judge.ifVacant(animal, direction) && Judge.ifAnimalEn(animal, direction) && animal.move(direction);
    }

    private boolean randomMove() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                int[] directionUp = {-1, 0};
                int[] directionDown = {1, 0};
                int[] directionLeft = {0, -1};
                int[] directionRight = {0, 1};
                if (Board.animalBoard[i][j].getCamp() == 1) {
                    animalChosen = Board.animalBoard[i][j];
                    if (animalChosen.move(directionLeft)) {
                        animalChosen.go(directionLeft);
                        if (ifDanger(animalChosen, directionUp) || ifDanger(animalChosen, directionDown) || ifDanger(animalChosen, directionLeft) || ifDanger(animalChosen, directionRight)) {
                            animalChosen.go(directionRight);
                        } else {
                            return true;
                        }
                    } else if (animalChosen.move(directionDown)) {
                        animalChosen.go(directionDown);
                        if (ifDanger(animalChosen, directionUp) || ifDanger(animalChosen, directionDown) || ifDanger(animalChosen, directionLeft) || ifDanger(animalChosen, directionRight)) {
                            animalChosen.go(directionUp);
                        } else {
                            return true;
                        }
                    } else if (animalChosen.move(directionRight)) {
                        animalChosen.go(directionRight);
                        if (ifDanger(animalChosen, directionUp) || ifDanger(animalChosen, directionDown) || ifDanger(animalChosen, directionLeft) || ifDanger(animalChosen, directionRight)) {
                            animalChosen.go(directionLeft);
                        } else {
                            return true;
                        }
                    } else if (animalChosen.move(directionUp)) {
                        animalChosen.go(directionUp);
                        if (ifDanger(animalChosen, directionUp) || ifDanger(animalChosen, directionDown) || ifDanger(animalChosen, directionLeft) || ifDanger(animalChosen, directionRight)) {
                            animalChosen.go(directionDown);
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean attack() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                int[] directionUp = {-1, 0};
                int[] directionDown = {1, 0};
                int[] directionLeft = {0, -1};
                int[] directionRight = {0, 1};
                if (Board.animalBoard[i][j].getCamp() == 1) {
                    animalChosen = Board.animalBoard[i][j];
                    if (animalChosen.move(directionLeft)) {
                        animalChosen.go(directionLeft);
                        if ((ifCanEat(animalChosen, directionUp) || ifCanEat(animalChosen, directionDown) || ifCanEat(animalChosen, directionLeft) || ifCanEat(animalChosen, directionRight)) && !(ifDanger(animalChosen, directionUp) || ifDanger(animalChosen, directionDown) || ifDanger(animalChosen, directionLeft) || ifDanger(animalChosen, directionRight))) {
                            return true;
                        } else {
                            animalChosen.go(directionRight);
                        }
                    } else if (animalChosen.move(directionDown)) {
                        animalChosen.go(directionDown);
                        if ((ifCanEat(animalChosen, directionUp) || ifCanEat(animalChosen, directionDown) || ifCanEat(animalChosen, directionLeft) || ifCanEat(animalChosen, directionRight)) && !(ifDanger(animalChosen, directionUp) || ifDanger(animalChosen, directionDown) || ifDanger(animalChosen, directionLeft) || ifDanger(animalChosen, directionRight))) {
                            return true;
                        } else {
                            animalChosen.go(directionUp);
                        }
                    } else if (animalChosen.move(directionUp)) {
                        animalChosen.go(directionUp);
                        if ((ifCanEat(animalChosen, directionUp) || ifCanEat(animalChosen, directionDown) || ifCanEat(animalChosen, directionLeft) || ifCanEat(animalChosen, directionRight)) && !(ifDanger(animalChosen, directionUp) || ifDanger(animalChosen, directionDown) || ifDanger(animalChosen, directionLeft) || ifDanger(animalChosen, directionRight))) {
                            return true;
                        } else {
                            animalChosen.go(directionDown);
                        }
                    } else if (animalChosen.move(directionRight)) {
                        animalChosen.go(directionRight);
                        if ((ifCanEat(animalChosen, directionUp) || ifCanEat(animalChosen, directionDown) || ifCanEat(animalChosen, directionLeft) || ifCanEat(animalChosen, directionRight)) && !(ifDanger(animalChosen, directionUp) || ifDanger(animalChosen, directionDown) || ifDanger(animalChosen, directionLeft) || ifDanger(animalChosen, directionRight))) {
                            return true;
                        } else {
                            animalChosen.go(directionLeft);
                        }
                    }
                }
            }
        }
        return false;
    }
}

package logic;

import logic.SpecialAnimals.Lion;
import logic.SpecialAnimals.Mouse;
import logic.SpecialAnimals.Tiger;
import view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cjy970910 on 2016/11/28.
 */
class History {
    ArrayList<Animal[][]> animalBoardRecord = new ArrayList<>();

    void addBoards() {
        Animal[][] temp = new Animal[7][9];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                temp[i][j] = Board.animalBoard[i][j];
            }
        }
        animalBoardRecord.add(temp);
    }

    void save(int number, View view) {
        String savePath = "save/save" + number + ".txt";
        String historyPath = "save/history" + number;
        //write in the last board
        try {
            FileWriter save = new FileWriter(savePath);
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    if (Board.animalBoard[i][j].getCamp() == -1) {
                        save.write((char) (Board.animalBoard[i][j].getValue() + 96) + "");
                    } else {
                        save.write("" + (Board.animalBoard[i][j].getValue()));
                    }
                }
                save.write("\r\n");
                save.flush();
            }
            //write in camp
            if (View.player) {
                save.write("-1");
                save.flush();
            } else {
                save.write("1");
                save.flush();
            }
            //write in time
            save.write("\r\n");
            save.write("" + view.getSecondsAll());
            save.flush();
            save.close();
        } catch (IOException e) {
        }
        //write in the history
        for (int k = 0; k < 10000; k++) {
            File history = new File(historyPath, "history" + k + ".txt");
            if (history.exists()) {
                history.delete();
            } else {
                break;
            }
        }
        for (int k = 0; k < animalBoardRecord.size(); k++) {
            File history = new File(historyPath, "history" + k + ".txt");
            if (!history.exists()) {
                try {
                    history.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileWriter save = new FileWriter(history);
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (animalBoardRecord.get(k)[i][j].getCamp() == -1) {
                                save.write((char) (animalBoardRecord.get(k)[i][j].getValue() + 96) + "");
                            } else {
                                save.write("" + animalBoardRecord.get(k)[i][j].getValue());
                            }
                        }
                        save.write("\r\n");
                        save.flush();
                    }
                    save.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void undo() {
        try {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    Board.animalBoard[i][j] = animalBoardRecord.get(animalBoardRecord.size() - 2)[i][j];
                    Board.animalBoard[i][j].setX(i);
                    Board.animalBoard[i][j].setY(j);
                }
            }
            animalBoardRecord.remove(animalBoardRecord.size() - 1);
            animalBoardRecord.remove(animalBoardRecord.size() - 1);
        } catch (Exception e) {
        }
    }

    void loadHistory(int number) {
        int[][] temp = new int[7][9];
        Scanner scannerAnimal = null;
        for (int k = 0; k < 10000; k++) {
            Animal[][] tempAnimal = new Animal[7][9];
            File newFile = new File("save/history" + number + "/history" + k + ".txt");
            if (newFile.exists()) {
                try {
                    scannerAnimal = new Scanner(newFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
            for (int i = 0; i < 7; i++) {
                assert scannerAnimal != null;
                if (scannerAnimal.hasNext()) {
                    String animal = scannerAnimal.nextLine();
                    for (int j = 0; j < 9; j++) {
                        temp[i][j] = animal.charAt(j);
                    }
                }
            }
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    if (temp[i][j] > 96) {
                        temp[i][j] = temp[i][j] - 96;
                        if (temp[i][j] == 1) {
                            tempAnimal[i][j] = new Mouse(temp[i][j], i, j);
                        } else if (temp[i][j] == 6) {
                            tempAnimal[i][j] = new Tiger(temp[i][j], i, j);
                        } else if (temp[i][j] == 7) {
                            tempAnimal[i][j] = new Lion(temp[i][j], i, j);
                        } else {
                            tempAnimal[i][j] = new Animal(temp[i][j], i, j);
                        }
                        if (tempAnimal[i][j].getValue() != 0) {
                            tempAnimal[i][j].setCamp(-1);
                        }
                    } else {
                        temp[i][j] = temp[i][j] - 48;
                        if (temp[i][j] == 1) {
                            tempAnimal[i][j] = new Mouse(temp[i][j], i, j);
                        } else if (temp[i][j] == 6) {
                            tempAnimal[i][j] = new Tiger(temp[i][j], i, j);
                        } else if (temp[i][j] == 7) {
                            tempAnimal[i][j] = new Lion(temp[i][j], i, j);
                        } else {
                            tempAnimal[i][j] = new Animal(temp[i][j], i, j);
                        }
                        if (tempAnimal[i][j].getValue() != 0) {
                            tempAnimal[i][j].setCamp(1);
                        }
                    }
                }
            }
            animalBoardRecord.add(tempAnimal);
        }
    }

    void clear() {
        animalBoardRecord.clear();
    }
}

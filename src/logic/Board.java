package logic;

import javafx.scene.layout.Border;
import logic.SpecialAnimals.Lion;
import logic.SpecialAnimals.Mouse;
import logic.SpecialAnimals.Tiger;
import view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by cjy970910 on 2016/11/28.
 */
public class Board {
    public static int[][] landForm = new int[7][9];
    public static Animal[][] animalBoard = new Animal[7][9];//行，列

    public Board() {
        this(readAnimalMap());
        readMap();
    }

    public Board(int save, View view) {
        this(readSaveMap(save, view));
        readMap();
    }

    public Board(int[][] map) {
        setAnimalBoard(map);
    }

    private void setAnimalBoard(int[][] map) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                if (map[i][j] > 96) {
                    map[i][j] = map[i][j] - 96;
                    if (map[i][j] == 1) {
                        animalBoard[i][j] = new Mouse(map[i][j], i, j);
                    } else if (map[i][j] == 6) {
                        animalBoard[i][j] = new Tiger(map[i][j], i, j);
                    } else if (map[i][j] == 7) {
                        animalBoard[i][j] = new Lion(map[i][j], i, j);
                    } else {
                        animalBoard[i][j] = new Animal(map[i][j], i, j);
                    }
                    if (animalBoard[i][j].getValue() != 0) {
                        animalBoard[i][j].setCamp(-1);
                    }
                } else {
                    map[i][j] = map[i][j] - 48;
                    if (map[i][j] == 1) {
                        animalBoard[i][j] = new Mouse(map[i][j], i, j);
                    } else if (map[i][j] == 6) {
                        animalBoard[i][j] = new Tiger(map[i][j], i, j);
                    } else if (map[i][j] == 7) {
                        animalBoard[i][j] = new Lion(map[i][j], i, j);
                    } else {
                        animalBoard[i][j] = new Animal(map[i][j], i, j);
                    }
                    if (animalBoard[i][j].getValue() != 0) {
                        animalBoard[i][j].setCamp(1);
                    }
                }
            }
        }
    }

    private void readMap() {
        Scanner scannerMap = null;
        try {
            scannerMap = new Scanner(new File("map.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 7; i++) {
            if (scannerMap == null) throw new AssertionError();
            String map = scannerMap.nextLine();
            for (int j = 0; j < 9; j++) {
                Board.landForm[i][j] = map.charAt(j) - 48;
            }
        }
    }

    private static int[][] readAnimalMap() {
        int[][] animalMap = new int[7][9];
        Scanner scannerAnimal = null;
        try {
            scannerAnimal = new Scanner(new File("animal.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 7; i++) {
            if (scannerAnimal == null) throw new AssertionError();
            String animal = scannerAnimal.nextLine();
            for (int j = 0; j < 9; j++) {
                animalMap[i][j] = animal.charAt(j);
            }
        }
        return animalMap;
    }

    private static int[][] readSaveMap(int number, View view) {
        String loadPath = "save/save" + number + ".txt";
        int[][] animalMap = new int[7][9];
        Scanner scannerAnimal = null;
        try {
            scannerAnimal = new Scanner(new File(loadPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 7; i++) {
            if (scannerAnimal == null) throw new AssertionError();
            String animal = scannerAnimal.nextLine();
            for (int j = 0; j < 9; j++) {
                animalMap[i][j] = animal.charAt(j);
            }
        }
        String camp = scannerAnimal.nextLine();
        View.player = Objects.equals(camp, "-1");

        String second = scannerAnimal.nextLine();
        int secondsAll = Integer.parseInt(second);
        view.setSecondsAll(secondsAll);

        return animalMap;
    }

}


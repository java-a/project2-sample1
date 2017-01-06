package logic;

import view.View;

/**
 * Created by cjy970910 on 2016/12/15.
 */
public class GameController {
    private static Board gameBoard = new Board();
    protected static History historyBoard = new History();
    private Animal animalChosen;
    private int[] direction = new int[2];

    public GameController() {
    }

    public GameController(int number, View view) {
        gameBoard = new Board(number, view);
    }

    public void play() {
        historyBoard.addBoards();
        if (animalChosen == null) {
            View.player = !View.player;
        } else if (animalChosen.move(direction)) {
            animalChosen.go(direction);
            View.player = !View.player;
        }
    }

    public boolean attention() {
        return Judge.ifDanger(View.player);
    }

    public static void undo() {
        historyBoard.undo();
    }

    public static void save(int number, View view) {
        historyBoard.save(number, view);
    }

    public static void load(int number) {
        historyBoard.loadHistory(number);
    }

    public static void clear() {
        historyBoard.clear();
    }

    public void setAnimal(int x, int y) {
        if (x >= 0 & y >= 0 & Board.animalBoard[x][y].getValue() != 0) {
            animalChosen = Board.animalBoard[x][y];
        } else {
            animalChosen = null;
        }
    }

    public void setDirection(int x, int y) {
        direction[0] = x;
        direction[1] = y;
    }

    public boolean ifEnd() {
        return Judge.ifHomeOccupied() || Judge.ifDead() || Judge.ifTrapped();
    }

    public void AI(){
        historyBoard.addBoards();
        View.player = !View.player;
        AI AI = new AI();
        AI.move();
    }

}
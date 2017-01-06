package logic;

/**
 * Created by cjy970910 on 2016/11/28.
 */
public class Animal {
    private int value;
    private int camp;        //left:-1 ; right:1
    private int x;
    private int y;

    public Animal() {
    }

    public Animal(int value, int x, int y) {
        this.camp = 0;
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCamp() {
        return camp;
    }

    public void setCamp(int camp) {
        this.camp = camp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean move(int[] direction) {
        return !(Judge.ifOutOfBoard(this, direction) || Judge.ifHome(this, direction) || Judge.ifRiver(this, direction)) && (Judge.ifVacant(this, direction) || Judge.ifAnimalEn(this, direction) && Judge.ifAnimalCanEat(this, direction));
    }

    /**
     * finish the movement when it can move.
     *
     * @param direction the location the animal will arrive.
     */
    public void go(int[] direction) {
        Board.animalBoard[this.getX()][this.getY()] = new Animal(0, this.getX(), this.getY());
        this.setX(this.getX() + direction[0]);
        this.setY(this.getY() + direction[1]);
        Board.animalBoard[this.getX()][this.getY()] = this;
    }

    public void jumpRiverDirection(int[] direction) {
    }

}

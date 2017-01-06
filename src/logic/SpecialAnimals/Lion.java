package logic.SpecialAnimals;

import logic.Animal;
import logic.Judge;

/**
 * Created by cjy970910 on 2016/12/16.
 */
public class Lion extends Animal {
    public Lion(int value, int x, int y) {
        super(value, x, y);
    }

    @Override
    public boolean move(int[] direction) {
        if (Judge.ifOutOfBoard(this, direction)) {
            return false;
        } else {
            if (Judge.ifRiver(this, direction)) {
                if (Judge.ifCanJump(this, direction)) {
                    jumpRiverDirection(direction);
                    return Judge.ifVacant(this, direction) || Judge.ifAnimalEn(this, direction) && Judge.ifAnimalCanEat(this, direction);
                } else {
                    return false;
                }
            } else {
                return !(Judge.ifHome(this, direction) || Judge.ifRiver(this, direction)) && (Judge.ifVacant(this, direction) || Judge.ifAnimalEn(this, direction) && Judge.ifAnimalCanEat(this, direction));
            }
        }
    }

    @Override
    public void jumpRiverDirection(int[] direction) {
        if (direction[0] == 0 && direction[1] == 1) {
            direction[0] = 0;
            direction[1] = 4;
        } else if (direction[0] == 0 && direction[1] == -1) {
            direction[0] = 0;
            direction[1] = -4;
        } else if (direction[0] == 1 && direction[1] == 0) {
            direction[0] = 3;
            direction[1] = 0;
        } else if (direction[0] == -1 && direction[1] == 0) {
            direction[0] = -3;
            direction[1] = 0;
        }
    }


}



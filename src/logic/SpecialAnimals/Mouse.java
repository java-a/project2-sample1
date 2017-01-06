package logic.SpecialAnimals;

import logic.Animal;
import logic.Judge;

/**
 * Created by cjy970910 on 2016/12/16.
 */
public class Mouse extends Animal {

    public Mouse(int value, int x, int y) {
        super(value,x,y);
    }

    @Override
    public boolean move(int[] direction) {
        return !(Judge.ifOutOfBoard(this, direction) || Judge.ifHome(this, direction)) && (Judge.ifVacant(this, direction) || Judge.ifAnimalEn(this, direction) && Judge.ifAnimalCanEat(this, direction));
    }

}

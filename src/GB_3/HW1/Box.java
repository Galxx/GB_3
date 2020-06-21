 package GB_3.HW1;

import java.util.ArrayList;

public class Box<T extends Fruit> {

    private ArrayList<T> box;

    public Box(){
        box = new ArrayList<T>();
    }

    public void add(T fruit){
        box.add(fruit);
    }

    public float getWight(){

        float wight = 0.0f;
        for (T o:box) {
            wight += o.getWight();
        }

        return wight;

    }

    public boolean compare(Box<?> anotherBox){

        if (this.getWight()>=anotherBox.getWight()){
            return true;
        }else{
            return false;
        }

    }

    public ArrayList<T> getAllFruit(){
        return box;
    }

    private void clearBox(){
        box.clear();
    }

    public void addAllFruitFromBox(Box<T> anotherBox){

        ArrayList<T> pileFruit = anotherBox.getAllFruit();
        for (T fruit:pileFruit) {
           this.box.add(fruit);
        }
        anotherBox.clearBox();

    }

}

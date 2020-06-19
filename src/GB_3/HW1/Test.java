package GB_3.HW1;

import java.util.ArrayList;
import java.util.Collections;

public class Test {


    public static void main(String[] args) {

        Task1();
        Task2();
        Task3();
    }

    static void Task1(){

        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        Orange orange1 = new Orange();
        Orange orange2 = new Orange();

        Fruit[] arr = {apple1,apple2,orange1,orange2};

        SwapHandler swapHandler = new SwapHandler();
        swapHandler.swapArr(arr,1,3);


    }

    static void Task2(){

        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        Orange orange1 = new Orange();
        Orange orange2 = new Orange();

        Fruit[] arrObject = {apple1,apple2,orange1,orange2};

        Converter converter = new Converter();
        Integer[] arrInteger = {12,123,123,434,34};
        int[]     arrInt    = {12,123,123,434,34};

       ArrayList result        = converter.covertArr(arrObject);
       ArrayList resultInteger = converter.covertArr(arrInteger);
       ArrayList resultInt     = converter.covertArr(arrInt);


    }

    static void Task3(){

        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        Apple apple3 = new Apple();
        Apple apple4 = new Apple();

        Box<Apple> box1 = new Box<>();
        box1.add(apple1);
        box1.add(apple2);

        Box<Apple> box3 = new Box<>();
        box3.add(apple3);
        box3.add(apple4);

        Orange orange1 = new Orange();
        Orange orange2 = new Orange();

        Box<Orange> box2 = new Box<>();
        box2.add(orange1);
        box2.add(orange2);

        System.out.println(""+box2.compare(box1));
        box1.addAllFruitFromBox(box3);
        System.out.println(""+box2.compare(box1));
    }


}

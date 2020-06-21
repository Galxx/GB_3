package GB_3.HW1;

import java.util.ArrayList;

public class Converter<T> {

    public ArrayList<T> covertArr(T[] arr){

        ArrayList<T> arrayList = new ArrayList<>();

        for (T o:arr) {
            arrayList.add(o);
        }

        return arrayList;
    }

    //Перегружаем метод для всех примитивных типов
    public ArrayList<Integer> covertArr(int[] arr){

        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int o:arr) {
            arrayList.add(o);
        }

        return arrayList;
    }

    public ArrayList<Float> covertArr(float[] arr){

        ArrayList<Float> arrayList = new ArrayList<>();

        for (float o:arr) {
            arrayList.add(o);
        }

        return arrayList;
    }






}

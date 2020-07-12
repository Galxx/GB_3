package HW6;

import java.util.ArrayList;

public class ArrayHandler {

    public ArrayList<Integer> arrHandler(int[] arr){

        ArrayList<Integer> arrResult = new ArrayList();
        int last4 = arr.length;

        for (int i = 0; i < arr.length; i++) {

            if (arr[i] == 4){
                last4 = i;
            }

        }

        for (int i =last4+1; i < arr.length; i++) {
               arrResult.add(arr[i]);
        }

        return arrResult;
    }
}

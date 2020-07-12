package HW6;

public class ArrayHandler2 {

    public boolean arrHandler(int[] arr) {

        boolean result = false;

        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == 1 || arr[i] == 4){
                result = true;
            }

        }

        return result;
    }

}

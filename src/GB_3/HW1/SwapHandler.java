package GB_3.HW1;

public class SwapHandler<T> {

    public   void swapArr(T[] arr, int i, int j) {

        T buffer;
        buffer = arr[i];
        arr[i] = arr[j];
        arr[j] = buffer;

    }

}


import HW6.ArrayHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class ArrayHandlerTest {

    private static ArrayHandler arrayHandler;

    private int[] arr;
    private ArrayList<Integer> arrResult;

    public ArrayHandlerTest(int[] arr, ArrayList<Integer> arrResult) {
        this.arr = arr;
        this.arrResult = arrResult;
    }

    @Parameters
    public static Collection setParam() {

        ArrayList<Integer> arrTest1 = new ArrayList<>();
        arrTest1.add(8);

        ArrayList<Integer> arrTest2 = new ArrayList<>();
        arrTest2.add(78);
        arrTest2.add(9);
        arrTest2.add(8);

        ArrayList<Integer> arrTest3 = new ArrayList<>();
        arrTest3.add(78);

        return Arrays.asList(new Object[][]{
                        {new int[]{1,2,3,4,78,4, 8}, arrTest1},
                        {new int[]{1,2,3,4,78,9, 8}, arrTest2},
                        {new int[]{1,2,3,4,78,4, 8}, arrTest3}
                }
        );
    }

    @BeforeClass
    public static void init() {
        System.out.println("init arrayHandler");
        arrayHandler = new ArrayHandler();
    }

    @Test
    public void paramTest() {

        Assert.assertEquals(arrResult, arrayHandler.arrHandler(arr));
    }


}

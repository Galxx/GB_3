
import HW6.ArrayHandler2;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class ArrayHandler2Test {

    private static ArrayHandler2 arrayHandler2;

    private int[] arr;
    private boolean result;

    public ArrayHandler2Test(int[] arr, boolean result) {
        this.arr = arr;
        this.result = result;
    }

    @Parameters
    public static Collection setParam() {
        
        return Arrays.asList(new Object[][]{
                        {new int[]{1,2,3,4,78,4, 8}, true},
                        {new int[]{8,2,3,8,78,9, 8}, false},
                        {new int[]{1,2,3,4,4,4, 8}, false}
                }
        );
    }

    @BeforeClass
    public static void init() {
        System.out.println("init arrayHandler");
        arrayHandler2 = new ArrayHandler2();
    }

    @Test
    public void paramTest() {

        Assert.assertEquals(result, arrayHandler2.arrHandler(arr));
    }

}

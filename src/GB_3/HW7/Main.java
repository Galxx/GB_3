package HW7;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        CalcTest ct = new CalcTest();
        ct.start(Calc.class);

    }
}

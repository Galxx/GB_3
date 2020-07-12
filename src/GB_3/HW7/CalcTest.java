package HW7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CalcTest {

    private class PriorityMetod{
       Method method;
       int priority;

       PriorityMetod(int priority,Method method){
           this.priority = priority;
           this.method   = method;
       }

    }

    private class SortbyPriority implements Comparator<PriorityMetod> {

        public int compare(PriorityMetod a, PriorityMetod b) {
            return a.priority - b.priority;
        }

    }

    public  void start(Class testClass) throws InvocationTargetException, IllegalAccessException {

        ArrayList<PriorityMetod> arrayTest = new ArrayList<>();
        ArrayList<Method> arrayBefore = new ArrayList<>();
        ArrayList<Method> arrayAfter = new ArrayList<>();

        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                Test test = m.getAnnotation(Test.class);
                arrayTest.add(new PriorityMetod(test.priority(),m));
            }else if(m.isAnnotationPresent(BeforeSuite.class)){
                arrayBefore.add(m);
            }else if(m.isAnnotationPresent(AfterSuite.class)){
                arrayAfter.add(m);
            }

        }

        if ((arrayBefore.size() > 1) || (arrayAfter.size() > 1)){
            throw new RuntimeException();
        }

        if(arrayBefore.size() ==1){
            arrayBefore.get(0).invoke(null);
        }

        Collections.sort(arrayTest, new SortbyPriority());
        for (int i = 0; i < arrayTest.size(); i++) {
            arrayTest.get(i).method.invoke(null);
        }

        if(arrayAfter.size() ==1){
            arrayAfter.get(0).invoke(null);
        }

    }

}



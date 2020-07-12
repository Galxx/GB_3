package HW7;

public class Calc {



    @BeforeSuite
    public static  void init(){
        System.out.println("Инициализация");
    }

//    @BeforeSuite
//    public static  void init2(){
//        System.out.println("Инициализация2");
//    }

    @Test(priority = 2)
    public static void add(){

        System.out.println("Сложение");
        int result = 5;
        int x1 = 2;
        int x2 = 3;

        if (x1+x2 == result){
            System.out.println("true");
        }else {
            System.out.println("false");
        }
    }

    @Test(priority = 1)
    public static void multiplication(){
        System.out.println("Умножение");
        int result = 5;
        int x1 = 2;
        int x2 = 3;

        if (x1*x2 == result){
            System.out.println("true");
        }else {
            System.out.println("false");
        }
    }

    @AfterSuite
    public static void endHandler(){
        System.out.println("Конец работы");
    }


}

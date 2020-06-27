package GB_3.HW4;

public class Task1 {
    private final Object mon = new Object();

    private char showChar = 'A';

    public static void main(String[] args) {
        Task1 w = new Task1();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    w.printA();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    w.printB();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    w.printC();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

    public void printA() {
        synchronized (mon) {
            try {
                while(showChar != 'A') {
                    mon.wait();
                }
                System.out.print("А");
                showChar = 'B';
                mon.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (mon) {
            try {
                while(showChar != 'B') {
                    mon.wait();
                }
                System.out.print("B");
                showChar = 'C';
                mon.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (mon) {
            try {
                while(showChar != 'C') {
                    mon.wait();
                }
                System.out.print("C");
                showChar = 'A';
                mon.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
package HW5;

import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static Boolean win = false;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier cb;

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(CyclicBarrier cb,Race race, int speed) {
        this.race  = race;
        this.speed = speed;
        this.cb    = cb;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cb.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            synchronized (win) {
                if (!win) {
                    win = true;
                    System.out.println(this.name + " - WIN");
                }
            }
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}

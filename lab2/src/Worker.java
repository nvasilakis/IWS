/**
 * Created by nikos on 1/31/14.
 */
public class Worker implements Runnable {
    String initText;
    String name;

    public Worker (String initText, String name) {
        this.initText = initText;
        this.name = name;
    }

    public void run() {
        System.out.println(initText);
        for (int j = 0; j < 10; j++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(name + ": shutting down..");
            }
            System.out.println(name + ": " + j);
        }
    }
}

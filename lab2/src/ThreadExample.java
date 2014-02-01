/**
 * Created by nikos on 1/31/14.
 */
import java.io.*; // for reading strings
import java.util.ArrayList;

public class ThreadExample {
    ArrayList<Thread> tPool;
    ArrayList<Worker> wPool;

    /**
     * Showcasing dynamic thread creation -- could have been static as well!
     *
     * @param args Command line arguments
     * @throws java.io.IOException To showcase how you document custom exceptions
     */
    public static void main(String[] args) throws IOException {
        String input = "";
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        ThreadExample te = new ThreadExample();
        //te.createThreads();

        while (!(input.equals("quit"))){
            input = in.readLine();
            if (!(input.equals("quit"))){
                te.createAThread(input);
            }
        }
    }
    public ThreadExample() {
        tPool = new ArrayList<Thread>();
        wPool = new ArrayList<Worker>();
    }

    public void createAThread(String str) {
        System.out.println("createAThread:" + str);

        String id = "Worker #" + wPool.size();
        // We can also pass a reference to the whole thread pool here!
        Worker worker = new Worker(str,id);
        Thread thread = new Thread(worker, id);
        wPool.add(worker);
        //System.out.println(str);
        tPool.add(thread);
        thread.start(); // run will sequence them!
    }

    // Or create a static number of theads!
    public void createThreads() {
        for (int i=0; i<10; i++){
            String id = "Worker #" + i;
            // We can also pass a reference to the whole thread pool here!
            Worker worker = new Worker("Static Init",id);
            wPool.add(worker);
            tPool.add(new Thread(worker, id));
        }
    }

}

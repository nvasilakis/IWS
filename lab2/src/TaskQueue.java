import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
    // It could be augmented objects
    private Queue<Socket> requests = new LinkedList<Socket>();
    // True if our queue is empty, false otherwise
    private boolean empty = true;

    /**
     * Pulls the request from the shared queue.
     *
     * @return a String containing the request information
     * @throws InterruptedException
     */
    public synchronized Socket pull() throws InterruptedException{
        // Wait until a request is available.
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                // We are being interrupted for shutdown
                throw new InterruptedException();
            }
        }
        // pull from the queue
        Socket request = requests.poll();
        // On a status change notify threads that we have a new request.
        if ((!empty) && (requests.size()==0)){
            notifyAll();
            empty = true;
        }
        return request;
    }

    /**
     * Pushes the request to the queue.
     *
     * @param request a String containing the request information
     */
    public synchronized void push(Socket request) {
        // Now it is empty -- notify threads that status has changed.
        if (empty){
            empty = false;
            notifyAll(); // notify all threads
        }
        // Store message.
        requests.add(request);
    }
}
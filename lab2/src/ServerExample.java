/**
 * Created by nikos on 1/31/14.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample {

    public static void main(String[] args) {
        listenSocket();
    }

    /**
     * Giving up to try/catch nightmare
     */
    public static void listenSocket(){
        ServerSocket server;
        try{
            server = new ServerSocket(4321);
            try{
                Socket client = server.accept();
                try{
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(System.out,true);
                    String line;
                    while((line = in.readLine()) != null){
                        System.out.println(line);
                        if (line.length()==0) {
                            break;
                        }
                    }
                    System.out.println("Now we can reply");
                } catch (IOException e) {
                    System.out.println("Read failed");
                    System.exit(-1);
                }
            } catch (IOException e) {
                System.out.println("Accept failed: 4321");
                System.exit(-1);
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port 4321");
            System.exit(-1);
        }
    }
}

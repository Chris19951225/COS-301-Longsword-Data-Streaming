import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ThreadedServer implements Runnable {
    Socket csocket;
    ThreadedServer(Socket csocket) {
        this.csocket = csocket;
    }
    public static void main(String args[]) throws Exception {
        ServerSocket ssock = new ServerSocket(9004);
        System.out.println("Listening on port: " + ssock.getLocalPort());

        while (true) {
            Socket sock = ssock.accept();
            System.out.println("Connected");
            new Thread(new ThreadedServer(sock)).start();
        }
    }
    public void run() {

        try
        {
            int numResponses = 0;
            boolean active = true;

            String input = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(this.csocket.getInputStream()));

            long startTime = System.nanoTime();

            while(numResponses < 6250)
            {
                input = br.readLine();
                if(input == null)
                {
                    continue;
                }
                else
                {
                    //System.out.println(input);
                    numResponses++;
                }
            }

            long endTime = System.nanoTime();

            System.out.println("Time to receive 6250 requests: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " milliseconds.");

            // Close the connection
            br.close();
            this.csocket.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
}
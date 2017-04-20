import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class TestServerRequester
{
    public static void main(String[] argc) throws IOException
    {
        final int portNumber = 9000;
        boolean active = true;
        ServerSocket serverSocket = null;
        Socket socket = null;
        System.out.println("Creating requesting server socket on port " + portNumber);

        try
        {
            serverSocket = new ServerSocket(portNumber);
        }
        catch(Exception e)
        {
            System.out.println("Error. Cannot open the server on this port.");
        }

        try
        {
            socket = serverSocket.accept();
        }
        catch(Exception e)
        {
            System.out.println("Unable to accept connection.");
        }

        PrintWriter pw = null;
        if(socket != null)
        {
            OutputStream os = socket.getOutputStream();
            pw = new PrintWriter(os, true);
        }

        long startTime = System.nanoTime();

        for(int i = 0; i < 1; i++)
        {
            pw.println("48:5A:3F:79:A3:D1");
        }

        long endTime = System.nanoTime();

        System.out.println("Time to send 25000 requests: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " milliseconds.");

        // Close the connection
        pw.close();
        socket.close();
    }
}

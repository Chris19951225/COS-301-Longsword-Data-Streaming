import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class TestServerRequester
{
    public static void main(String[] args) throws IOException
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


        long startTime = 0;
        long endTime = 0;

        if(Boolean.parseBoolean(args[0]))
        {
            startTime = System.nanoTime();

            for(int i = 0 ; i < 120 ; i++)
            {
                pw.println("EC:1F:72:B7:5D:39");
            }

            startTime = System.nanoTime();
        }
        else
        {
            startTime = System.nanoTime();

            for(int i = 0; i < 25000; i++)
            {
                pw.println("48:5A:3F:79:A3:D1");
            }

            endTime = System.nanoTime();

        }

        System.out.println("Time to send 25000 requests: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " milliseconds.");

        // Close the connection
        pw.close();
        socket.close();
    }
}


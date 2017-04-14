import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Lucian on 4/14/2017.
 */
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

        for(int i = 0; i < 100; i++)
        {
            pw.println(i);
        }


        // Close the connection
        pw.close();
        socket.close();
    }
}

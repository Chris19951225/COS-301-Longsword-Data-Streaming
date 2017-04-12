import java.io.*;
import java.net.*;

public class TestServer
{
    public static void main(String[] argc) throws IOException
    {
        final int portNumber = 9002;
        boolean active = true;
        ServerSocket serverSocket = null;
        Socket socket = null;
        System.out.println("Creating server socket on port " + portNumber);

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

        String input = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while(active)
        {
            input = br.readLine();
            if(input.equalsIgnoreCase("STOP"))
            {
                active = false;
            }
            else
            {
                System.out.println(input);

                try
                {
                    socket = serverSocket.accept();
                }
                catch(Exception e)
                {
                    System.out.println("Unable to accept connection.");
                }
            }
        }

        // Close the connection
        br.close();
        socket.close();
    }
}

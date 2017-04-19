package Test;

import com.sun.corba.se.spi.activation.Server;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Boxall 14056136 on 4/19/2017.
 */
public class AccessInterface {

    public static void main(String[] args) throws IOException {

        // Read in the required parameters from a text file
        // Ports and sockets could be defined in the textfile in future


        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("address.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String str;

        List<String> list = new ArrayList<String>();
        try {
            while((str = in.readLine()) != null){
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] stringArr = list.toArray(new String[0]);

        String address = stringArr[0];

        // Start up the Threaded Server

        ProcessBuilder ServerProcessBuilder = new ProcessBuilder("ThreadedServer.jar", "-jar", "ThreadedServer.jar");
        ServerProcessBuilder.directory(new File("/Test"));
        Process ServerProcess= ServerProcessBuilder.start();


        //Start up the data Server
        ProcessBuilder DataProcessBuilder = new ProcessBuilder("Data.jar", "-jar", "Data.jar");
        DataProcessBuilder.directory(new File("/Test"));
        Process DataProcess = DataProcessBuilder.start();


        // Start up the Server requester with parameters

        ProcessBuilder TestServerRequesterProcessBuilder = new ProcessBuilder("Data.jar", "-jar", "Data.jar", address);
        TestServerRequesterProcessBuilder .directory(new File("/Test"));
        Process TestServerRequesterProcess= TestServerRequesterProcessBuilder .start();


    }

}

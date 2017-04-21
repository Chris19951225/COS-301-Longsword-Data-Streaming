package com.example.peter_pc.navupsockettest;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SocketTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatemen

        return super.onOptionsItemSelected(item);
    }

    public void RunRequest(View v) throws IOException {

        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();


        final int portNumber = 9000;
        boolean active = true;
        ServerSocket serverSocket = null;
        Socket socket = null;
        System.out.println("Creating requesting server socket on port " + portNumber);

        try
        {
            serverSocket = new ServerSocket(1755);
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
            pw.println(address);
        }

        long endTime = System.nanoTime();

        System.out.println("Time to send 25000 requests: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " milliseconds.");

        // Close the connection
        pw.close();
        socket.close();
    }
}

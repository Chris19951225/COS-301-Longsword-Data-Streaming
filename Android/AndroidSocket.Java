package com.example.peter_pc.navupsockettest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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

        EditText txtMac = (EditText)findViewById(R.id.editText);
        EditText txtResponse = (EditText) findViewById(R.id.editText2);

        final String mac = txtMac.getText().toString();
        txtResponse.setText("Request Sent, waitng for server");
        //   Toast.makeText(this,"Request sending...",Toast.LENGTH_LONG);

        String response = "no response";

        Thread thread = new Thread() {
            @Override
            public void run() {

                //------------SOCKET CODE----------
                Socket socket = null;
                try {
                    socket = new Socket("10.0.0.18",1755);
                    DataOutputStream DOS = null;
                    DOS = new DataOutputStream(socket.getOutputStream());
                    DOS.writeUTF(mac);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        //---------------------------------
    }
}

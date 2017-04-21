package com.company;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Server is running....");
        String msg_received;

        ServerSocket socket;
        socket = new ServerSocket(1755);
        System.out.println("Waiting for client....");
        Socket clientSocket = socket.accept();       //This is blocking. It will wait.
        DataInputStream DIS = new DataInputStream(clientSocket.getInputStream());
        msg_received = DIS.readUTF();
        clientSocket.close();
        socket.close();
        System.out.println("message: " + msg_received);

    }
}

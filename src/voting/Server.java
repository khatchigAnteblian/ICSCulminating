/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// package voting;

import java.io.*;
import java.net.*;

/**
 *
 * @author Khatchig Anteblian & Vedant Shah
 */

public class Server {

    // ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dis;
    // static DataOutputStream dos;

    public static void main (String[] args) {
        String msgin = "";
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            while (!msgin.equals("exit")) {
                System.out.println("Waiting for client...");
                socket = serverSocket.accept();
                System.out.println("Client accepted: " + socket);

                dis = new DataInputStream(socket.getInputStream());
                // dos = new DataOutputStream(socket.getOutputStream());

                // while (!msgin.equals("exit")) {
                    // msgin = dis.readUTF();
                    // System.out.println(msgin);
                // }
                msgin = dis.readUTF();
                System.out.println(msgin);
                socket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}

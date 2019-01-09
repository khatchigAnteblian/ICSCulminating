/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voting;

import java.io.*;
import java.net.*;

/**
 *
 * @author Khatchig Anteblian & Vedant Shah
 */

public class Server {

    static Socket socket;
    static DataInputStream dis;
    static DataOutputStream dos;

    public static void main (String[] args) {
        String msgin = "";
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            while (!msgin.equals("exit")) {
                System.out.println("Waiting for client...");
                socket = serverSocket.accept();
                System.out.println("Client accepted: " + socket);

                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                msgin = dis.readUTF();
                String[] inputData = msgin.split(" ");

                String path = "../../Database/";
                File f = new File(path + inputData[0]);
                if (!f.exists()) {
                    try {
                        writeToDbase(inputData, f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    dos.writeUTF("AOK");
                    dos.flush();
                    System.out.println(msgin);
                } else {
                    dos.writeUTF("FAE");
                    dos.flush();
                }
                socket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToDbase(String[] s, File f) throws IOException {
        FileWriter writer = new FileWriter(f, true);
        for (int i=1; i<s.length; i++) {
            writer.write(s[i]);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
        writer.close();
    }

}

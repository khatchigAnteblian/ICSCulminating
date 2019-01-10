/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voting;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

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
                int inputValidation = validateUserInput(f);
                if (inputValidation == 0) {
                    try {
                        writeToDbase(inputData, f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    dos.writeUTF("0");
                    dos.flush();
                    System.out.println("INPUT AOK!");
                } else {
                    dos.writeUTF("" + inputValidation);
                    dos.flush();
                    System.out.println("INPUT NOT OK!");
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
    
    public static int validateUserInput(File f) {
        // Return val 0: User input is new and unique.
        // Return val 1: Username already taken.
        // Return val -1: Exact match with all input. Account already exists.
        // Return val 2: User info exists under a different username.
        if (f.exists()) {
            if (accountExists(f)) {
                return -1;
            }
            return 1;
        } else {
            if (accountExists(f)) {
                return 2;
            }
            return 0;
        }
    }
    
    public static boolean accountExists(File f) {
        String path = "../../Database/";
        File directory = new File(path);
        String file = readFile(f);
        String[] dbase_files = directory.list();
        for (String i : dbase_files) {
           if (readFile(new File(path + i)).equals(file)) {
               return true;
           } 
        }
        return false;
    }

    public static String readFile(File f) {
        String fileContent = "";
        try {
            fileContent = new String(Files.readAllBytes(Paths.get(f.getPath())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileContent);
        System.out.println("----------------");
        return fileContent;
    }

}

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
    static final String dbasePath = "../../Database/";

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

                File f = new File(dbasePath + inputData[0]);
                int inputValidation = validateUserInput(inputData);
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
    
    public static int validateUserInput(String[] input) {
        // Return val 0: User input is new and unique.
        // Return val 1: Username already taken.
        // Return val -1: Exact match with all input. Account already exists.
        // Return val 2: User info exists under a different username.
        File file = new File(dbasePath + input[0]);
        if (file.exists()) {
            if (accountExists(input)) {
                return -1;
            }
            return 1;
        } else {
            if (accountExists(input)) {
                return 2;
            }
            return 0;
        }
    }

    public static boolean accountExists(String[] input) {
        File directory = new File(dbasePath);
        String[] dbase_files = directory.list();
        for (String i : dbase_files) {
            if (compareFiles(dbasePath + i, input)) {
                return true;
            }
        }
        return false;
    }

    public static boolean compareFiles(String filePath, String[] input) {
        boolean match = false;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String fileContent = br.readLine();
            int lineNum = 1;
            while (fileContent != null) {
                if (!fileContent.equals(input[lineNum])) {
                    System.out.println(match);
                    return match;
                } 
                lineNum++;
                fileContent = br.readLine();
            }
            match = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return match;
    }

}

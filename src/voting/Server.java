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
        File currentUser = null;
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            while (true) {
                System.out.println("Waiting for client...");
                socket = serverSocket.accept();
                System.out.println("Client accepted: " + socket);

                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                msgin = dis.readUTF();
                String[] inputData = msgin.split(" ");

                if (inputData.length == 2) {
                    if (validateLogin(inputData)) {
                        currentUser = new File(dbasePath + inputData[0]);
                        try {
                            dos.writeUTF("0");
                            dos.flush();
                            System.out.println("LOGIN AOK!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            dos.writeUTF("1");
                            dos.flush();
                            System.out.println("LOGIN NOT OK!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (inputData.length == 1) {
                    try {
                        writeToDbase(inputData, currentUser, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    int inputValidation = validateUserInput(inputData);
                    currentUser = new File(dbasePath + inputData[0]);
                    if (inputValidation == 0) {
                        try {
                            writeToDbase(inputData, currentUser, 1);
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
                }
                socket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToDbase(String[] s, File f, int index) throws IOException {
        FileWriter writer = new FileWriter(f, true);
        for (int i=index; i<s.length; i++) {
            writer.write(s[i]);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
        writer.close();
    }

    public static boolean validateLogin(String[] input) {
        String filePath = dbasePath + input[0];
        File file = new File(filePath);
        boolean result = false;
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                // Discard first line of file.
                br.readLine();

                if (br.readLine().trim().equals(input[1])) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
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

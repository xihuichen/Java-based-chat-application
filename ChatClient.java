import java.net.*;
import java.io.*;
//import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Properties;

public class ChatClient {
    private static final String CONFIG_FILE="config.properties";
//    private static final int Chat_PORT = 5002;

    
    private static Properties loadConfiguration(String fileName) throws IOException {
        Properties config = new Properties();
        FileInputStream in = new FileInputStream(fileName);
        config.load(in);
        in.close();
        return config;
    }
    public static void main(String[] args) throws IOException {
        
    //    String serverAddress = args.length > 0 ? args[0]:"123.1.0.0";
    //    String hostname = args.length > 0 ? args[1] : "localhost";
    try {
        Properties config = loadConfiguration(CONFIG_FILE);
        String serverAddress = config.getProperty("serverAddress");
        int serverPort = Integer.parseInt(config.getProperty("serverPort"));
        Socket socket = new Socket(serverAddress, serverPort);
        System.out.println("Connected to chat server at " + serverAddress + ":" + serverPort);
        Scanner scanner = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
   
        //listenForServerMessages();
        // Thread to read messages from the server
        Thread readThread = new Thread(() -> {
            try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);//Reads the message from the server and print out
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                }
            });
            readThread.start();        

        // Main loop for sending messages to the server
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("\\q")) {
                break;
            }
            out.println(userInput); /*Sends the user's input to the server. 
                                      The println method of PrintWriter writes 
                                      the string to the output stream and adds 
                                      a newline character, signaling the end of 
                                      the message.*/
        }

        socket.close();
        scanner.close();
        System.out.println("Disconnected from the chat server.");
        } catch (UnknownHostException e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        

    

    } 
       




}
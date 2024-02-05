import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
//import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Properties;

public class PooledChatServer {

    private final static int PORT = 5002;
    private static ExecutorService threadPool= Executors.newFixedThreadPool(50);
    private static Map<Socket,PrintWriter> clientWriters= new ConcurrentHashMap<>();
    private static final String CONFIG_FILE= "config.properties";
    public static void main(String[] args) throws Exception {
        generateConfigFile();
        System.out.println("Chat server is running...");
        
    
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Listening for connection on port " + PORT);
            while (true) {
                try {
                    Socket connection = server.accept();
                    System.out.println("Client connected from " + connection.getInetAddress() + ":" + connection.getPort() );
                    Callable<Void> task = new ChatTask(connection);
                    threadPool.submit(task);
                } catch (IOException ex) {
                }
            }
        } catch (IOException ex) {
            System.err.println("Couldn't start server");
        }
    }

    private static class ChatTask implements Callable<Void> {

        private Socket connection;
        private BufferedReader in;
        private PrintWriter out;

        ChatTask(Socket connection) {
            this.connection = connection;
        }

        @Override
        public Void call() {
            try {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                out = new PrintWriter(connection.getOutputStream(), true);

                clientWriters.put(connection, out);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    for (PrintWriter writer : clientWriters.values()) {
                        writer.println(inputLine);
                    }}
               
            } catch (IOException ex) {
                System.err.println(ex);
            } finally {
                try {
                    clientWriters.remove(connection);
                    connection.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket:"+e.getMessage());
                }
            }
            return null;
        }
    }
    private static void generateConfigFile() {
        Properties config = new Properties();
        try {
            config.setProperty("serverAddress", InetAddress.getLocalHost().getHostAddress());
            config.setProperty("serverPort", String.valueOf(PORT));

            FileOutputStream out = new FileOutputStream(CONFIG_FILE);
            config.store(out, "Chat Server Configuration");
            out.close();

            System.out.println("Configuration file generated.");
        } catch (IOException e) {
            System.out.println("Error generating configuration file: " + e.getMessage());
        }
    }
}
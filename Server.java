import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 5000;
    private static final Logger logger = new Logger("Server");
    private static ServerSocket server;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args)
        throws Exception{
        try {
            server = new ServerSocket(PORT);
            logger.log("Server is up on port " + PORT);
            
            while (true) {
                logger.log("Waiting for one single client to connect...");
                Socket client = server.accept();
                logger.log("Client connected");
    
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
    
                if (in.readLine().equals("connection check")) {
                    out.println("ok");
                }

                while(true) {
                    try {
                        String message = in.readLine();
                        if (message.equals("stop")) {
                            out.println("ok bye!!!");
                            disconnectClient(client);
                            break;
                        } if (message.contains("Hey") || message.contains("Hello") || message.contains("hey") || message.contains("hello")) {
                            logger.log("client message received -> " + message);
                            out.println("Hello I am a Java Socket Server");
                        } else {
                            logger.log("client message received -> " + message);
                            out.println("I am a pretty lame system and cannot understand anything till now :(");
                        }
                    } catch (Exception ignored) {
                        disconnectClient(client);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            logger.log(e);
        } finally {
            server.close();
            logger.log("Bye");
        }
    }

    public static void disconnectClient(Socket client) {
        try {
            client.close();
            logger.log("Client disconnected");
        } catch (Exception ignored) {}
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 5000;
    private static final Logger logger = new Logger("Client");
    private static final BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;
    public static void main(String[] args)
        throws Exception{
        try {
            socket = new Socket(SERVER_IP, PORT);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("connection check");
            System.out.println("Waiting...");

            Thread waitThread = new Thread(new Runnable() {
                private Logger logger = new Logger("Client Manager");
                private boolean flag = false;

                @Override
                public void run() {
                    try {
                        long start = System.currentTimeMillis();
                        while(true) {
                            if (Thread.currentThread().isInterrupted())
                                throw new InterruptedException();
                            long end = System.currentTimeMillis();
                            if ((end - start) > 10000l && (end - start) < 11000l && !flag) {
                                flag = true;
                                logger.log("The server is a one-to-one server and it seems that a client is already connected. You can try again later.");
                            }
                        }
                    } catch (InterruptedException ex) {
                        if (flag) {
                            logger.log("It seems the connection has established...");
                        }
                    }
                }
            });
            waitThread.start();

            System.out.println(in.readLine());

            waitThread.interrupt();

            logger.log("Connected to server");
            logger.log("Type in a command to get response");
            logger.log("Type 'stop' to quit");

            while (true) {
                System.out.print(">");
                String cmd = keyboard.readLine();
                out.println(cmd);
                String reply = in.readLine();
                if (reply.equals("ok bye!!!")) {
                    System.out.println(reply);
                    break;
                }
                System.out.println(reply);
                System.out.println("");
            }
        } catch (IOException e) {
            logger.log(e);
        } finally {
            socket.close();
            logger.log("Disconnected");
        }
    }
}
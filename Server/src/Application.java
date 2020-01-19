import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Application {

    ServerSocket serverSocket;
    private static final int SERVER_PORT = 6969;
    private ArrayList<MessageThread> connections;

    public static void main(String[] args) {
        try {
            new Application().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException {
        // Create a socket to wait for clients.
        serverSocket = new ServerSocket(SERVER_PORT);
        connections = new ArrayList<>();

        while (true) {
            // Wait for an incoming client-connection request (blocking).
            Socket socket = serverSocket.accept();
            MessageThread mt = new MessageThread(socket);
            connections.add(mt);

            // Message processing thread
            new Thread(mt).start();

            // Ping thread
//            PingThread pt = new PingThread (mt);
//            new Thread(pt).start();

            // Your code here:
            // TODO: Start a message processing thread for each connecting client.

            // TODO: Start a ping thread for each connecting client.
        }
    }
}

import com.sun.tools.javac.Main;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        try {
            new Application().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final String SERVER_PORT = "6969";
    boolean pressedQ = false;

    public void run() throws IOException {

        Socket socket;

        socket = new Socket(SERVER_ADDRESS, Integer.parseInt(SERVER_PORT));
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        Thread receiveThread = new Thread(() -> {
            while (!pressedQ) {
                String line = null;
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (line.contains("PING")) {
                    writer.println("PONG");

                } else if (line.startsWith("HELO")) {
                    writer.println("HELO RemEd");
                }
                writer.flush();
            }
        });
        receiveThread.start();

        Thread sendThread = new Thread(() -> {

            Scanner scanner = new Scanner(System.in);
            while(!pressedQ){
                System.out.println("Send Q to disconnect from the server.");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")){
                    pressedQ = true;
                    writer.println("QUIT");
                    writer.flush();
                }
            }
        });
        sendThread.start();


    }
}

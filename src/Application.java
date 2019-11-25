import com.sun.tools.javac.Main;

import java.io.*;
import java.net.Socket;

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

    public void run() throws IOException {

        Socket socket;

        socket = new Socket(SERVER_ADDRESS, Integer.parseInt(SERVER_PORT));
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();


        PrintWriter writer = new PrintWriter(outputStream);

        writer.println("HELO RemEd");
        writer.flush();

        while (true) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String line = reader.readLine();

            if (line.contains("PING")) {
                writer.println("PONG");
                writer.flush();
            } else {
                //login/send messages etc.
            }
        }
    }
}

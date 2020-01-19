import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

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
    Scanner scanner = new Scanner(System.in);

    public void run() throws IOException {

        Socket socket;

        socket = new Socket(SERVER_ADDRESS, Integer.parseInt(SERVER_PORT));
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        //Basic errorhandling for username allowed characters


        AtomicReference<String> finalUsername = new AtomicReference<>(setUsername());
        Thread receiveThread = new Thread(() -> {
            while (!pressedQ) {

                String line = null;
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert line != null;

                if (line.contains("PING")) {
                    writer.println("PONG");

                } else if (line.startsWith("-ERR")) {
                    finalUsername.set(setUsername());
                } else if (line.startsWith("HELO")) {
                    writer.println("HELO " + finalUsername);
                } else if (line.startsWith("BCST")) {
                    String[] stringElements = line.split(" ", 3);

                    String sender = stringElements[1];
                    sender = sender.replace("[", "").replace("]", "");

                    String message = stringElements[2];
                    System.out.println(sender + ": " + message);
                }
                writer.flush();
            }
        });
        receiveThread.start();

        Thread sendThread = new Thread(() -> {


            System.out.println("Send Q to disconnect from the server.");
            while (!pressedQ) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    pressedQ = true;
                    writer.println("QUIT");

                } else {
                    writer.println("BCST " + input);
                }
                writer.flush();
            }
        });
        sendThread.start();


    }

    private String setUsername() {
        String username = "";
        boolean usernameCorrect = false;

        while (!usernameCorrect) {
            System.out.print("Enter your preferred username: ");
            username = scanner.nextLine();
            if (username.matches("[A-Za-z0-9_]+")) {
                usernameCorrect = true;

            } else {
                System.out.println("That's not quite right! Try again (username can only contain alphanumeric characters and underscores)");
            }
        }
        return username;
    }
}

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private ReceiveThread receiveThread;
    private SendThread sendThread;
    private boolean validUsername;
    private boolean waitForServer;

    public static void main(String[] args) {
        try {
            new Client().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 6969;
    private boolean pressedQ = false;
    private Scanner scanner = new Scanner(System.in);

    private void run() throws IOException {

        Socket socket;

        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

        receiveThread = new ReceiveThread(this, socket);
        receiveThread.start();

        sendThread = new SendThread(this, socket);
        sendThread.start();

        waitForServer = false;
        String finalUsername;
        validUsername = false;

        while (!validUsername) {
            if (!waitForServer) {
                String username = setUsername();
                sendThread.sendMessage("HELO " + username);
                waitForServer = true;
            }
        }

        System.out.println("Username OK, go send your messages!");
        while (!pressedQ){
            // STUUR BERICHTEN
        }

//
//        Thread receiveThread = new Thread(() -> {
//            while (!pressedQ) {
//
//                String line = null;
//                try {
//                    line = reader.readLine();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                assert line != null;
//
//                if (line.contains("PING")) {
//                    writer.println("PONG");
//
//                }
//
//                if (!validUsername){
//
//                } else{
//                    if (line.startsWith("BCST")) {
//                        String[] stringElements = line.split(" ", 3);
//
//                        String sender = stringElements[1];
//                        sender = sender.replace("[", "").replace("]", "");
//
//                        String message = stringElements[2];
//                        System.out.println(sender + ": " + message);
//                    }
//                }
//
//
//                writer.flush();
//            }
//        });
//
//        receiveThread.start();
//
//        Thread sendThread = new Thread(() -> {
//
//            System.out.println("Send Q to disconnect from the server.");
//            while (!pressedQ) {
//                String input = scanner.nextLine();
//                if (input.equalsIgnoreCase("q")) {
//                    pressedQ = true;
//                    writer.println("QUIT");
//                } else {
//                    writer.println("BCST " + input);
//                }
//                writer.flush();
//            }
//        });
//        sendThread.start();
//    }
    }

    private String setUsername() {
        System.out.print("Enter your preferred username: ");
//        boolean usernameCorrect = false;
//
//        while (!usernameCorrect) {
//            System.out.print("Enter your preferred username: ");
//            username = scanner.nextLine();
//            if (username.matches("[A-Za-z0-9_]+")) {
//                usernameCorrect = true;
//
//            } else {
//                System.out.println("That's not quite right! Try again (username can only contain alphanumeric characters and underscores)");
//            }
//        }
        return scanner.nextLine();
    }

    public void pingReceived() {
        sendThread.sendMessage("PONG");
    }

    public void setValidUsername(boolean validUsername) {
        this.validUsername = validUsername;
    }

    public boolean hasValidUsername() {
        return validUsername;
    }

    public void setWaitForServer(boolean waitForServer) {
        this.waitForServer = waitForServer;
    }
}

import java.io.*;
import java.net.Socket;

public class MessageThread implements Runnable {

    Socket socket;
    InputStream inputStream;
    OutputStream outputStream;
    PrintWriter writer;
    BufferedReader reader;

    public MessageThread(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        writer = new PrintWriter(outputStream);
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder().append("HELO ");
        String welcomeMessage = "Welkom bij RemEd Chatservices!";
        sb.append(welcomeMessage);
        System.out.println(sb.toString());
        writer.println(sb.toString());
        writer.flush();

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (line != null) {
            System.out.println(line);
        }
    }


}

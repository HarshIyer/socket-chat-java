import java.io.*;
import java.net.*;

class ChatClient {
    private static String SERVER_IP = "localhost";
    private static int SERVER_PORT = 4001;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("The connection has been set up with server. Start typing messages.");

            Thread receivingThread = new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receivingThread.start();

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}

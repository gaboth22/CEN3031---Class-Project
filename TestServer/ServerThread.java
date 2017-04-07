import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket clientSocket;
    private volatile String messageFromClient = null;

    private PrintWriter outputToClient;

    public ServerThread(Socket clientSocket) throws Exception {
        super("ServerThread");
        this.clientSocket = clientSocket;
        outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String receivedMessage;

            while((receivedMessage = inputFromClient.readLine()) != null) {
                messageFromClient = receivedMessage;
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToClient(String message) {
        outputToClient.println(message);
    }

    public String getMessageFromClient() throws Exception {
        while(messageFromClient == null) {
        }
        String receivedMessage = messageFromClient;
        messageFromClient = null;
        return receivedMessage;
    }
}

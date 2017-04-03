package LocalTestServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket clientSocket;
    private String messageFromClient = null;
    private String emptyLine = "";

    public ServerThread(Socket clientSocket) {
        super("ServerThread");
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String receivedMessage;

            while((receivedMessage = inputFromClient.readLine()) != null) {
                if(!receivedMessage.equals(emptyLine))
                    messageFromClient = receivedMessage;
                if(receivedMessage.equals(emptyLine))
                    break;
            }

            inputFromClient.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToClient(String message) {
        try {
            PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            outputToClient.println(message);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessageFromClient() {
        while(messageFromClient == null) {
        }

        return messageFromClient;
    }
}

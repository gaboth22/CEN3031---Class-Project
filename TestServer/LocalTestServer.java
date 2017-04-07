import java.io.IOException;
import java.net.ServerSocket;

public class LocalTestServer {
    private ServerSocket serverSocket;
    private ServerThread thread;

    public LocalTestServer(int portNumber) throws Exception {
        this.serverSocket = new ServerSocket(portNumber);
    }

    public void handleClient() throws Exception {
        System.out.println("Waiting for client to connect...");
        thread = new ServerThread(serverSocket.accept());
        System.out.println("Client connected");
        System.out.println("Enter strings to send to client from here on:\n");
        thread.start();
    }

    public String getMessageFromClient() throws Exception {
        return thread.getMessageFromClient();
    }

    public void sendMessageToClient(String message) {
        thread.sendMessageToClient(message);
    }

    public void close() {
        try {
            serverSocket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
package LocalTestServer;

import java.io.IOException;
import java.net.ServerSocket;

public class LocalTestServer {
    private ServerSocket serverSocket;
    private ServerThread thread;

    public LocalTestServer(int portNumber) throws IOException {
        this.serverSocket = new ServerSocket(portNumber);
    }

    public void handleClient() throws IOException {
        thread = new ServerThread(serverSocket.accept());
        thread.start();
    }

    public String getMessageFromClient() {
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
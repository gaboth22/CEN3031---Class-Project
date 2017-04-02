package ServerClient;

import java.io.*;
import java.net.Socket;

public class ServerClient {
    private Socket clientSocket;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader inputReader;
    private OutputStream outputStream;
    private OutputStreamWriter outputStreamWriter;
    private BufferedWriter bufferedWriter;

    public ServerClient(String hostName, int portNumber) throws IOException {
        clientSocket = new Socket(hostName, portNumber);

        outputStream = clientSocket.getOutputStream();
        outputStreamWriter = new OutputStreamWriter(outputStream);
        bufferedWriter = new BufferedWriter(outputStreamWriter);

        inputStream = clientSocket.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        inputReader = new BufferedReader(inputStreamReader);
    }

    public void sendDataToServer(String data) throws IOException {
        bufferedWriter.write(format(data));
        bufferedWriter.flush();
    }

    private String format(String toFormat) {
        return toFormat + "\n";
    }

    public String receiveData() throws IOException {
        return inputReader.readLine();
    }
}
package TestMain;

import GameMaster.ServerComm.GameClient;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;

public class TestClient implements GameClient {

    private InputStream inputStream;
    private BufferedReader inputReader;
    private OutputStream outputStream;
    private OutputStreamWriter outputStreamWriter;
    private BufferedWriter bufferedWriter;

    private boolean consoleInput = false;

    public TestClient(String fileToReadTestsFrom, String fileToWriteTestResultsTo) throws Exception {
        outputStream = new FileOutputStream(fileToWriteTestResultsTo);
        outputStreamWriter = new OutputStreamWriter(outputStream);
        bufferedWriter = new BufferedWriter(outputStreamWriter);

        inputStream = new FileInputStream(fileToReadTestsFrom);
        InputStreamReader isr = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        inputReader = new BufferedReader(isr);
    }

    public void setConsoleInterrupt(boolean flag) {
        this.consoleInput = flag;
    }

    @Override
    public void sendDataToServer(String data) throws IOException {
        bufferedWriter.write(format(data));
        bufferedWriter.flush();
    }

    private String format(String toFormat) {
        if(!toFormat.contains("\r\n"))
            return toFormat + "\r\n";
        else
            return toFormat;
    }

    @Override
    public String receiveData() throws IOException {
        return getData();
    }

    private String getData() throws IOException {
        String returnString = "";
        sleep(200);
        while(returnString.trim().equals("")) {
            returnString = inputReader.readLine();

            if(returnString == null){
                promptEnterKey();
                System.exit(0);
            }
        }

        if(consoleInput == true) {
            promptEnterKey();
        }

        return returnString;
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception ex) {

        }
    }

    public void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    @Override
    public void close() {

    }
}

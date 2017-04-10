package GameMaster.ServerComm;

import java.io.IOException;

public interface GameClient {

    void sendDataToServer(String data) throws IOException;

    String receiveData() throws IOException;

    void close();
}

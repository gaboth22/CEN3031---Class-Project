package ServerMessageParser.ServerData;

import Sender.SenderData.SenderData;

public class ServerIncomingData implements SenderData<String> {
    private String data;

    public ServerIncomingData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}

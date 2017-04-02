package GameMaster.ServerComm;

import Sender.SenderData.SenderData;

public class IncomingServerSenderData implements SenderData<String> {
    private String data;

    public IncomingServerSenderData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}

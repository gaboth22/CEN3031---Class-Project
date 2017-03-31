package SenderTest;

import Sender.SenderData.SenderData;
import Receiver.Receiver;

public class ReceiverTestDouble implements Receiver {

    private String receivedData;

    public String getReceivedData() {
        return receivedData;
    }

    public void callback(SenderData data) {
        receivedData = (String) data.getData();
    }
}

package Sender;

import java.util.LinkedList;
import java.util.List;
import Receiver.Receiver;
import Sender.SenderData.SenderData;

public class Sender {
    private List<Receiver> receiverList;

    public Sender() {
        receiverList = new LinkedList<Receiver>();
    }

    public void subscribe(Receiver receiver) {
        receiverList.add(receiver);
    }

    public void publish(SenderData data) {
        for(Receiver sub : receiverList) {
            sub.callback(data);
        }
    }
}

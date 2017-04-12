package Sender;

import Receiver.Receiver;
import Sender.SenderData.SenderData;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Sender {
    private List<Receiver> receiverList;

    public Sender() {
        receiverList = new CopyOnWriteArrayList<Receiver>();
    }

    public void subscribe(Receiver receiver) {
        receiverList.add(receiver);
    }

    public synchronized void publish(SenderData data) {
        for(Receiver sub : receiverList) {
            sub.callback(data);
        }
    }
}

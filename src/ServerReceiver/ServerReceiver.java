package ServerReceiver;

import Sender.Sender;
import ServerReceiver.ServerIncomingData.ServerIncomingData;
import Receiver.Receiver;

public class ServerReceiver {
    private Sender serverDataSender;

    public ServerReceiver() {
        serverDataSender = new Sender();
    }

    public void subscribe(Receiver r) {
        serverDataSender.subscribe(r);
    }

    public void sendData(ServerIncomingData data) {
        serverDataSender.publish(data);
    }
}

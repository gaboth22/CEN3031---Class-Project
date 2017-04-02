package ServerMessageParser;

import Sender.Sender;
import ServerMessageParser.ServerData.ServerIncomingData;
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

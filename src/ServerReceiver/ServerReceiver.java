package ServerReceiver;

import Publisher.Publisher;
import ServerReceiver.ServerIncomingData.ServerIncomingData;
import Subscriber.Subscriber;

public class ServerReceiver {
    private Publisher serverDataPublisher;

    public ServerReceiver() {
        serverDataPublisher = new Publisher();
    }

    public void subscribe(Subscriber s) {
        serverDataPublisher.subscribe(s);
    }

    public void sendData(ServerIncomingData data) {
        serverDataPublisher.publish(data);
    }
}

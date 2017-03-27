package PublisherTest;

import Publisher.PublisherData.PublisherData;
import Subscriber.Subscriber;

public class SubscriberTestDouble implements Subscriber {

    private String receivedData;

    public String getReceivedData() {
        return receivedData;
    }

    public void callback(PublisherData data) {
        receivedData = (String) data.getData();
    }
}

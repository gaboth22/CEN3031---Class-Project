package Publisher;

import java.util.LinkedList;
import java.util.List;
import Subscriber.Subscriber;
import Publisher.PublisherData.PublisherData;

public class Publisher {
    private List<Subscriber> subscriberList;

    public Publisher() {
        subscriberList = new LinkedList<Subscriber>();
    }

    public void subscribe(Subscriber subscriber) {
        subscriberList.add(subscriber);
    }

    public void publish(PublisherData data) {
        for(Subscriber sub : subscriberList) {
            sub.callback(data);
        }
    }
}

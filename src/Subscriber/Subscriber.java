package Subscriber;

import Publisher.PublisherData.PublisherData;

public interface Subscriber {
    void callback(PublisherData data);
}

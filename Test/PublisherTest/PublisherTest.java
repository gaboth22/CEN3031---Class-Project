package PublisherTest;

import Publisher.Publisher;
import Publisher.PublisherData.PublisherData;
import Subscriber.Subscriber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PublisherTest {
    private Publisher pub;
    private Subscriber sub;
    private PublisherData data;

    @Before
    public void initializeInstances() {
        pub = new Publisher();
        sub = new SubscriberTestDouble();
    }

    @Test
    public void theDataPublishedShouldBeReceivedByTheSubscriber() {
        givenTheDataToBePublishedIs("Hello, World!");
        givenTheSubscriberHasSubscribedToThePublisher();
        whenThePublisherPublishesTheData();
        theDataReceivedBySubscriberShouldBe("Hello, World!");
    }

    public void givenTheDataToBePublishedIs(String data) {
        this.data = new PublisherDataTestDouble(data);
    }
    private void givenTheSubscriberHasSubscribedToThePublisher() {
        pub.subscribe(sub);
    }

    private void whenThePublisherPublishesTheData() {
        pub.publish(data);
    }

    public void theDataReceivedBySubscriberShouldBe(String data) {
        String dataReceivedBySubscriber = ((SubscriberTestDouble) sub).getReceivedData();
        Assert.assertEquals(dataReceivedBySubscriber, data);
    }

    @Test
    public void theDataReceivedByTheSubscriberShouldNotMatchTheTestData() {
        givenTheDataToBePublishedIs("Fine string");
        givenTheSubscriberHasSubscribedToThePublisher();
        whenThePublisherPublishesTheData();
        theDataReceivedShouldNotBe("Hello, World!");
        butTheDataShouldBeInstead("Fine string");
    }

    public void theDataReceivedShouldNotBe(String data) {
        String dataReceivedBySubscriber = ((SubscriberTestDouble) sub).getReceivedData();
        Assert.assertFalse(dataReceivedBySubscriber.equals(data));
    }

    public void butTheDataShouldBeInstead(String data) {
        String dataReceivedBySubscriber = ((SubscriberTestDouble) sub).getReceivedData();
        Assert.assertEquals(dataReceivedBySubscriber, data);
    }
}

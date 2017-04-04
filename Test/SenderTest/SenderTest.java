package SenderTest;

import Sender.Sender;
import Sender.SenderData.SenderData;
import Receiver.Receiver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SenderTest {
    private Sender pub;
    private Receiver sub;
    private SenderData data;

    @Before
    public void initializeInstances() {
        pub = new Sender();
        sub = new ReceiverTestDouble();
    }

    @Test
    public void theDataPublishedShouldBeReceivedByTheSubscriber() {
        givenTheDataToBePublishedIs("Hello, World!");
        givenTheSubscriberHasSubscribedToThePublisher();
        whenThePublisherPublishesTheData();
        theDataReceivedBySubscriberShouldBe("Hello, World!");
    }

    public void givenTheDataToBePublishedIs(String data) {
        this.data = new SenderDataTestDouble(data);
    }

    private void givenTheSubscriberHasSubscribedToThePublisher() {
        pub.subscribe(sub);
    }

    private void whenThePublisherPublishesTheData() {
        pub.publish(data);
    }

    public void theDataReceivedBySubscriberShouldBe(String data) {
        String dataReceivedBySubscriber = ((ReceiverTestDouble) sub).getReceivedData();
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
        String dataReceivedBySubscriber = ((ReceiverTestDouble) sub).getReceivedData();
        Assert.assertFalse(dataReceivedBySubscriber.equals(data));
    }

    public void butTheDataShouldBeInstead(String data) {
        String dataReceivedBySubscriber = ((ReceiverTestDouble) sub).getReceivedData();
        Assert.assertEquals(dataReceivedBySubscriber, data);
    }
}

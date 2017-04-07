package GameMasterTest.ServerCommTest;

import LocalTestServer.LocalTestServer;
import GameMaster.ServerComm.ServerClient;
import org.junit.*;
import java.io.IOException;
import Networking.*;
@Ignore("Stalls for some people (not Gabe)")
public class ServerClientTest {
    private LocalTestServer testServer;
    private ServerClient serverClient;

    @Before
    public void initializeInstances() {
        try {
            testServer = new LocalTestServer(PortNumber.FOR_TESTS);
            serverClient = new ServerClient("127.0.0.1", PortNumber.FOR_TESTS);
            testServer.handleClient();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        serverClient.close();
        testServer.close();
    }

    @Test
    public void theServerShouldReceiveTheRightMessageFromTheClient() {
        String message = "Hello, Server!";
        givenTheClientSendsAMessageToTheServer(message);
        thenTheServerShouldHaveReceived(message);
    }

    private void givenTheClientSendsAMessageToTheServer(String message) {
        try {
            serverClient.sendDataToServer(message);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void thenTheServerShouldHaveReceived(String message) {
        String messageFromClient = testServer.getMessageFromClient();
        Assert.assertEquals(message, messageFromClient);
    }

    @Test
    public void theServerShouldNotReceiveTheWrongMessageFromTheClient() {
        String message = "Hello, Server!";
        String otherMessage = "Hello, Hello";
        givenTheClientSendsAMessageToTheServer(message);
        thenTheServerShouldShouldHaveReceived(otherMessage);
    }

    private void thenTheServerShouldShouldHaveReceived(String message) {
        String messageFromClient = testServer.getMessageFromClient();
        Assert.assertNotEquals(messageFromClient, message);
    }

    @Test
    public void theClientShouldReceivedTheRightMessageFromTheServer() throws IOException {
        String message = "Hello, Client!";
        givenTheServerSendsAMessageToTheClient(message);
        thenTheClientShouldReceive(message);
    }

    private void givenTheServerSendsAMessageToTheClient(String message) {
        testServer.sendMessageToClient(message);
    }

    private void thenTheClientShouldReceive(String expectedMessage) throws IOException {
        Assert.assertEquals(expectedMessage, serverClient.receiveData());
    }

    @Test
    public void theClientShouldNotReceiveAWrongMessageFromTheServer() throws IOException {
        String message = "Hello, Client!";
        String otherMessage = "Hello, Hello";
        givenTheServerSendsAMessageToTheClient(message);
        thenTheClientShouldNotReceive(otherMessage);
    }

    private void thenTheClientShouldNotReceive(String invalidMessage) throws IOException {
        Assert.assertNotEquals(invalidMessage, serverClient.receiveData());
    }
}

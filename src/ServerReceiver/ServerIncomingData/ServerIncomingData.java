package ServerReceiver.ServerIncomingData;

import Publisher.PublisherData.PublisherData;

public class ServerIncomingData implements PublisherData<String> {
    private String data;

    public ServerIncomingData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}

package PublisherTest;

import Publisher.PublisherData.PublisherData;

public class PublisherDataTestDouble implements PublisherData<String> {
    private String data;

    public PublisherDataTestDouble(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}

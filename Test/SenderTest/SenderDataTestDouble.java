package SenderTest;

import Sender.SenderData.SenderData;

public class SenderDataTestDouble implements SenderData<String> {
    private String data;

    public SenderDataTestDouble(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}

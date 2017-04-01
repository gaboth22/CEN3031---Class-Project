package Receiver;

import Sender.SenderData.SenderData;

public interface Receiver {
    void callback(SenderData data);
}

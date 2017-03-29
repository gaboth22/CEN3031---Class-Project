package GameBoard.PlayAck;

import Sender.SenderData.SenderData;

public class PlayAckSenderData implements SenderData<PlayAck> {
    private PlayAck ack;

    public PlayAckSenderData(PlayAck ack) {
        this.ack = ack;
    }


    public PlayAck getData() {
        return ack;
    }
}

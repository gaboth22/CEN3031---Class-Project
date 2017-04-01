package Steve;

import Sender.SenderData.SenderData;

public class StevePlayTypeSenderData implements SenderData <StevePlayType> {
    private StevePlayType playType;

    public StevePlayTypeSenderData(StevePlayType playType) {
        this.playType = playType;
    }

    public StevePlayType getData() {
        return playType;
    }
}

package GameBoard.PhasePublisherData;

import Play.TilePlacementPhase.TilePlacementPhase;
import Sender.SenderData.SenderData;

public class TilePlacementPhaseSenderData implements SenderData<TilePlacementPhase> {
    TilePlacementPhase play;

    public TilePlacementPhaseSenderData(TilePlacementPhase play) {
        this.play = play;
    }

    public TilePlacementPhase getData() {
        return play;
    }
}

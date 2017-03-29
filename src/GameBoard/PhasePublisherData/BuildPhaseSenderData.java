package GameBoard.PhasePublisherData;

import Play.BuildPhase.BuildPhase;
import Sender.SenderData.SenderData;

public class BuildPhaseSenderData implements SenderData<BuildPhase> {
    BuildPhase play;

    public BuildPhaseSenderData(BuildPhase play) {
        this.play = play;
    }

    public BuildPhase getData() {
        return play;
    }
}

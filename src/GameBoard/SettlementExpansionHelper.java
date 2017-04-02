package GameBoard;

import Location.*;
import Play.BuildPhase.BuildPhaseException;

public interface SettlementExpansionHelper {
    void expandSettlement() throws BuildPhaseException;
    Location[] getListOfLocationsExpandedTo();
}

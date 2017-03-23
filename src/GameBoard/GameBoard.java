package GameBoard;

import Play.Rule.Rule;
import Play.TilePlacementPhase.TilePlacementPhase;

public interface GameBoard {
    void insertRule(Rule rule);

    void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception;
}

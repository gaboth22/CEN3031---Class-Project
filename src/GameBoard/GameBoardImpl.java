package GameBoard;

import GamePieceMap.GamePieceMap;
import TileMap.TileMap;
import Play.Rule.RuleType;
import Play.Rule.Rule;
import java.util.Map;

public class GameBoardImpl implements GameBoard {
    private GamePieceMap gamePieceMap;
    private TileMap tileMap;
    private Map<RuleType, Rule> rules;

    @Override
    public void insertRule(Rule rule) {
        rules.put(rule.getType(), rule);
    }
}

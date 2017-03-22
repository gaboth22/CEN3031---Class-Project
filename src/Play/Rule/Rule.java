package Play.Rule;

import GamePieceMap.GamePieceMap;
import TileMap.TileMap;
import Play.Rule.RuleType;

public abstract class Rule {
    private RuleType ruleType;

    public Rule(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public RuleType getType() {
        return ruleType;
    }

    public abstract void applyRule(TileMap tileMap, GamePieceMap gamePieceMap);
}

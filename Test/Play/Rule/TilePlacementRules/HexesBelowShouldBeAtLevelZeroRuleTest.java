package Play.Rule.TilePlacementRules;

import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import TileMap.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HexesBelowShouldBeAtLevelZeroRuleTest {
    private TileMap tileMap;
    private TileBuilder builder;

    @Before
    public void initializeInstances() {
        tileMap = new HexagonMap();
        builder = new TileBuilder();
    }

    @After
    public void cleanUp() {
        tileMap = null;
    }

    @Test(expected = InvalidTilePlacementRuleException.class)
    public void theRuleShouldFailSinceThereHexesBelowAreOccupied() throws Exception {
        givenThereIsATileAtTheOrigin();
        whenIAttemptToPlaceAnotherTileAtTheOrigin();
    }

    private void givenThereIsATileAtTheOrigin() throws Exception {
        Tile atOrigin = builder.getTileAtOrigin();
        tileMap.insertTile(atOrigin);
    }

    private void whenIAttemptToPlaceAnotherTileAtTheOrigin() throws Exception {
        Tile atOriginAgain = builder.getTileAtOrigin();
        HexesBelowShouldBeAtLevelZeroRule.applyRule(tileMap, atOriginAgain);
    }

    @Test
    public void theRuleShouldPassSinceTheHexesBelowAreEmpty() throws InvalidTilePlacementRuleException {
        Tile atOrigin = builder.getTileAtOrigin();
        HexesBelowShouldBeAtLevelZeroRule.applyRule(tileMap, atOrigin);
    }
}

package Play.Rule.PiecePlacementRules;

import Location.Location;
import Movement.*;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import TileMap.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FoundationMustBeLevelOneRuleTest {

    private TileMap tileMap;
    private TileBuilder builder;
    private Movement movement;
    private Location origin;

    @Before
    public void initializeInstances() {
        tileMap = new HexagonMap();
        builder = new TileBuilder();
        movement = new AxialMovement();
        origin = new Location(0, 0);
    }

    @After
    public void cleanUp() {
        tileMap = null;
        builder = null;
        movement = null;
        origin = null;
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void ruleShouldFailSinceHexHeightIsTwo() throws Exception {
        givenTheTileMapIsPopulatedUpToHeightTwo();
        whenICheckTheRule();
    }

    private void givenTheTileMapIsPopulatedUpToHeightTwo() throws Exception {
        Tile atOrigin = builder.getTileAtOrigin();
        Tile alsoAtOrigin = builder.getTileAtOrigin();
        tileMap.insertTile(atOrigin);
        tileMap.insertTile(alsoAtOrigin);
    }

    private void whenICheckTheRule() throws InvalidPiecePlacementRuleException {
        FoundationMustBeLevelOneRule.applyRule(tileMap, movement.up(origin));
    }

    @Test
    public void ruleShouldPassSinceHexHeightIsOne() throws Exception {
        givenTheTileMapIsPopulatedUpToHeightOne();
        whenICheckTheRule();
    }

    private void givenTheTileMapIsPopulatedUpToHeightOne() throws Exception {
        Tile againAtOrigin = builder.getTileAtOrigin();
        tileMap.insertTile(againAtOrigin);
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void ruleShouldPassSinceHexHeightIsGreaterThanThree() throws Exception {
        givenTheTileMapIsPopulatedUpToHeightFour();
        whenICheckTheRule();
    }

    private void givenTheTileMapIsPopulatedUpToHeightFour() throws Exception {
        givenTheTileMapIsPopulatedUpToHeightOne();
        Tile yetAgainAtOrigin = builder.getTileAtOrigin();
        tileMap.insertTile(yetAgainAtOrigin);
    }

}
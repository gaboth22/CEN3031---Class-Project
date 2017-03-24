package Play.Rule.TilePlacementRules;

import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import TestExceptions.MethodCalledException;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import TileMap.HexagonMap;
import Location.*;
import Movement.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HexesBelowTileShouldBeSameLevelRuleTest {
    private TileBuilder builder;
    private HexagonMap hexMap;
    private Location[] locations;
    private AxialMovement movement;
    private Tile toPlacePass;
    private Tile toPlaceFail;
    private Tile origin;
    private Tile adjacent;

    @Before
    public void setUp() throws Exception {
        movement = new AxialMovement();
        hexMap = new HexagonMap();
        builder = new TileBuilder();
        origin = builder.getTileAtOrigin();
        adjacent = builder.getAdjacentTile(origin);
        Location adjacentVolcano = adjacent.getArrayOfTerrainLocations()[0];
        hexMap.insertTile(origin);
        hexMap.insertTile(adjacent);
        toPlacePass = builder.getTileWithLocations(adjacentVolcano, movement.down(adjacentVolcano),
                movement.downLeft(adjacentVolcano));
        toPlaceFail = builder.getTileWithLocations(adjacentVolcano, movement.down(adjacentVolcano),
                movement.downRight(adjacentVolcano));
    }

    @Test(expected = InvalidTilePlacementRuleException.class)
    public void applyRuleThrowsTest() throws Exception {
        HexesBelowTileShouldBeSameLevelRule.applyRule(hexMap, toPlaceFail);
    }

    @Test(expected = MethodCalledException.class)
    public void applyRulesPassesTest() throws Exception {
        HexesBelowTileShouldBeSameLevelRule.applyRule(hexMap, toPlacePass);
        theRulePassedBecauseWeMadeItHere();
    }

    private void theRulePassedBecauseWeMadeItHere() throws MethodCalledException {
        throw new MethodCalledException();
    }

    @After
    public void tearDown() throws Exception {
        movement = null;
        hexMap = null;
        builder = null;
    }

}
package Play.Rule.TilePlacementRules;

import Location.Location;
import Movement.*;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import TestExceptions.MethodCalledException;
import TileBuilder.TileBuilder;
import TileMap.*;
import Tile.Tile.Tile;
import org.junit.Before;
import org.junit.Test;

public class TileMustTouchOneEdgeRuleTest {
    private TileMap tileMap;
    private TileBuilder tileBuilder;
    private Movement locationGen;

    @Before
    public void initializeInstances() {
        tileMap = new HexagonMap();
        tileBuilder = new TileBuilder();
        locationGen = new AxialMovement();
    }

    @Test(expected = MethodCalledException.class)
    public void ruleShouldPass() throws Exception {
        givenIHaveATileAtTheOriginOfTheMap();
        Tile adjacent = givenIHaveAnAdjacentTileToTheOneAtTheOrigin();
        whenIApplyTheRule(adjacent);
        thenIShouldMakeItAllTheWayDownHereBecauseTheRulePassed();
    }

    private void givenIHaveATileAtTheOriginOfTheMap() throws Exception {
        tileMap.insertTile(tileBuilder.getTileAtOrigin());
    }

    private Tile givenIHaveAnAdjacentTileToTheOneAtTheOrigin() {
        return tileBuilder.getAdjacentTile(tileBuilder.getTileAtOrigin());
    }

    private void whenIApplyTheRule(Tile tileToPlace) throws Exception {
        TileMustTouchOneEdgeRule.applyRule(tileMap, tileToPlace);
    }

    private void thenIShouldMakeItAllTheWayDownHereBecauseTheRulePassed() throws MethodCalledException {
        throw new MethodCalledException();
    }


    @Test(expected = InvalidTilePlacementRuleException.class)
    public void ruleShouldNotPass() throws Exception {
        givenIHaveATileAtTheOriginOfTheMap();
        Location volcanoLocation = new Location(1, 2);
        Tile notAdjacent = tileBuilder.getTileWithLocations(volcanoLocation,
                                                            locationGen.up(volcanoLocation),
                                                            locationGen.upRight(volcanoLocation));
        whenIApplyTheRule(notAdjacent);
    }

}
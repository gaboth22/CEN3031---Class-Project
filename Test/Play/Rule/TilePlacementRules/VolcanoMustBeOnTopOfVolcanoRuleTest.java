package Play.Rule.TilePlacementRules;

import Location.Location;
import Movement.*;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import TestExceptions.MethodCalledException;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import TileMap.*;
import org.junit.Before;
import org.junit.Test;

public class VolcanoMustBeOnTopOfVolcanoRuleTest {
    private TileBuilder builder;
    private TileMap tileMap;
    private Movement movement;

    @Before
    public void initializeInstances() {
        builder = new TileBuilder();
        tileMap = new HexagonMap();
        movement = new AxialMovement();
    }

    @Test(expected = InvalidTilePlacementRuleException.class)
    public void theRuleShouldNotPassSinceThereIsNoVolcanoBelow() throws Exception {
        givenThereIsAVolcanoAtTheOrigin();
        Tile badTile = givenIHaveATileWithItsVolcanoNotAtTheOrigin();
        whenTheBadTileIsPutDown(badTile);
    }

    private void givenThereIsAVolcanoAtTheOrigin() throws Exception {
        Tile origin = builder.getTileAtOrigin();
        tileMap.insertTile(origin);
    }

    private Tile givenIHaveATileWithItsVolcanoNotAtTheOrigin() {
        Location origin = new Location(0, 0);
        Location badTileVolcano = movement.up(origin);
        Tile badTile = builder.getTileWithLocations(badTileVolcano,
                                                    movement.down(badTileVolcano),
                                                    movement.downRight(badTileVolcano));
        return badTile;
    }

    private void whenTheBadTileIsPutDown(Tile badTile) throws Exception {
        VolcanoMustBeOnTopOfVolcanoRule.applyRule(tileMap, badTile);
    }

    @Test(expected = MethodCalledException.class)
    public void theRuleShouldPassSinceThereIsAVolcanoBelow() throws Exception {
        givenThereIsAVolcanoAtTheOrigin();
        Tile goodTile = givenIHaveATileWithItsVolcanoAtTheOrigin();
        whenTheGoodTileIsPutDown(goodTile);
        thenIShouldMakeItHereBecauseTheRulePassed();
    }

    private Tile givenIHaveATileWithItsVolcanoAtTheOrigin() {
        return builder.getTileAtOrigin();
    }

    private void whenTheGoodTileIsPutDown(Tile goodTile) throws Exception {
        VolcanoMustBeOnTopOfVolcanoRule.applyRule(tileMap, goodTile);
    }

    private void thenIShouldMakeItHereBecauseTheRulePassed() throws MethodCalledException {
        throw new MethodCalledException();
    }
}

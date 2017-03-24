package Play.Rule.TilePlacementRules;

import Location.Location;
import Movement.Movement;
import Movement.AxialMovement;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Play.Rule.TotoroCannotBePlacedOnVolcanoRule;
import TileMap.HexagonMap;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Terrain.Terrain.Terrain;
import Tile.TileFactory.*;
import TileMap.TileMap;
import TileTest.TileInformationGeneratorTestDouble.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TotoroCannotBePlacedOnVolcanoRuleTest {

    private TileMap tileMap;
    private Movement movement;
    private TileInformationGeneratorWithOrientationTestDouble infoGen;
    private TileFactory tileFactory;
    private Location origin;

    private Location locationToPlace;

    @Before
    public void setUp() throws Exception {
        tileMap = new HexagonMap();
        movement = new AxialMovement();
        infoGen = new TileInformationGeneratorWithOrientationTestDouble(movement);
        tileFactory = new TileFactory();

        origin = new Location(0,0);

        infoGen.createTile(origin, OrientationTestDouble.UP, Terrain.GRASSLANDS, Terrain.JUNGLE);
        tileMap.insertTile(tileFactory.makeTile(infoGen));

        Location secondLocation = movement.down(origin);
        infoGen.createTile(secondLocation, OrientationTestDouble.DOWN, Terrain.ROCKY, Terrain.LAKE);
        tileMap.insertTile(tileFactory.makeTile(infoGen));

        Location thirdLocation = movement.downLeft(origin);
        infoGen.createTile(thirdLocation, OrientationTestDouble.DOWNLEFT, Terrain.GRASSLANDS, Terrain.GRASSLANDS);
        tileMap.insertTile(tileFactory.makeTile(infoGen));
    }

    @After
    public void tearDown() {
        tileMap = null;
        movement = null;
        infoGen = null;
        tileFactory = null;
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void aTotoroSanctuaryShouldNotBePlacedOnAVolcano() throws Exception {
        givenIHaveALocationToPlaceATotoro(origin);
        whenICheckIfICanPlaceTheTotoro();
    }

    @Test
    public void aTotoroSanctuaryShouldBeAbleToBePlacedOnGrasslands() throws Exception {
        givenIHaveALocationToPlaceATotoro(movement.up(origin));
        whenICheckIfICanPlaceTheTotoro();
    }

    private void givenIHaveALocationToPlaceATotoro(Location location) {
        this.locationToPlace = location;
    }

    private void whenICheckIfICanPlaceTheTotoro() throws Exception {
        TotoroCannotBePlacedOnVolcanoRule.applyRule(tileMap, locationToPlace);
    }
}

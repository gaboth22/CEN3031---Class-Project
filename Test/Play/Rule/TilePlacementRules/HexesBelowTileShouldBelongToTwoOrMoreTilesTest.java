package Play.Rule.TilePlacementRules;

import Location.Location;
import Terrain.Terrain.Terrain;
import Tile.TileFactory.ImpossibleTileException;
import Tile.TileFactory.TileFactory;
import TileTest.TileInformationGeneratorTestDouble.OrientationTestDouble;
import TileTest.TileInformationGeneratorTestDouble.TileInformationGeneratorWithOrientationTestDouble;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import TileMap.TileMap;
import TileMap.HexagonMap;
import Movement.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HexesBelowTileShouldBelongToTwoOrMoreTilesTest {

    private TileMap tileMap;
    private Movement movement;
    private TileInformationGeneratorWithOrientationTestDouble infoGen;
    private TileFactory tileFactory;
    private Location origin;

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

    @Test
    public void placingATileOnAllEmptySpacesShouldNotThrowAnException() throws Exception {
        infoGen.createTile(new Location(55,55), OrientationTestDouble.DOWNLEFT, Terrain.GRASSLANDS, Terrain.JUNGLE);
        whenICheckIfTheTileIsValid();
    }

    @Test
    public void placingATileOnTopOfThreeDifferentTilesShouldBeValid() throws Exception {
        givenIHaveAValidTileThatWIllCoverThreeTiles();
        whenICheckIfTheTileIsValid();
    }

    private void givenIHaveAValidTileThatWIllCoverThreeTiles() {
        infoGen.createTile(movement.down(origin), OrientationTestDouble.UP, Terrain.GRASSLANDS, Terrain.LAKE);
    }

    @Test
    public void placingATileOnTopOfTwoOtherTilesShouldBeValid() throws Exception {
        givenIHaveAValidTileThatWillCoverTwoTiles();
        whenICheckIfTheTileIsValid();
    }

    private void givenIHaveAValidTileThatWillCoverTwoTiles() {
        infoGen.createTile(movement.down(origin), OrientationTestDouble.UPLEFT, Terrain.JUNGLE, Terrain.LAKE);
    }

    private void whenICheckIfTheTileIsValid() throws ImpossibleTileException, InvalidTilePlacementRuleException {
        HexesBelowTileShouldBelongToTwoOrMoreTiles.applyRule(tileMap, tileFactory.makeTile(infoGen));
    }

    @Test(expected = InvalidTilePlacementRuleException.class)
    public void placingATileOnTopOfAnotherShouldThrowAnError() throws Exception {
        givenIHaveAnInvalidTileToPlace();
        whenICheckIfTheTileIsValid();
    }

    private void givenIHaveAnInvalidTileToPlace() {
        infoGen.createTile(origin, OrientationTestDouble.UP, Terrain.JUNGLE, Terrain.LAKE);
    }
}
package Play.Rule.TilePlacementRules;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Movement.Movement;
import Movement.AxialMovement;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Play.Rule.PiecePlacementRules.GamePieceCannotBePlacedOnVolcanoRule;
import Player.PlayerID;
import TileMap.HexagonMap;
import Terrain.Terrain.Terrain;
import Tile.TileFactory.*;
import TileMap.TileMap;
import TileTest.TileInformationGeneratorTestDouble.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GamePieceCannotBePlacedOnVolcanoRuleTest {

    private TileMap tileMap;
    private Movement movement;
    private TileInformationGeneratorWithOrientationTestDouble infoGen;
    private TileFactory tileFactory;
    private Location origin;

    private Location locationToPlace;
    private GamePiece gamePieceToPlace;

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
        whenICheckIfICanPlaceTheGamePiece();
    }

    @Test
    public void aTotoroSanctuaryShouldBeAbleToBePlacedOnGrasslands() throws Exception {
        givenIHaveALocationToPlaceATotoro(movement.up(origin));
        whenICheckIfICanPlaceTheGamePiece();
    }

    private void givenIHaveALocationToPlaceATotoro(Location location) {
        this.locationToPlace = location;
        this.gamePieceToPlace = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TOTORO);
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void aTigerSanctuaryShouldNotBePlacedOnAVolcano() throws Exception {
        givenIHaveALocationToPlaceATigerSanctuary(origin);
        whenICheckIfICanPlaceTheGamePiece();
    }

    @Test
    public void aTigerSanctuaryCanBePlacedOnJungle() throws Exception {
        givenIHaveALocationToPlaceATigerSanctuary(movement.upLeft(origin));
        whenICheckIfICanPlaceTheGamePiece();
    }

    private void givenIHaveALocationToPlaceATigerSanctuary(Location location) {
        this.locationToPlace = location;
        this.gamePieceToPlace = new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.TIGER);
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void aVillagerShouldNotBePlacedOntoAVolcano() throws Exception {
        givenIHaveALocationToSettleAVillager(movement.down(origin));
        whenICheckIfICanPlaceTheGamePiece();
    }

    @Test
    public void aVillagerCanBePlacedOnRockyTerrain() throws Exception {
        Location newLocation = movement.down(movement.down(origin));
        givenIHaveALocationToPlaceATotoro(newLocation);
    }

    private void givenIHaveALocationToSettleAVillager(Location location) {
        this.locationToPlace = location;
        this.gamePieceToPlace = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
    }

    private void whenICheckIfICanPlaceTheGamePiece() throws Exception {
        GamePieceCannotBePlacedOnVolcanoRule.applyRule(tileMap, gamePieceToPlace, locationToPlace);
    }
}

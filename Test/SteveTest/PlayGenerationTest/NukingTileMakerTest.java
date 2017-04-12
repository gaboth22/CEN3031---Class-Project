package SteveTest.PlayGenerationTest;

import GameBoard.GameBoardState;
import GamePieceMap.GamePiece;
import GamePieceMap.GamePieceMap;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Player.Player;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Steve.PlayGeneration.SmartTilePlacer.NukingTileMaker;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import TileMap.Hexagon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NukingTileMakerTest {

    private NukingTileMaker tileMaker;
    private Map<Location, Hexagon> hexMap;
    private Settlement nukeableSettlement;
    private Settlement activePlayerSettlement;
    private Location ofVolcano;
    private BiHexTileStructure terrains;
    private GameBoardState dummyState;
    private GamePieceMap pieceMap;
    private PlayerID activePlayer;
    private ArrayList<Settlement> p1Settlements;
    private ArrayList<Settlement> p2Settlements;
    private Player playerOne;
    private Player playerTwo;

    @Before
    public void initializeInstances() throws Exception {
        activePlayer = PlayerID.PLAYER_TWO;

        pieceMap = new GamePieceMap();

        tileMaker = new NukingTileMaker();
        hexMap = new HashMap<Location, Hexagon>();
        nukeableSettlement = new Settlement();
        terrains = new BiHexTileStructure(new Terrain[]{Terrain.LAKE, Terrain.ROCKY});

        Hexagon[] hexesOnBoard = new Hexagon[] {
                new Hexagon(1,
                        new Location(0, 0),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(1,
                        new Location(-1, 1),
                        1,
                        Terrain.LAKE),
                new Hexagon(1,
                        new Location(-1, 0),
                        1,
                        Terrain.JUNGLE),


                new Hexagon(2,
                        new Location(-1, 2),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(2,
                        new Location(0, 2),
                        1,
                        Terrain.GRASSLANDS),
                new Hexagon(2,
                        new Location(0, 1),
                        1,
                        Terrain.LAKE)
        };

        for(Hexagon hex : hexesOnBoard) {
            hexMap.put(hex.getTerrainLocation(), hex);
        }

        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        nukeableSettlement.markPieceInSettlement(new Location(0, 1), villager);
        nukeableSettlement.markPieceInSettlement(new Location(-1, 1), villager);

        pieceMap.insertAPieceAt(new Location(-1, 1), villager);
        pieceMap.insertAPieceAt(new Location(0, 1), villager);

        playerOne = new Player(PlayerID.PLAYER_ONE);
        playerTwo = new Player(PlayerID.PLAYER_TWO);

        p1Settlements = new ArrayList<>();
        p1Settlements.add(nukeableSettlement);

        playerOne.setListOfSettlements(p1Settlements);
        playerTwo.setListOfSettlements(new ArrayList<>());

        dummyState = new GameBoardState(
                playerOne,
                playerTwo,
                0,
                hexMap,
                null,
                null,
                null,
                null);

        p2Settlements = new ArrayList<>();
    }

    @Test
    public void nukingTileMakeShouldProduceAValidNukingTile() {
        givenThereVolcanoIsAt(new Location(0, 0));
        Tile nukingTile = tileMaker.makeTile(dummyState, hexMap, pieceMap, nukeableSettlement, ofVolcano, terrains, activePlayer);

        Location[] locationsInTile = nukingTile.getArrayOfTerrainLocations();
        Assert.assertTrue(locationsInTile[0].equals(ofVolcano));
        Assert.assertTrue(
                nukeableSettlement.locationIsInSettlement(locationsInTile[1]) ||
                          nukeableSettlement.locationIsInSettlement(locationsInTile[2])
        );
    }

    private void givenThereVolcanoIsAt(Location loc) {
        ofVolcano = loc;
    }

    @Test
    public void nukingTileShouldProduceAValidNukingTileForADifferentPivotVolcano() {
        givenThereVolcanoIsAt(new Location(-1, 2));
        Tile nukingTile = tileMaker.makeTile(dummyState, hexMap, pieceMap, nukeableSettlement, ofVolcano, terrains, activePlayer);

        Location[] locationsInTile = nukingTile.getArrayOfTerrainLocations();
        Assert.assertTrue(locationsInTile[0].equals(ofVolcano));
        Assert.assertTrue(
                nukeableSettlement.locationIsInSettlement(locationsInTile[1]) ||
                nukeableSettlement.locationIsInSettlement(locationsInTile[2])
        );
    }

    @Test
    public void shouldNotProduceNukingTileSinceThereIsTotoro() throws Exception {
        givenThereVolcanoIsAt(new Location(0, 0));
        givenTheSettlementHasATotoro();
        Tile nukingTile = tileMaker.makeTile(dummyState, hexMap, pieceMap, nukeableSettlement, ofVolcano, terrains, activePlayer);
        Assert.assertTrue(nukingTile == null);
    }

    private void givenTheSettlementHasATotoro() throws Exception {
        GamePiece totoro = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TOTORO);
        Location loc = new Location(0, 1);
        pieceMap.removeAPieceAt(loc); //remove villager that was here
        pieceMap.insertAPieceAt(loc, totoro);
        nukeableSettlement = new Settlement();
        nukeableSettlement.markPieceInSettlement(loc, totoro);
        nukeableSettlement.markPieceInSettlement(new Location(-1, 1), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        p1Settlements.clear();;
        p1Settlements.add(nukeableSettlement);
        playerOne.setListOfSettlements(p1Settlements);
    }

    @Test
    public void shouldNotProduceNukingTileSinceThereIsTiger() throws Exception {
        givenThereVolcanoIsAt(new Location(0, 0));
        givenTheSettlementHasATiger();
        Tile nukingTile = tileMaker.makeTile(dummyState, hexMap, pieceMap, nukeableSettlement, ofVolcano, terrains, activePlayer);
        Assert.assertTrue(nukingTile == null);
    }

    private void givenTheSettlementHasATiger() throws Exception {
        GamePiece tiger = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER);
        Location loc = new Location(0, 1);
        pieceMap.removeAPieceAt(loc); //remove villager that was here
        pieceMap.insertAPieceAt(loc, tiger);
        nukeableSettlement = new Settlement();
        nukeableSettlement.markPieceInSettlement(loc, tiger);
        nukeableSettlement.markPieceInSettlement(new Location(-1, 1), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        p1Settlements.clear();;
        p1Settlements.add(nukeableSettlement);
        playerOne.setListOfSettlements(p1Settlements);
    }

    @Test
    public void shouldNotProduceNukeableTileSinceItWouldNukeSizeOneSettlement() throws Exception {
        givenThereVolcanoIsAt(new Location(0, 0));
        givenTheNukeableSettlementIsOfSizeOne();
        Tile nukingTile = tileMaker.makeTile(dummyState, hexMap, pieceMap, nukeableSettlement, ofVolcano, terrains, activePlayer);
        Assert.assertTrue(nukingTile == null);
    }


    private void givenTheNukeableSettlementIsOfSizeOne() throws Exception {
        Location loc = new Location(0, 1);
        pieceMap.removeAPieceAt(loc); //remove villager that was here
        nukeableSettlement = new Settlement();
        nukeableSettlement.markPieceInSettlement(new Location(-1, 1), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        Assert.assertTrue(nukeableSettlement.getNumberOfHexesInSettlement() == 1);
        p1Settlements.clear();;
        p1Settlements.add(nukeableSettlement);
        playerOne.setListOfSettlements(p1Settlements);
    }

    @Test
    public void shouldNotProduceTileAsItWouldNukeSizeOneSettlementForEitherPlayer() throws Exception {
        givenThereVolcanoIsAt(new Location(0, 0));
        givenTheNukeableHexesContainASizeOneSettlementOfEachPlayer();
        Tile nukingTile = tileMaker.makeTile(dummyState, hexMap, pieceMap, nukeableSettlement, ofVolcano, terrains, activePlayer);
        Assert.assertTrue(nukingTile == null);
    }

    private void givenTheNukeableHexesContainASizeOneSettlementOfEachPlayer() throws Exception {
        Location loc = new Location(0, 1);
        pieceMap.removeAPieceAt(loc); //remove villager that was here
        nukeableSettlement = new Settlement();
        nukeableSettlement.markPieceInSettlement(new Location(-1, 1), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        nukeableSettlement.markPieceInSettlement(new Location(-1, 0), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        p1Settlements.clear();
        p1Settlements.add(nukeableSettlement);
        playerOne.setListOfSettlements(p1Settlements);


        activePlayerSettlement = new Settlement();
        GamePiece activePlayerPiece = new GamePiece(activePlayer, TypeOfPiece.VILLAGER);
        activePlayerSettlement.markPieceInSettlement(loc, activePlayerPiece);
        p2Settlements.add(activePlayerSettlement);
        playerTwo.setListOfSettlements(p2Settlements);
        pieceMap.insertAPieceAt(loc, activePlayerPiece);


        Assert.assertTrue(nukeableSettlement.getNumberOfHexesInSettlement() == 2);
        Assert.assertTrue(activePlayerSettlement.getNumberOfHexesInSettlement() == 1);
    }
}

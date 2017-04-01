package Settlements.SettlementExpansion;

import GamePieceMap.GamePieceMap;
import Movement.*;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;
import Settlements.Expansion.*;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.TileFactory.TileFactory;
import TileMap.*;
import Location.*;
import TileTest.TileInformationGeneratorTestDouble.TileInformationGeneratorWithOrientationTestDouble;
import GamePieceMap.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettlementExpansionTest {

    //Elements of a board
    private TileMap tileMap;
    private GamePieceMap pieceMap;

    Movement coordinateSystem = new AxialMovement();

    private List<Tile> tilesToAdd;
    private TileInformationGeneratorWithOrientationTestDouble infoGen;
    private TileFactory tileFactory;

    private Map<Location, Hexagon> hexMap;

    @Before
    public void setUp() throws Exception {
        tileMap = new HexagonMap();
        pieceMap = new GamePieceMap();

        infoGen = new TileInformationGeneratorWithOrientationTestDouble(coordinateSystem);
        tileFactory = new TileFactory();
    }

    private Map<Location, Hexagon> getTopLevelHexagons(Hexagon[] toGenerate) throws  Exception {
        Map<Location, Hexagon> generatedMap = new HashMap<Location, Hexagon>();
        for(int i = 0; i < toGenerate.length; i++) {
            Hexagon currHex = toGenerate[i];
            generatedMap.put(currHex.getTerrainLocation(), currHex);
        }
        return generatedMap;
    }

    private Hexagon[] largeHexagonBoard() {
        //Tile by Tile in each Column Starting from Column -6
        Hexagon[] generatedArray = new Hexagon[]{

                //Tile 1
                new Hexagon(1, new Location(-6,4), 1, Terrain.VOLCANO),
                new Hexagon(1, new Location(-6,3), 1, Terrain.GRASSLANDS),
                new Hexagon(1, new Location(-5,3), 1, Terrain.GRASSLANDS),

                //Tile 2
                new Hexagon(2, new Location(-6,2), 1, Terrain.VOLCANO),
                new Hexagon(2, new Location(-5,2), 1, Terrain.ROCKY),
                new Hexagon(2, new Location(-5,1), 1, Terrain.JUNGLE),

                //Tile 3
                new Hexagon(3, new Location(-5,0), 1, Terrain.JUNGLE),
                new Hexagon(3, new Location(-4,0), 1, Terrain.ROCKY),
                new Hexagon(3, new Location(-4,-1), 1, Terrain.ROCKY),

                //Tile 4
                new Hexagon(4, new Location(-4,3), 1, Terrain.GRASSLANDS),
                new Hexagon(4, new Location(-3,3), 1, Terrain.VOLCANO),
                new Hexagon(4, new Location(-3,2), 1, Terrain.GRASSLANDS),

                //Tile 5 : Other two tiles are covered
                new Hexagon(5, new Location(-3,4), 1, Terrain.LAKE),

                //Tile 6
                new Hexagon(6, new Location(-3,0), 1, Terrain.VOLCANO),
                new Hexagon(6, new Location(-3,-1), 1, Terrain.JUNGLE),

                //Tile 7
                new Hexagon(7, new Location(-2,4), 2, Terrain.VOLCANO),
                new Hexagon(7, new Location(-2,3), 2, Terrain.LAKE),
                new Hexagon(7, new Location(-1,3), 2, Terrain.ROCKY),

                //Tile 8
                new Hexagon(8, new Location(-2,2), 1, Terrain.GRASSLANDS),

                //Tile 9
                new Hexagon(9, new Location(-2,1), 2, Terrain.GRASSLANDS),
                new Hexagon(9, new Location(-2,0), 2, Terrain.GRASSLANDS),

                //Tile 10
                new Hexagon(10, new Location(-2,-1), 2, Terrain.ROCKY),
                new Hexagon(10, new Location(-1,-2), 2, Terrain.VOLCANO),

                //Tile 11
                new Hexagon(11, new Location(-1,1), 3, Terrain.ROCKY),
                new Hexagon(11, new Location(0,0), 3, Terrain.GRASSLANDS),

                //Tile 12
                new Hexagon(12, new Location(-1,0), 4, Terrain.VOLCANO),
                new Hexagon(12, new Location(-1,-1), 4, Terrain.GRASSLANDS),
                new Hexagon(12, new Location(0,-1), 4, Terrain.LAKE),

                //Tile 13
                new Hexagon(13, new Location(0,3), 1, Terrain.GRASSLANDS),
                new Hexagon(13, new Location(0,2), 1, Terrain.VOLCANO),

                //Tile 14
                new Hexagon(14, new Location(0,1), 2, Terrain.LAKE),

                //Tile 15
                new Hexagon(15, new Location(0,-2), 3, Terrain.ROCKY),

                //Tile 16
                new Hexagon(16, new Location(0,-3), 1, Terrain.LAKE),

                //Tile 17
                new Hexagon(17, new Location(1,0), 1, Terrain.ROCKY),

                //Tile 18
                new Hexagon(18, new Location(1,-1), 1, Terrain.JUNGLE),

                //Tile 19
                new Hexagon(19, new Location(1,-2), 2, Terrain.ROCKY)
        };
        return generatedArray;
    }

    @Test
    public void gettingLocationsToExpandToShouldReturnAValidList() throws Exception {
        hexMap = getTopLevelHexagons(largeHexagonBoard());
    }

    @Test
    public void bigGameBoardExpansionShouldGiveValidNumber() throws Exception {
        hexMap = getTopLevelHexagons(largeHexagonBoard());
        addPlayerOneLocationsOnLargeHexagonBoard();

        int numberOfVillagersRequired = getNumberOfVillagersRequiredToExpand(new Location(-2,3), Terrain.GRASSLANDS);
        Assert.assertEquals(14, numberOfVillagersRequired);
    }

    private int getNumberOfVillagersRequiredToExpand(Location locationToExpandFrom, Terrain terrainToExpandInto) {
        Settlement settlementToExpand = SettlementCreator.getSettlementAt(pieceMap, locationToExpandFrom);
        List<Location> locationsToExpandTo = SettlementExpansion.findLocationsToExpandInto(hexMap,
                settlementToExpand,
                pieceMap,
                terrainToExpandInto);

        return SettlementExpansion.numVillagersRequiredToExpansion(hexMap, locationsToExpandTo);
    }

    private void addPlayerOneLocationsOnLargeHexagonBoard() throws Exception {
        pieceMap.insertAPieceAt(new Location(-2,3), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        pieceMap.insertAPieceAt(new Location(-1,3), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER));
    }

    @Test
    public void bigGameBoardExpansionShouldBeBlockedByEnemySettlement() throws Exception {
        hexMap = getTopLevelHexagons(largeHexagonBoard());
        addPlayerOneLocationsOnLargeHexagonBoard();
        addPlayerTwoLocationsOnLargeHexagonBoard();

        Assert.assertEquals(1, getNumberOfVillagersRequiredToExpand(new Location(-2,3), Terrain.GRASSLANDS));
    }

    private void addPlayerTwoLocationsOnLargeHexagonBoard() throws Exception {
        pieceMap.insertAPieceAt(new Location(-2,2), new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.TOTORO));
    }

    @Test
    public void theLocationsForSimpleLineExpansionShouldBeValid() throws Exception {
        hexMap = getTopLevelHexagons(simpleLineOfExpandableTerrain());
        addPieceToOrigin();

        Location locationOfSettlement = new Location(0,0);
        Terrain terrainToExpandInto = Terrain.GRASSLANDS;
        Assert.assertEquals(2, getNumberOfVillagersRequiredToExpand(locationOfSettlement, terrainToExpandInto));
    }

    @Test
    public void theLocationsInASimpleLineExpansionShouldBeUpdated() throws Exception {
        hexMap = getTopLevelHexagons(simpleLineOfExpandableTerrain());
        addPieceToOrigin();

        Location locationOfSettlement = new Location(0,0);
        Terrain terrainToExpandInto = Terrain.GRASSLANDS;

        Settlement settlement = SettlementCreator.getSettlementAt(pieceMap, locationOfSettlement);
        List<Location> locations = SettlementExpansion.findLocationsToExpandInto(hexMap, settlement, pieceMap, terrainToExpandInto);
        expandVillage(settlement, locations);
        checkLocationsWereAdded(locations);
    }

    @Test
    public void theLocationsInLargeGameBoardShouldBeUpdated() throws Exception {
        hexMap = getTopLevelHexagons(largeHexagonBoard());
        addPlayerOneLocationsOnLargeHexagonBoard();

        Location locationOfSettlement = new Location(-2,3);
        Terrain terrainToExpandInto = Terrain.GRASSLANDS;

        Settlement settlement = SettlementCreator.getSettlementAt(pieceMap, locationOfSettlement);
        List<Location> locations = SettlementExpansion.findLocationsToExpandInto(hexMap, settlement, pieceMap, terrainToExpandInto);
        expandVillage(settlement, locations);
        checkLocationsWereAdded(locations);
    }

    private void expandVillage(Settlement settlement, List<Location> locations) throws Exception {
        SettlementExpansion.expandSettlement(pieceMap, settlement, locations);
    }

    private void checkLocationsWereAdded(List<Location> toCheck) throws Exception {
        for(Location loc : toCheck) {
            Assert.assertEquals(TypeOfPiece.VILLAGER, pieceMap.getPieceAtLocation(loc).getPieceType());
        }
    }

    private Hexagon[] simpleLineOfExpandableTerrain() {
        return new Hexagon[] {
                new Hexagon(1, new Location(0,0), 1, Terrain.GRASSLANDS),
                new Hexagon(1, new Location(0,1), 1, Terrain.GRASSLANDS),
                new Hexagon(1, new Location(0,2), 1, Terrain.GRASSLANDS)
        };
    }

    private void addPieceToOrigin() throws Exception {
        pieceMap.insertAPieceAt(new Location(0,0), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
    }

    @After
    public void tearDown() {
        tileMap = null;
        pieceMap = null;
        tilesToAdd = null;
        infoGen = null;
        tileFactory = null;
        hexMap = null;
    }

}
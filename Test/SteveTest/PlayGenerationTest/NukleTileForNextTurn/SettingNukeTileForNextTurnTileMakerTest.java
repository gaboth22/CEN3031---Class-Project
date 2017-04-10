package SteveTest.PlayGenerationTest.NukleTileForNextTurn;

import GameBoard.GameBoardState;
import GamePieceMap.GamePiece;
import GamePieceMap.GamePieceMap;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Player.Player;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Steve.PlayGeneration.NukeTileSetter.SettingNukeTileForNextTurnTileMaker;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import TileMap.Hexagon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingNukeTileForNextTurnTileMakerTest {

    private PlayerID activePlayer;
    private SettingNukeTileForNextTurnTileMaker tileMaker;
    private BiHexTileStructure terrains;
    private Map<Location, Hexagon> hexMap;
    private GameBoardState gameBoardState;
    Player playerOne;
    Player playerTwo;


    @Test
    public void setupTileShouldBePlaceToEnableANuke() throws Exception {
        givenIHaveAValidGameMap();

        Tile generated = tileMaker.getTile(gameBoardState, activePlayer, terrains);
        Location[] locationsIntile = generated.getArrayOfTerrainLocations();

        Assert.assertEquals(new Location(6,-5), locationsIntile[0]);
        Assert.assertEquals(new Location(6,-4), locationsIntile[1]);
        Assert.assertEquals(new Location(7,-5), locationsIntile[2]);
    }

    private void givenIHaveAValidGameMap() throws Exception {

        activePlayer = PlayerID.PLAYER_ONE;
        playerOne = new Player(activePlayer);
        playerTwo = new Player(PlayerID.PLAYER_TWO);
        List<Settlement> playerOneSettlements = new ArrayList<>();
        List<Settlement> playerTwoSettlements = new ArrayList<>();
        terrains = new BiHexTileStructure(new Terrain[]{Terrain.LAKE, Terrain.ROCKY});
        hexMap = new HashMap<Location, Hexagon>();
        tileMaker = new SettingNukeTileForNextTurnTileMaker();

        Hexagon[] hexesOnBoard = new Hexagon[]{
                new Hexagon(1,
                        new Location(3, -2),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(1,
                        new Location(3, -3),
                        1,
                        Terrain.LAKE),
                new Hexagon(1,
                        new Location(2, -2),
                        1,
                        Terrain.JUNGLE),


                new Hexagon(2,
                        new Location(5, -4),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(2,
                        new Location(5, -5),
                        1,
                        Terrain.LAKE),
                new Hexagon(2,
                        new Location(4, -4),
                        1,
                        Terrain.JUNGLE)
        };

        for(Hexagon hex : hexesOnBoard) {
            hexMap.put(hex.getTerrainLocation(), hex);
        }

        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        playerTwoSettlements.add(new Settlement());
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(4, -4), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(3, -3), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(5, -5), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(2, -2), villager);

        playerOne.setListOfSettlements(playerOneSettlements);
        playerTwo.setListOfSettlements(playerTwoSettlements);

        gameBoardState = new GameBoardState(
                playerOne,
                playerTwo,
                2,
                hexMap,
                new GamePieceMap(),
                new ArrayList<Location>(),
                new ArrayList<Location>()
        );
    }

    @Test
    public void setupTileShouldBePlacedAgainToEnableANuke() throws Exception {
        givenIHaveAnotherValidGameMap();
        Tile generated = tileMaker.getTile(gameBoardState, activePlayer, terrains);
        Location[] locationsIntile = generated.getArrayOfTerrainLocations();
        Assert.assertEquals(new Location(5,-4), locationsIntile[0]);
        Assert.assertEquals(new Location(5,-3), locationsIntile[1]);
        Assert.assertEquals(new Location(6,-4), locationsIntile[2]);
    }

    private void givenIHaveAnotherValidGameMap() throws Exception {
        activePlayer = PlayerID.PLAYER_ONE;
        playerOne = new Player(activePlayer);
        playerTwo = new Player(PlayerID.PLAYER_TWO);
        List<Settlement> playerOneSettlements = new ArrayList<>();
        List<Settlement> playerTwoSettlements = new ArrayList<>();
        terrains = new BiHexTileStructure(new Terrain[]{Terrain.LAKE, Terrain.ROCKY});
        hexMap = new HashMap<Location, Hexagon>();
        tileMaker = new SettingNukeTileForNextTurnTileMaker();

        Hexagon[] hexesOnBoard = new Hexagon[]{
                new Hexagon(1,
                        new Location(2, -3),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(1,
                        new Location(2, -2),
                        1,
                        Terrain.LAKE),
                new Hexagon(1,
                        new Location(3, -3),
                        1,
                        Terrain.JUNGLE),


                new Hexagon(2,
                        new Location(4, -5),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(2,
                        new Location(4, -4),
                        1,
                        Terrain.LAKE),
                new Hexagon(2,
                        new Location(5, -5),
                        1,
                        Terrain.JUNGLE)
        };

        for(Hexagon hex : hexesOnBoard) {
            hexMap.put(hex.getTerrainLocation(), hex);
        }

        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        playerTwoSettlements.add(new Settlement());
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(4, -4), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(3, -3), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(5, -5), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(2, -2), villager);

        playerOne.setListOfSettlements(playerOneSettlements);
        playerTwo.setListOfSettlements(playerTwoSettlements);

        gameBoardState = new GameBoardState(
                playerOne,
                playerTwo,
                2,
                hexMap,
                new GamePieceMap(),
                new ArrayList<Location>(),
                new ArrayList<Location>()
        );
    }

    @Test
    public void shouldStillProduceAValidSetupTile() throws Exception {
        givenIHaveYetAnotherValidGameMap();
        Tile generated = tileMaker.getTile(gameBoardState, activePlayer, terrains);
        Location[] locationsIntile = generated.getArrayOfTerrainLocations();
        Assert.assertEquals(new Location(6,-5), locationsIntile[0]);
        Assert.assertEquals(new Location(6,-4), locationsIntile[1]);
        Assert.assertEquals(new Location(7,-5), locationsIntile[2]);
    }

    private void givenIHaveYetAnotherValidGameMap() throws Exception {

        activePlayer = PlayerID.PLAYER_ONE;
        playerOne = new Player(activePlayer);
        playerTwo = new Player(PlayerID.PLAYER_TWO);
        List<Settlement> playerOneSettlements = new ArrayList<>();
        List<Settlement> playerTwoSettlements = new ArrayList<>();
        terrains = new BiHexTileStructure(new Terrain[]{Terrain.LAKE, Terrain.ROCKY});
        hexMap = new HashMap<Location, Hexagon>();
        tileMaker = new SettingNukeTileForNextTurnTileMaker();

        Hexagon[] hexesOnBoard = new Hexagon[]{
                new Hexagon(1,
                        new Location(3, -2),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(1,
                        new Location(3, -3),
                        1,
                        Terrain.LAKE),
                new Hexagon(1,
                        new Location(2, -2),
                        1,
                        Terrain.JUNGLE),


                new Hexagon(2,
                        new Location(5, -4),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(2,
                        new Location(5, -5),
                        1,
                        Terrain.LAKE),
                new Hexagon(2,
                        new Location(4, -4),
                        1,
                        Terrain.JUNGLE),

                new Hexagon(2,
                        new Location(5, -2),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(2,
                        new Location(5, -3),
                        1,
                        Terrain.LAKE),
                new Hexagon(2,
                        new Location(4, -2),
                        1,
                        Terrain.JUNGLE)
        };

        for(Hexagon hex : hexesOnBoard) {
            hexMap.put(hex.getTerrainLocation(), hex);
        }

        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        playerTwoSettlements.add(new Settlement());
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(4, -4), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(3, -3), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(5, -5), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(2, -2), villager);

        playerOne.setListOfSettlements(playerOneSettlements);
        playerTwo.setListOfSettlements(playerTwoSettlements);

        gameBoardState = new GameBoardState(
                playerOne,
                playerTwo,
                2,
                hexMap,
                new GamePieceMap(),
                new ArrayList<Location>(),
                new ArrayList<Location>()
        );
    }
}

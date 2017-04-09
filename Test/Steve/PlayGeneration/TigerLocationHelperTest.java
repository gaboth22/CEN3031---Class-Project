package Steve.PlayGeneration;


import GameBoard.GameBoardImpl;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import TileBuilder.TileBuilder;
import Location.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;


public class TigerLocationHelperTest {
    private GameBoardImpl gameBoard;
    private TileBuilder tileBuilder;
    private BuildPhase phase;
    private PlayerID player;

    @Before
    public void setUp(){
        gameBoard = new GameBoardImpl();
        tileBuilder = new TileBuilder();
        player = PlayerID.PLAYER_ONE;
    }

    @Test
    public void shouldFailToPlaceTiger() throws Exception {
        placeLevelOneTiles();
        foundSettlement(new Location(1,-1));

        phase = TigerLocationHelper.pickTigerLocation(gameBoard.getPlacedHexagons(), gameBoard.getPlayerOneSettlements(), gameBoard.getGamePieceMap());

        assertTrue(phase == null);
    }

    @Test
    public void shouldPlaceTiger() throws Exception {
        placeLevelOneTiles();
        placeHigherLevelTiles();
        foundSettlement(new Location (1,-1));

        phase = TigerLocationHelper.pickTigerLocation(gameBoard.getPlacedHexagons(), gameBoard.getPlayerOneSettlements(), gameBoard.getGamePieceMap());

        assertTrue(phase.getLocationToPlacePieceOn().equals(new Location(1,-2)));
    }

    private void placeLevelOneTiles() throws Exception{

        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        Location[] locations1 = new Location[]{
                new Location(0, -2),
                new Location(-1, -1),
                new Location(0, -1)
        };

        placeATileOnLevelOne(new TileImpl(Arrays.asList(terrains), Arrays.asList(locations1)));

        Location[] locations2 = new Location[]{
                new Location(1, -2),
                new Location(2,-3),
                new Location(1,-3)
        };

        placeATileOnLevelOne(new TileImpl(Arrays.asList(terrains), Arrays.asList(locations2)));

        Location[] locations3 = new Location[]{
                new Location(2,0),
                new Location(3,-1),
                new Location(2,-1)
        };

        placeATileOnLevelOne(new TileImpl(Arrays.asList(terrains), Arrays.asList(locations3)));

    }

    private  void placeHigherLevelTiles() throws Exception {
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };

        Location[] locations1 = new Location[]{
                new Location(0, 0),
                new Location(0, -1),
                new Location(-1, 0)
        };

        placeTileAboveLevelOne(new TileImpl(Arrays.asList(terrains), Arrays.asList(locations1)));

        Location[] locations2 = new Location[]{
                new Location(0, -2),
                new Location(1,-2),
                new Location(1,-3)
        };

        placeTileAboveLevelOne(new TileImpl(Arrays.asList(terrains), Arrays.asList(locations2)));

        Location[] locations3 = new Location[]{
                new Location(2,0),
                new Location(2,-1),
                new Location(1,0)
        };

        placeTileAboveLevelOne(new TileImpl(Arrays.asList(terrains), Arrays.asList(locations3)));

        Location[] locations4 = new Location[]{
                new Location(0,-2),
                new Location(0,-1),
                new Location(1, -2)

        };

        placeTileAboveLevelOne(new TileImpl(Arrays.asList(terrains), Arrays.asList(locations4)));

    }

    private void placeATileOnLevelOne(Tile tile) throws Exception {
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, tile);
        tilePlacementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);
    }

    private void placeTileAboveLevelOne(Tile tile) throws Exception {
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, tile);
        tilePlacementPhase.setTilePlacementType(TilePlacementType.NUKE);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);
    }

    private void foundSettlement(Location newSettlementLocation) throws Exception {
        GamePiece foundingVillager = new GamePiece(player, TypeOfPiece.VILLAGER);
        phase = new BuildPhase(foundingVillager, newSettlementLocation);
        phase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(phase);
    }
}
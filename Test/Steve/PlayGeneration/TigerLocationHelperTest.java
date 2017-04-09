package Steve.PlayGeneration;


import GameBoard.GameBoardImpl;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import Location.*;
import org.junit.Before;
import org.junit.Test;
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
        foundSettlement(new Location (2,-1));

        phase = TigerLocationHelper.pickTigerLocation(gameBoard.getPlacedHexagons(), gameBoard.getPlayerOneSettlements(), gameBoard.getGamePieceMap());

        assertTrue(phase.getLocationToPlacePieceOn() == new Location(1,-1));
    }

    private void placeLevelOneTiles() throws Exception{
        Location l1 = new Location(0, -2);
        Location l2 = new Location(0, -1);
        Location l3 = new Location(-1, -1);
        placeATileOnLevelOne(l1, l2, l3);

        l1 = new Location(1, -2);
        l2 = new Location(1,-1);
        l3 = new Location(2,-2);
        placeATileOnLevelOne(l1, l2, l3);

        l1 = new Location(2,0);
        l2 = new Location(2,-1);
        l3 = new Location(3,-1);
        placeATileOnLevelOne(l1, l2, l3);
    }

    private  void placeHigherLevelTiles() throws Exception {
        Location l1 = new Location(0, 0);
        Location l2 = new Location(-1, 0);
        Location l3 = new Location(0, -1);
        placeTileAboveLevelOne(l1, l2, l3);

        l1 = new Location(0, -2);
        l2 = new Location(1,-1);
        l3 = new Location(1,-2);
        placeTileAboveLevelOne(l1, l2, l3);

        l1 = new Location(2,0);
        l2 = new Location(1,-1);
        l3 = new Location(1,0);
        placeTileAboveLevelOne(l1, l2, l3);

        l1 = new Location(0,-2);
        l2 = new Location(1,-1);
        l3 = new Location(1, -2);
        placeTileAboveLevelOne(l1, l2, l3);
    }

    private void placeATileOnLevelOne(Location l1, Location l2, Location l3) throws Exception {
        Tile adjacentTile = tileBuilder.getTileWithLocations(l1, l2, l3);
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, adjacentTile);
        tilePlacementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);
    }

    private void placeTileAboveLevelOne(Location l1, Location l2, Location l3) throws Exception {
        Tile adjacentTile = tileBuilder.getTileWithLocations(l1, l2, l3);
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, adjacentTile);
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
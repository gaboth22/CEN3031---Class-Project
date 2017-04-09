package Steve.PlayGeneration;

import GameBoard.GameBoardImpl;
import GameBoard.GameBoardState;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.Player;
import Player.PlayerID;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ExpansionHelperTest {
    private GameBoardImpl gameBoard;
    private GameBoardState gameState;
    private BuildPhase phase;

    @Before
    public void setUp() {
        gameBoard = new GameBoardImpl();
        gameState = null;
    }

    @Test
    public void helperChoosesCorrectPlaceToExpand() throws Exception{
        placeLevelOneTiles();
        setUpGameBoardState();
        foundSettlement(new Location(1,0));
        setUpGameBoardState();
        phase = ExpansionHelper.expansionChoice(gameState.getPlacedHexagons(), gameState.getPlayerTwo(), gameState.getGamePieceMap());
        assertEquals(phase.getLocationToPlacePieceOn(), new Location(2,0));

    }


    private void setUpGameBoardState() {
        gameState = new GameBoardState(gameBoard.getPlayer(PlayerID.PLAYER_ONE), gameBoard.getPlayer(PlayerID.PLAYER_TWO), 1, gameBoard.getPlacedHexagons(), gameBoard.getGamePieceMap(), gameBoard.getPlaceableLocations(), gameBoard.getNukeableVolcanoLocations());
    }


    private void placeLevelOneTiles() throws Exception{

        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        Location[] locations1 = new Location[]{
                new Location(1, 1),
                new Location(0, 1),
                new Location(0, 2)
        };

        placeATileOnLevelOne(new TileImpl(Arrays.asList(terrains), Arrays.asList(locations1)));

        Location[] locations2 = new Location[]{
                new Location(3, -1),
                new Location(2,-1),
                new Location(2,0)
        };

        placeATileOnLevelOne(new TileImpl(Arrays.asList(terrains), Arrays.asList(locations2)));

    }

    private void placeATileOnLevelOne(Tile tile) throws Exception {
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, tile);
        tilePlacementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);
    }

    private void foundSettlement(Location newSettlementLocation) throws Exception {
        GamePiece foundingVillager = new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.VILLAGER);
        phase = new BuildPhase(foundingVillager, newSettlementLocation);
        phase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(phase);
    }
}

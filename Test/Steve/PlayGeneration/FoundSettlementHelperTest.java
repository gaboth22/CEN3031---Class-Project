package Steve.PlayGeneration;

import GameBoard.GameBoardImpl;
import GameBoard.GameBoardState;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.Player;
import Player.PlayerID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoundSettlementHelperTest {
    private GameBoardImpl gameBoard;
    private GameBoardState gameState;
    private BuildPhase phase;

    @Before
    public void setUp() {
        gameBoard = new GameBoardImpl();
        gameState = null;
    }

    @Test
    public void shouldPlaceVillagerNextToSettlement() throws Exception {
        foundSettlement(new Location(1,-1));
        setUpGameBoardState();
        phase = FoundSettlementHelper.pickLocationForNewSettlement(gameState, PlayerID.PLAYER_ONE);
        assertEquals(phase.getLocationToPlacePieceOn(), new Location(1, 0));
    }

   @Test
    public void shouldNotDoStrategicPlacement() {
        setUpGameBoardState();
        phase = FoundSettlementHelper.pickLocationForNewSettlement(gameState, PlayerID.PLAYER_ONE);
        assertTrue(phase == null);
    }

    private void foundSettlement(Location newSettlementLocation) throws Exception {
        GamePiece foundingVillager = new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.VILLAGER);
        phase = new BuildPhase(foundingVillager, newSettlementLocation);
        phase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(phase);
    }

    private void setUpGameBoardState() {
        gameState = new GameBoardState(gameBoard.getPlayer(PlayerID.PLAYER_ONE), gameBoard.getPlayer(PlayerID.PLAYER_TWO), 1, gameBoard.getPlacedHexagons(), gameBoard.getGamePieceMap(), gameBoard.getPlaceableLocations(), gameBoard.getNukeableVolcanoLocations());
    }
}
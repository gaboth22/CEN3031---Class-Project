package GamePieceMapTest;

import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import GamePieceMap.*;
import Location.Location;
import Player.PlayerID;

import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GamePieceMapTest {

    private GamePieceMap gameMap;
    private GamePiece gamePiece;
    private Location location;

    @Before
    public void setUp() {
        gameMap = new GamePieceMap();
        gamePiece = new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.VILLAGER);
        location = new Location(10,10);
    }

    @After
    public void tearDown() {
        gameMap = null;
        gamePiece = null;
        location = null;
    }

    @Test
    public void checksIfThereIsAPieceAtALocationAfterInsertingOne() throws LocationNotEmptyException {
        gameMap.insertAPieceAt(location, gamePiece);
        assertTrue(gameMap.isThereAPieceAt(location));
    }

    @Test(expected = LocationNotEmptyException.class)
    public void givenAPlayerInsertsAGamePieceWhereTheLocationIsAlreadyOccupied() throws LocationNotEmptyException {
        gameMap.insertAPieceAt(location, gamePiece);

        GamePiece secondGamePieceSameLocation = new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.TOTORO);
        gameMap.insertAPieceAt(location, secondGamePieceSameLocation);
    }

    @Test
    public void ensuresAGamePieceHasBeenRemovedFromTheMap() throws LocationNotEmptyException {
        gameMap.insertAPieceAt(location, gamePiece);
        gameMap.removeAPieceAt(location);
        assertFalse(gameMap.isThereAPieceAt(location));
    }

    @Test
    public void ensureTheProperPlayerIDIsFoundAtLocation() throws LocationNotEmptyException {
        gameMap.insertAPieceAt(location, gamePiece);
        assertEquals(PlayerID.PLAYER_TWO, gameMap.getPieceOwnerIdAtLocation(location));
    }

    @Test
    public void ensureTheProperPieceTypeIsFoundAtLocation() throws LocationNotEmptyException {
        gameMap.insertAPieceAt(location, gamePiece);
        assertEquals(TypeOfPiece.VILLAGER, gameMap.getPieceTypeAtLocation(location));
    }

    @Test
    public void ensureThatSetOfOccupiedLocationsIsValid() throws LocationNotEmptyException {
        gameMap.insertAPieceAt(location, gamePiece);
        assertTrue(gameMap.getSetOfOccupiedLocations().contains(location));
    }

    @Test
    public void ensureThatChangingAReturnedSetDoesNotChangeGameMap() throws LocationNotEmptyException {
        gameMap.insertAPieceAt(location, gamePiece);
        gameMap.getSetOfOccupiedLocations().clear();
        assertTrue(gameMap.getSetOfOccupiedLocations().contains(location));
    }
}
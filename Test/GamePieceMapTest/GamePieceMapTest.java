package GamePieceMapTest;

import org.junit.Test;
import GamePieceMap.*;
import Location.Location;
import Player.PlayerID;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GamePieceMapTest {

    private GamePieceMap gameMap = new GamePieceMap();
    private GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.VILLAGER);
    private Location location = new Location(10,10);

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
    public void ensuresAGamePieceHasBeenRemovedFromTheMap() throws LocationNotEmptyException{
        gameMap.insertAPieceAt(location, gamePiece);
        gameMap.removeAPieceAt(location);
        assertFalse(gameMap.isThereAPieceAt(location));
    }

    @Test
    public void ensureTheProperPlayerIDIsFoundAtLocation() throws LocationNotEmptyException{
        gameMap.insertAPieceAt(location, gamePiece);
        assertEquals(PlayerID.PLAYER_TWO, gameMap.getPieceOwnerIdAtLocation(location));
    }

    @Test
    public void ensureTheProperPieceTypeIsFoundAtLocation() throws LocationNotEmptyException{
        gameMap.insertAPieceAt(location, gamePiece);
        assertEquals(TypeOfPiece.VILLAGER, gameMap.getPieceTypeAtLocation(location));
    }
}
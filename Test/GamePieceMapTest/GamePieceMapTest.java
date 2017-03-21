package GamePieceMapTest;

import org.junit.Test;

import GamePieceMap.*;
import Location.Location;
import org.junit.Test;

public class GamePieceMapTest {

    @Test(expected = LocationNotEmptyException.class)
    public void givenAPlayerInsertsAGamePieceWhereTheLocationIsAlreadyOccupied() throws Exception {
        GamePieceMap gameMap = new GamePieceMap();

        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_2, TypeOfPiece.MEEPLE);
        Location location = new Location(10,10);
        gameMap.insertAPieceAt(location, gamePiece);

        GamePiece secondGamePieceSameLocation = new GamePiece(PlayerID.PLAYER_2, TypeOfPiece.TOTORO);
        gameMap.insertAPieceAt(location, secondGamePieceSameLocation);
    }
}
package Settlements;

import GamePieceMap.*;
import Location.Location;
import Player.PlayerID;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SettlementCreatorTest {

    GamePieceMap pieceMap;

    @Before
    public void setUpPieceMap() throws Exception {
        Location[] listOfPlayerOneLocations = listOfPlayerOneLocations();
        GamePiece[] listOfPlayerOneGamePieces = listOfPlayerOneGamePieces();
        
    }

    private void insertPlayerPieces(Location[] locations, GamePiece[] pieces) throws Exception {
        for(int i = 0; i < locations.length; i++) {
            pieceMap.insertAPieceAt(locations[i], pieces[i]);
        }
    }

    private Location[] listOfPlayerOneLocations() {
        return new Location[] {
                //Village One: Three Villagers
                new Location(0,0),
                new Location(0,1),
                new Location(0,2),

                //Village Two: Five Villagers, One Totoro
                new Location(3,3),
                new Location(3,4),
                new Location(4,3),
                new Location(4,4),
                new Location(5,3),
                new Location(6,2)
        };
    }

    private GamePiece[] listOfPlayerOneGamePieces() {
        PlayerID playerID = PlayerID.PLAYER_ONE;
        return new GamePiece[] {
                //Village One: Three Villagers
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),

                //Village Two: Five Villagers, One Totoro
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.TOTORO)
        };
    }

    private Location[] listOfPlayerTwoLocations() {
        return new Location[] {
                //Village One: Two Villagers
                new Location(5,4),
                new Location(6,3),

                //Village Two: Two Villagers
                new Location(3,2),
                new Location(4,2),

                //Village Three: One Villager
                new Location(2,4),

                //Village Four: Five Villagers, One Totoro, One Tiger
                new Location(2,1),
                new Location(2,0),
                new Location(3,-1),
                new Location(2,-1),
                new Location(3,-2),
                new Location(3,-3),
                new Location(2,-2)
        };
    }

    private GamePiece[] listOfPlayerTwoGamePieces() {
        PlayerID playerID = PlayerID.PLAYER_TWO;
        return new GamePiece[] {
                //Village One: Two Villagers
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),

                //Village Two: Two Villagers
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),

                //Village Three: One Villager
                new GamePiece(playerID, TypeOfPiece.VILLAGER),

                //Village Four: Five Villagers, One Totoro, One Tiger
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.TOTORO),
                new GamePiece(playerID, TypeOfPiece.TIGER)
        };
    }

}

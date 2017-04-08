package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameBoard.GameBoardState;
import GameMaster.ServerComm.Parsers.BasicParser;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.PlayerID;

public class BuildInfoToBuildPhase {

    static final int FOUND_X_IND = 5;
    static final int FOUND_Y_IND = 3;

    public static BuildPhase getFoundPhase(String play, PlayerID playerID) {
        //e.g. "FOUNDED SETTLEMENT AT 0 0 1"
        int x = BasicParser.getIntegerFromStringAtIndex(play, FOUND_X_IND);
        int y = BasicParser.getIntegerFromStringAtIndex(play, FOUND_Y_IND);
        Location location = new Location(x,y);

        GamePiece piece = new GamePiece(playerID, TypeOfPiece.VILLAGER);
        BuildPhase toReturn = new BuildPhase(piece, location);
        toReturn.setBuildType(BuildType.FOUND);
        return toReturn;
    }

    public static BuildPhase getExpandPhase(String play, GameBoardState gameBoardState, PlayerID playerID) {
        //TODO:
        return null;
    }

    public static BuildPhase getPlaceTotoroPhase(String play, PlayerID playerID) {
        //TODO:
        return null;
    }

    public static BuildPhase getPlaceTigerPhase(String play, PlayerID playerID) {
        //TODO:
        return null;
    }
}

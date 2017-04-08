package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameMaster.ServerComm.Parsers.BasicParser;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.PlayerID;

public class BuildInfoToPlaceTiger {

    private static final int PLACE_TIGER_X_IND = 6;
    private static final int PLACE_TIGER_Y_IND = 4;

    public static BuildPhase getPlaceTigerPhase(String play, PlayerID playerID) {
        //e.g "BUILT TIGER PLAYGROUND AT 0 0 1"
        int x = BasicParser.getIntegerFromStringAtIndex(play, PLACE_TIGER_X_IND);
        int y = BasicParser.getIntegerFromStringAtIndex(play, PLACE_TIGER_Y_IND);
        Location location = new Location(x,y);
        GamePiece toPlace = new GamePiece(playerID, TypeOfPiece.TIGER);

        BuildPhase buildPhase = new BuildPhase(toPlace, location);
        buildPhase.setBuildType(BuildType.PLACE_TIGER);
        return buildPhase;
    }

}

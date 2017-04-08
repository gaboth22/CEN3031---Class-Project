package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameMaster.ServerComm.Parsers.BasicParser;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.PlayerID;
import Location.Location;

public class BuildInfoToPlaceTotoro {

    private static final int PLACE_TOTORO_X_IND = 6;
    private static final int PLACE_TOTORO_Y_IND = 4;

    public static BuildPhase getPlaceTotoroPhase(String play, PlayerID playerID) {

        //e.g "BUILT TOTORO SANCTUARY AT 0 0 1"
        int x = BasicParser.getIntegerFromStringAtIndex(play, PLACE_TOTORO_X_IND);
        int y = BasicParser.getIntegerFromStringAtIndex(play, PLACE_TOTORO_Y_IND);
        Location location = new Location(x,y);
        GamePiece toPlace = new GamePiece(playerID, TypeOfPiece.TOTORO);

        BuildPhase buildPhase = new BuildPhase(toPlace, location);
        buildPhase.setBuildType(BuildType.PLACE_TOTORO);
        return buildPhase;
    }

}

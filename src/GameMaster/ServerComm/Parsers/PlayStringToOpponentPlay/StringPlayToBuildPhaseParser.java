package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameBoard.GameBoardState;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.PlayerID;
import Settlements.Creation.SettlementCreator;

public class StringPlayToBuildPhaseParser {

    public static BuildPhase getBuildPhase(String play, GameBoardState gameBoardState, PlayerID playerID) {

        BuildType buildType = StringPlayToTypeOfBuildParser.getBuildType(play);

        BuildPhase buildPhase;

        if(buildType.equals(BuildType.FOUND)) {
            buildPhase = BuildInfoToBuildPhase.getFoundPhase(play, playerID);
        }
        else if(buildType.equals(BuildType.EXPAND)) {
            buildPhase = BuildInfoToBuildPhase.getExpandPhase(play, gameBoardState, playerID);
        }
        else if(buildType.equals(BuildType.PLACE_TOTORO)) {
            buildPhase = BuildInfoToPlaceTotoro.getPlaceTotoroPhase(play, playerID);
        }
        else {
            buildPhase = BuildInfoToPlaceTiger.getPlaceTigerPhase(play, playerID);
        }

        return buildPhase;
    }
}

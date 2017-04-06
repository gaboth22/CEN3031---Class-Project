package GameMaster.ServerComm.Parsers;

import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Terrain.Terrain.Terrain;

public class BuildPhaseToStringConverter {

    public static String getStringConversion(BuildPhase phase, Terrain expandedOn) {

        if(phase == null)
            return "UNABLE TO BUILD";

        String buildType = BuildTypeToStringForOutgoingPlay.getString(phase.getBuildType());

        int ourX = phase.getLocationToPlacePieceOn().getX();
        int ourY = phase.getLocationToPlacePieceOn().getY();

        int serverX = ourY;
        int serverY = ExtraCoordinateGenerator.generate(ourX, ourY);
        int serverZ = ourX;

        String conversion = buildType + " AT " + serverX + " " + serverY + " " + serverZ;

        if(expandedOn != null && phase.getBuildType() == BuildType.EXPAND) {
            conversion += " ";
            conversion += TerrainToStringConverter.getString(expandedOn);
        }

        return conversion;

    }
}

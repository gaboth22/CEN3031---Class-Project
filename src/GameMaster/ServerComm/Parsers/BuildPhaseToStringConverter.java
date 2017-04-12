package GameMaster.ServerComm.Parsers;

import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Terrain.Terrain.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BuildPhaseToStringConverter {

    public static String getStringConversion(BuildPhase phase, Terrain expandedOn) {

        if(phase == null)
            return "UNABLE TO BUILD";

        String buildType = BuildTypeToStringForOutgoingPlay.getString(phase.getBuildType());

        int ourX;
        int ourY;
        if(phase.getBuildType() == BuildType.EXPAND) {
            Set<Location> settlementAsSet = phase.getSettlement().getSetOfLocationsInSettlement();
            List<Location> settlementAsList = new ArrayList<Location>(settlementAsSet);
            ourX = settlementAsList.get(0).getX();
            ourY = settlementAsList.get(0).getY();
        }
        else {
            ourX = phase.getLocationToPlacePieceOn().getX();
            ourY = phase.getLocationToPlacePieceOn().getY();
        }

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

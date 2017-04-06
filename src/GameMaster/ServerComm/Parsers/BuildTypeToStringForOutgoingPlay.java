package GameMaster.ServerComm.Parsers;

import Play.BuildPhase.BuildType;

public class BuildTypeToStringForOutgoingPlay {

    public static String getString(BuildType type) {

        if(type == BuildType.EXPAND)
            return "EXPAND SETTLEMENT";

        else if(type == BuildType.FOUND)
            return "FOUND SETTLEMENT";

        else if(type == BuildType.PLACE_TIGER)
            return "BUILD TIGER PLAYGROUND";

        else if(type == BuildType.PLACE_TOTORO)
            return "BUILD TOTORO SANCTUARY";

        else return "";
    }
}

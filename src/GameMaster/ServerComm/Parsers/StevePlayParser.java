package GameMaster.ServerComm.Parsers;

import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Terrain.Terrain.Terrain;

public class StevePlayParser {
    public static String parse(
            BuildPhase buildPhase,
            TilePlacementPhase tilePlacementPhase,
            Terrain expandedOn,
            String[] serverStuff) {

        //e.g. PLACE <tile> AT <x> <y> <z> <orientation> BUILD TIGER PLAYGROUND AT <x> <y> <z>
        String piecePlacement = BuildPhaseToStringConverter.getStringConversion(buildPhase, expandedOn);
        String tilePlacement = TilePlacementPhaseToStringConverter.getStringConversion(tilePlacementPhase);

        return "PLACE " + tilePlacement + " " + piecePlacement;
    }
}

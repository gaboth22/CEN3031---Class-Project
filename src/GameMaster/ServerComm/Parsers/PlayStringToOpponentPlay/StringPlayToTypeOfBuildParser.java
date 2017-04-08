package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameMaster.ServerComm.Parsers.BasicParser;
import Play.BuildPhase.BuildType;

import java.security.InvalidParameterException;

public class StringPlayToTypeOfBuildParser {

    static final int BUILD_TYPE_INDEX = 0;
    static final int BUILD_TYPE_SECOND_INDEX = 1;

    static final String FOUND_SETTLEMENT = "FOUNDED";
    static final String EXPAND_SETTLEMENT = "EXPANDED";
    static final String PLACE_TOTORO = "TOTORO";
    static final String PLACE_TIGER = "TIGER";

    public static BuildType getBuildType(String play) {
        String buildTypeSnippet = BasicParser.getSubstringFromStringAtIndex(play, BUILD_TYPE_INDEX);
        String secondBuildTypeSnippet = BasicParser.getSubstringFromStringAtIndex(play, BUILD_TYPE_SECOND_INDEX);

        BuildType buildType;

        if(buildTypeSnippet.equals(FOUND_SETTLEMENT)) {
            buildType = BuildType.FOUND;
        }
        else if(buildTypeSnippet.equals(EXPAND_SETTLEMENT)) {
            buildType = BuildType.EXPAND;
        }
        else{
            if(secondBuildTypeSnippet.equals(PLACE_TOTORO)) {
                buildType = BuildType.PLACE_TOTORO;
            }
            else if(secondBuildTypeSnippet.equals(PLACE_TIGER)) {
                buildType = BuildType.PLACE_TIGER;
            }
            else {
                throw new InvalidParameterException("The play is formatted incorrectly when given to StringPlayToTypeOfBuildParser");
            }
        }

        return buildType;
    }
}

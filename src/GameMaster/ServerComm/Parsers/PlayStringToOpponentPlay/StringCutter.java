package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameMaster.ServerComm.Parsers.BasicParser;
import GameMaster.ServerComm.Parsers.Split;

public class StringCutter {

    public static String cutFirstNSubStringsFromStringSplitBySpace(String stringToCut, int index) {
        String cutString = "";
        String[] stringToCutInArray = Split.byWhiteSpace(stringToCut);
        for(int i = index; i < stringToCutInArray.length; i++) {
            cutString += BasicParser.getSubstringFromStringAtIndex(stringToCut, i);
            if(i != stringToCutInArray.length-1)
                cutString += " ";
        }
        return cutString;
    }
}

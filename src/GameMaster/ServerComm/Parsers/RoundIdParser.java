package GameMaster.ServerComm.Parsers;

public class RoundIdParser {
    public static String getRoundId(String message) {
        int ROUND_ID_INDEX = 2;
        return BasicParser.getSubstringFromStringAtIndex(message, ROUND_ID_INDEX);
    }
}

package GameMaster.ServerComm.Parsers;

public class RoundCountParser {
    public static String getRoundCount(String messageFromServer) {
        int ROUND_COUNT_INDEX = 6;
        return BasicParser.getSubstringFromStringAtIndex(messageFromServer, ROUND_COUNT_INDEX);
    }
}

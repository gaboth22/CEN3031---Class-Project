package GameMaster.ServerComm.Parsers;

public class RoundCountParser {
    public static int getRoundCount(String messageFromServer) {
        int ROUND_COUNT_INDEX = 4;
        int roundCount;
        roundCount = BasicParser.getIntegerFromStringAtIndex(messageFromServer, ROUND_COUNT_INDEX);
        return roundCount;
    }
}

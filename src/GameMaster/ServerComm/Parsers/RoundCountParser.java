package GameMaster.ServerComm.Parsers;

public class RoundCountParser {
    public static int getRoundCount(String messageFromServer) {
        int roundCount;
        roundCount = BasicParser.getIntegerFromStringAtIndex(messageFromServer, 4);
        return roundCount;
    }
}

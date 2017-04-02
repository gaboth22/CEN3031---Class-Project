package GameMaster.ServerComm.Parsers;

public class MoveNumberParser {
    public static String getMoveNumber(String message) {
        String moveNumber;
        moveNumber = BasicParser.getSubstringFromStringAtIndex(message, 10);
        return moveNumber;
    }
}

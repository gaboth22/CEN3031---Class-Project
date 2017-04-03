package GameMaster.ServerComm.Parsers;

public class MoveNumberParser {
    public static String getMoveNumber(String message) {
        int MOVE_NUMBER_INDEX = 10;
        String moveNumber;
        moveNumber = BasicParser.getSubstringFromStringAtIndex(message, MOVE_NUMBER_INDEX);
        return moveNumber;
    }
}

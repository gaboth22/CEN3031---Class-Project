package GameMaster.ServerComm.Parsers;

public class OwnMoveFormatter {
    public static String getFormattedMove(String steveMove, String gameId, String moveNumber) {
        return "GAME " + gameId + " MOVE " + moveNumber + " " + steveMove;
    }
}

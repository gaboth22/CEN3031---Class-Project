package GameMaster.ServerComm.Parsers;

public class GameIdParser {
    public static String getGameIdForOwnMove(String messageFromServer) {
        String gid;
        gid = BasicParser.getSubstringFromStringAtIndex(messageFromServer, 5);
        return gid;
    }

    public static String getGameIdForOtherPlayersMove(String messageFromServer) {
        String gid;
        gid = BasicParser.getSubstringFromStringAtIndex(messageFromServer, 1);
        return gid;
    }
}

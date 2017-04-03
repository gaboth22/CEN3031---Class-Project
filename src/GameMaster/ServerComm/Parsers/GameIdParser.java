package GameMaster.ServerComm.Parsers;

public class GameIdParser {
    public static String getGameIdForOwnMove(String messageFromServer) {
        int GAME_ID_INDEX =  5;
        String gid;
        gid = BasicParser.getSubstringFromStringAtIndex(messageFromServer, GAME_ID_INDEX);
        return gid;
    }

    public static String getGameIdForOtherPlayersMove(String messageFromServer) {
        int GAME_ID_INDEX =  1;
        String gid;
        gid = BasicParser.getSubstringFromStringAtIndex(messageFromServer, GAME_ID_INDEX);
        return gid;
    }
}

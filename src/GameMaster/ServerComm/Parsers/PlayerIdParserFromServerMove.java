package GameMaster.ServerComm.Parsers;

public class PlayerIdParserFromServerMove {
    public static String getPlayerId(String messageFromServer) {
        int PLAYER_ID_INDEX = 5;
        String pid;
        pid = BasicParser.getSubstringFromStringAtIndex(messageFromServer,PLAYER_ID_INDEX);
        return pid;
    }
}

package GameMaster.ServerComm.Parsers;

public class PlayerIdParser {

    public static String getPlayerId(String messageFromServer) {
        int PLAYER_ID_INDEX = 6;
        String pid;
        pid = BasicParser.getSubstringFromStringAtIndex(messageFromServer,PLAYER_ID_INDEX);
        return pid;
    }
}

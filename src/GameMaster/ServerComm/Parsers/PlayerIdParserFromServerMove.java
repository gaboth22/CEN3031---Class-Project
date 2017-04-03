package GameMaster.ServerComm.Parsers;

public class PlayerIdParserFromServerMove {
    public static String getPlayerId(String messageFromServer) {
        String pid;
        pid = BasicParser.getSubstringFromStringAtIndex(messageFromServer,5);
        return pid;
    }
}

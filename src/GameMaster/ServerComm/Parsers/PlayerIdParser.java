package GameMaster.ServerComm.Parsers;

public class PlayerIdParser {

    public static String getPlayerId(String messageFromServer) {
        String pid;
        pid = BasicParser.getSubstringFromStringAtIndex(messageFromServer,6);
        return pid;
    }
}

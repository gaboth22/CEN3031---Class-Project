package GameMaster.ServerComm.Parsers;

public class TileInformationParser {
    public static String getTile(String messageFromServer) {
        String tileInfo;
        tileInfo = BasicParser.getSubstringFromStringAtIndex(messageFromServer, 12);
        return tileInfo;
    }
}

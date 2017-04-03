package GameMaster.ServerComm.Parsers;

public class TileInformationParser {
    public static String getTile(String messageFromServer) {
        int TILE_INFO_INDEX = 12;
        String tileInfo;
        tileInfo = BasicParser.getSubstringFromStringAtIndex(messageFromServer, TILE_INFO_INDEX);
        return tileInfo;
    }
}

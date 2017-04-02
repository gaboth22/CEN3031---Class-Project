package GameMaster.ServerComm.Parsers;

import java.security.InvalidParameterException;

public class TileInformationParser {
    public static String getTile(String messageFromServer) {
        String BY_WHITE_SPACE = " ";

        if(messageFromServer == null)
            throw new InvalidParameterException("Asking to parse null string for tile info from server");

        String[] componentsOfMessage = messageFromServer.split(BY_WHITE_SPACE);
        String tileInfo = null;

        try {
            tileInfo = componentsOfMessage[12];
        }
        catch(IndexOutOfBoundsException e) {
            throw new InvalidParameterException("Asking to parse wrong string for tile info from server");
        }

        return tileInfo;
    }
}

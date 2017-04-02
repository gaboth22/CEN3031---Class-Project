package GameMaster.ServerComm.Parsers;

import java.security.InvalidParameterException;

public class PlayerIdParserFromMove {
    public static int getPlayerId(String messageFromServer) {
        String BY_WHITE_SPACE = " ";

        if(messageFromServer == null)
            throw new InvalidParameterException("Asking to parse null string for pid from server");

        String[] componentOfMessage = messageFromServer.split(BY_WHITE_SPACE);
        int pid;

        try {
            pid = Integer.parseInt(componentOfMessage[5]);
        }
        catch(IndexOutOfBoundsException e) {
            throw new InvalidParameterException("Asking to parse wrong string for pid from server");
        }

        return pid;
    }
}

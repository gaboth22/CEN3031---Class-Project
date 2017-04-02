package GameMaster.ServerComm.Parsers;

public class OtherPlayersPlayParser {
    public static String getPlay(String messageFromServer) {
        String BY_WHITE_SPACE = " ";
        String play = "";
        String [] componentsOfMessage = messageFromServer.split(BY_WHITE_SPACE);

        for(int i = 6; i < componentsOfMessage.length; i++) {
            play += BasicParser.getSubstringFromStringAtIndex(messageFromServer, i);
            if(i != componentsOfMessage.length-1)
                play += " ";
        }

        return play;
    }
}

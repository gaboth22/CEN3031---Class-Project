package GameMaster.ServerComm.Parsers;

public class OtherPlayersPlayParser {
    public static String getPlay(String messageFromServer) {
        int START_OF_PLAY_INDEX = 6;
        String BY_WHITE_SPACE = " ";
        String play = "";
        String [] componentsOfMessage = messageFromServer.split(BY_WHITE_SPACE);

        for(int i = START_OF_PLAY_INDEX; i < componentsOfMessage.length; i++) {
            play += BasicParser.getSubstringFromStringAtIndex(messageFromServer, i);
            if(i != componentsOfMessage.length-1)
                play += " ";
        }

        return play;
    }
}

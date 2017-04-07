package GameMaster.ServerComm.Parsers;

public class ChallengeIdParser {

    public static String getChallengeId(String message) {
        int CHALLENGE_ID_INDEX = 2;
        return BasicParser.getSubstringFromStringAtIndex(message, CHALLENGE_ID_INDEX);
    }
}

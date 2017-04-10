package GameMaster.ServerComm.Parsers;

import java.security.InvalidParameterException;

public class BasicParser {
    public static int getIntegerFromStringAtIndex(String string, int stringIndex) {
        if(string == null)
            throw new InvalidParameterException("Asking to parse a null string from server");

        int parsedInt;

        try{
            String subStr = getSubstringAtIndex(string, stringIndex);
            parsedInt = Integer.parseInt(subStr);
        }
        catch(Exception e) {
            throw new InvalidParameterException("From server string parser: " +
                                                "\nException: " + e.getClass() +
                                                "\n Message: "+ e.getMessage() +
                                                "\nPassed string: " + string);
        }

        return parsedInt;
    }

    private static String getSubstringAtIndex(String str, int index) throws Exception {
        String[] stringComponents = Split.byWhiteSpace(str);
        return stringComponents[index];
    }

    public static String getSubstringFromStringAtIndex(String string, int stringIndex) {
        if(string == null)
            throw new InvalidParameterException("Asking to parse null string from server");

        String subStr;

        try {
            subStr = getSubstringAtIndex(string, stringIndex);
        }
        catch(Exception e) {
            throw new InvalidParameterException("From server string parser: " +
                                                "\nException: " + e.getClass() +
                                                "\n Message: "+ e.getMessage() +
                                                "\nPassed string: " + string);
        }

        return subStr;
    }
}

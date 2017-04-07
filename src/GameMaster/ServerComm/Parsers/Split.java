package GameMaster.ServerComm.Parsers;

public class Split {

    public static String[] byPlus(String str) {
        String BY_PLUS_SIGN = "\\+";
        String[] split_string = str.split(BY_PLUS_SIGN);
        return split_string;
    }
}

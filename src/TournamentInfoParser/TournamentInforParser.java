package TournamentInfoParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class TournamentInforParser {

    private static String ipAddress;
    private static String portNumber;
    private static String tournamentPass;
    private static String username;
    private static String password;

    public static void parse(String filename) throws Exception {
        InputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);

        ipAddress = br.readLine();
        portNumber = br.readLine();
        tournamentPass = br.readLine();
        username = br.readLine();
        password = br.readLine();
    }

    public static String getIpAddress() {
        return ipAddress.replace("IP:", "");
    }

    public static int getPortNumber() {
        return Integer.parseInt(portNumber.replace("PORT:", ""));
    }

    public static String getTournamentPass() {
        return tournamentPass.replace("TOURNAMENT_PASS:", "");
    }

    public static String getUsername() {
        return username.replace("USERNAME:", "");
    }

    public static String getPassword() {
        return password.replace("PASS:", "");
    }
}

import Debug.Debug;
import GameMaster.Game.Game;
import GameMaster.GameMaster;
import GameMaster.ServerComm.GameClient;
import GameMaster.ServerComm.ServerClient;
import Player.PlayerID;
import Steve.PlayGeneration.ProfitablePlayGeneration;
import TournamentInfoParser.TournamentInforParser;

public class Main {

    private static String SERVER_IP;
    private static int SERVER_PORT;
    private static String TOURNAMENT_PASSWORD;
    private static String USERNAME;
    private static String PASSWORD;

    public static void main(String[] args) throws Exception {

        TournamentInforParser.parse("./src/tournament.txt");

        SERVER_IP = TournamentInforParser.getIpAddress();
        SERVER_PORT = TournamentInforParser.getPortNumber();
        TOURNAMENT_PASSWORD = TournamentInforParser.getTournamentPass();
        USERNAME = TournamentInforParser.getUsername();
        PASSWORD = TournamentInforParser.getPassword();
        
        Debug.enableAllDebugLevels();
        Debug.enableLogFile("log.log");

        GameClient client = new ServerClient(SERVER_IP, SERVER_PORT);

        Game gameOne = new Game(PlayerID.PLAYER_ONE, new ProfitablePlayGeneration());
        Game gameTwo = new Game(PlayerID.PLAYER_TWO, new ProfitablePlayGeneration());

        GameMaster gameMaster = new GameMaster(client, gameOne, gameTwo);
        gameMaster.setTournamentPassword(TOURNAMENT_PASSWORD);
        gameMaster.setUsername(USERNAME);
        gameMaster.setPassword(PASSWORD);
        gameMaster.start();
    }
}
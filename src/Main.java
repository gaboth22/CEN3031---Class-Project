import Debug.Debug;
import GameMaster.Game.Game;
import GameMaster.GameMaster;
import GameMaster.ServerComm.ServerClient;
import Player.PlayerID;
import Steve.PlayGeneration.ProfitablePlayGeneration;
import Steve.PlayGeneration.SimplePlayGenerator;

public class Main {
    private static final String SERVER_IP = "192.168.1.142";
    private static final int SERVER_PORT = 6969;

    private static final String TOURNAMENT_PASSWORD = "heygang";
    private static final String USERNAME = "P";
    private static final String PASSWORD = "P";


    public static void main(String[] args) throws Exception {
        Debug.enableAllDebugLevels();
        Debug.enableLogFile("log.log");
        ServerClient client = new ServerClient(SERVER_IP, SERVER_PORT);
        Game gameOne = new Game(PlayerID.PLAYER_ONE, new ProfitablePlayGeneration());
        gameOne.runWithGui();
        Game gameTwo = new Game(PlayerID.PLAYER_TWO, new ProfitablePlayGeneration());
        gameTwo.runWithGui();
        GameMaster gameMaster = new GameMaster(client, gameOne, gameTwo);
        gameMaster.setTournamentPassword(TOURNAMENT_PASSWORD);
        gameMaster.setUsername(USERNAME);
        gameMaster.setPassword(PASSWORD);
        gameMaster.start();
    }
}
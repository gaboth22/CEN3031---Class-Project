import Debug.Debug;
import GameMaster.Game.Game;
import GameMaster.GameMaster;
import GameMaster.ServerComm.GameClient;
import GameMaster.ServerComm.ServerClient;
import Player.PlayerID;
import Steve.PlayGeneration.ProfitablePlayGeneration;
import Steve.PlayGeneration.SimplePlayGenerator;

public class Main {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8000;

    private static final String TOURNAMENT_PASSWORD = "heygang";
    private static final String USERNAME = "P";
    private static final String PASSWORD = "P";

    private static final boolean CONSOLE_INPUT_IS_DISABLED = false;

    public static void main(String[] args) throws Exception {
        Debug.enableAllDebugLevels();
        Debug.enableLogFile("log.log");
        GameClient client = new ServerClient(SERVER_IP, SERVER_PORT);

        Game gameOne = new Game(PlayerID.PLAYER_ONE, new SimplePlayGenerator());
        gameOne.runWithGui(CONSOLE_INPUT_IS_DISABLED);

        Game gameTwo = new Game(PlayerID.PLAYER_TWO, new SimplePlayGenerator());
        gameTwo.runWithGui(CONSOLE_INPUT_IS_DISABLED);

        GameMaster gameMaster = new GameMaster(client, gameOne, gameTwo);
        gameMaster.setTournamentPassword(TOURNAMENT_PASSWORD);
        gameMaster.setUsername(USERNAME);
        gameMaster.setPassword(PASSWORD);
        gameMaster.start();
    }
}
import Debug.Debug;
import GameMaster.Game.Game;
import GameMaster.GameMaster;
import GameMaster.ServerComm.ServerClient;
import Player.PlayerID;
import Steve.PlayGeneration.SimplePlayGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        Debug.enableAllDebugLevels();
        Debug.enableLogFile("log.log");
        ServerClient client = new ServerClient("127.0.0.1", 8000);
        Game gameOne = new Game(PlayerID.PLAYER_ONE, new SimplePlayGenerator());
        gameOne.runWithGui();
        Game gameTwo = new Game(PlayerID.PLAYER_TWO, new SimplePlayGenerator());
        GameMaster gameMaster = new GameMaster(client, gameOne, gameTwo);
        gameMaster.start();
    }
}

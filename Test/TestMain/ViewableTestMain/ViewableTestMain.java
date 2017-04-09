package TestMain.ViewableTestMain;

import Debug.Debug;
import GameMaster.Game.Game;
import GameMaster.GameMaster;
import GameMaster.ServerComm.GameClient;
import Player.PlayerID;
import Steve.PlayGeneration.SimplePlayGenerator;
import TestMain.TestClient;

public class ViewableTestMain {

    private static final String TOURNAMENT_PASSWORD = "TPASS :D:D";
    private static final String USERNAME = "PEPE";
    private static final String PASSWORD = "UF_ROCKS";

    private static final String fileDirectoryToReadTestsFrom = "./Test/TestMain/ViewableTestMain/testLogs/";
    private static final String filePathToWriteServer = fileDirectoryToReadTestsFrom + "testServerLog.log";
    private static final String filePathTestLog = fileDirectoryToReadTestsFrom + "test.log";

    public static void main(String[] args) throws Exception {
        Debug.enableAllDebugLevels();

        Debug.enableLogFile("testLog.log");


        TestClient testClient = new TestClient(filePathTestLog, filePathToWriteServer);
        testClient.setConsoleInterrupt(true);
        GameClient client = testClient;

        Game gameOne = new Game(PlayerID.PLAYER_ONE, new SimplePlayGenerator());
        gameOne.runWithGui(false);

        Game gameTwo = new Game(PlayerID.PLAYER_TWO, new SimplePlayGenerator());
        gameTwo.runWithGui(false);

        GameMaster gameMaster = new GameMaster(client, gameOne, gameTwo);
        gameMaster.setTournamentPassword(TOURNAMENT_PASSWORD);
        gameMaster.setUsername(USERNAME);
        gameMaster.setPassword(PASSWORD);
        gameMaster.start();
    }
}

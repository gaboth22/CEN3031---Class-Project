package TestMain.RunnableTest;

import Debug.Debug;
import GameMaster.Game.Game;
import GameMaster.GameMaster;
import GameMaster.ServerComm.GameClient;
import Player.PlayerID;
import Steve.PlayGeneration.SimplePlayGenerator;
import TestMain.TestClient;
import org.junit.Test;

public class RunnableTestMain {

    private static final String TOURNAMENT_PASSWORD = "TPASS :D:D";
    private static final String USERNAME = "PEPE";
    private static final String PASSWORD = "UF_ROCKS";

    private static final String fileDirectoryToReadTestsFrom = "./Test/TestMain/RunnableTest/testLogs/testLogs/";
    private static final String filePathToWriteTestResultsTo = fileDirectoryToReadTestsFrom + "testServerLog.log";

    @Test
    public void runTestOne() throws Exception {
        Debug.enableAllDebugLevels();

        Debug.enableLogFile("testLog.log");

        String testOneFilePath = fileDirectoryToReadTestsFrom + "test1.log";
        GameClient client = new TestClient(testOneFilePath, filePathToWriteTestResultsTo);

        Game gameOne = new Game(PlayerID.PLAYER_ONE, new SimplePlayGenerator());

        Game gameTwo = new Game(PlayerID.PLAYER_TWO, new SimplePlayGenerator());

        GameMaster gameMaster = new GameMaster(client, gameOne, gameTwo);
        gameMaster.setTournamentPassword(TOURNAMENT_PASSWORD);
        gameMaster.setUsername(USERNAME);
        gameMaster.setPassword(PASSWORD);
        gameMaster.start();
    }

    @Test
    public void runTestTwo() throws Exception {
        Debug.enableAllDebugLevels();

        Debug.enableLogFile("testLog.log");

        String testOneFilePath = fileDirectoryToReadTestsFrom + "test2.log";
        GameClient client = new TestClient(testOneFilePath, filePathToWriteTestResultsTo);

        Game gameOne = new Game(PlayerID.PLAYER_ONE, new SimplePlayGenerator());

        Game gameTwo = new Game(PlayerID.PLAYER_TWO, new SimplePlayGenerator());

        GameMaster gameMaster = new GameMaster(client, gameOne, gameTwo);
        gameMaster.setTournamentPassword(TOURNAMENT_PASSWORD);
        gameMaster.setUsername(USERNAME);
        gameMaster.setPassword(PASSWORD);
        gameMaster.start();
    }
}

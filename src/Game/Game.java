package Game;

import GUI.GuiThread.GuiThread;
import Player.PlayerID;
import Steve.Steve;

public class Game {

    private GuiThread guiThread;

    public Game(PlayerID activePlayer) {
        guiThread = new GuiThread();
        Steve steve = new Steve();
        steve.playAs(activePlayer);
    }

    public void runWithGui() {
        guiThread.start();
    }


}

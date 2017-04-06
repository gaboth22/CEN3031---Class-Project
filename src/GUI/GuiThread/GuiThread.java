package GUI.GuiThread;

import GUI.HexLayoutGui;
import processing.core.PApplet;

public class GuiThread extends Thread {
    private volatile HexLayoutGui hexLayoutGui;
    private Thread consoleThread;

    public GuiThread() {
        hexLayoutGui = new HexLayoutGui();
        consoleThread = new GetInputFromConsoleThread(this);
    }

    public void run() {
        consoleThread.start();
        String[] args = new String[]{"GUI.HexLayoutGui"};
        PApplet.runSketch(args, hexLayoutGui);

        while(true) {
        }
    }

    public void updateGui(String update) {
        if(hexLayoutGui != null) {
            hexLayoutGui.updateGui(update);
        }
    }
}
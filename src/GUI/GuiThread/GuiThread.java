package GUI.GuiThread;

import GUI.HexLayoutGui;
import processing.core.PApplet;

public class GuiThread extends Thread {
    private HexLayoutGui hexLayoutGui;

    public GuiThread() {
        hexLayoutGui = new HexLayoutGui();
        Thread consoleThread = new GetInputFromConsoleThread(this);
        consoleThread.start();
    }

    public void run() {
        String[] args = new String[]{"GUI.HexLayoutGui"};
        PApplet.runSketch(args, hexLayoutGui);

        while(true) {
        }
    }

    public synchronized void updateGui(String update) {
        if(hexLayoutGui != null) {
            hexLayoutGui.updateGui(update);
        }
    }
}
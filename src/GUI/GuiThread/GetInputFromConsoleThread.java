package GUI.GuiThread;

import java.util.Scanner;

public class GetInputFromConsoleThread extends Thread {
    private Thread guiThread;
    private Scanner input;


    public GetInputFromConsoleThread(Thread guiThread) {
        this.guiThread = guiThread;
        input = new Scanner(System.in);
    }

    public void run() {
        while(true) {
            System.out.println("Enter Play: ");
            ((GuiThread) guiThread).updateGui(input.nextLine());
        }
    }


}

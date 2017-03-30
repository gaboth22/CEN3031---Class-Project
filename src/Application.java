import GUI.GuiThread.GuiThread;

public class Application {
    public static void main(String[] args) {
        GuiThread guiThread = new GuiThread();
        guiThread.start();
    }
}

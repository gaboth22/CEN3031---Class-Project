package GameTest;

public class ThreadToBeInterrupted extends Thread {

    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted()) {
            //just stall here until we are interrupted
        }
    }
}

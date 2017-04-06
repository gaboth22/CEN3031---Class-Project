package GameMaster.Game;

import java.util.concurrent.TimeUnit;

public class TimerThread extends  Thread {
    private long starTime;
    private long timeOut;
    private Thread toInterrupt;

    public TimerThread(long startTime, long timeOut, Thread toInterrupt) {
        this.starTime = startTime;
        this.timeOut = timeOut;
        this.toInterrupt = toInterrupt;
    }

    @Override
    public void run() {
        while(true) {

            long currentTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());

            if(currentTime- starTime >= timeOut) {
                toInterrupt.interrupt();
                break;
            }
        }
    }
}

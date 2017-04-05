package GameTest;

import GameMaster.Game.TimerThread;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.TimeUnit;

public class TimerThreadTest {

    ThreadToBeInterrupted toBeInterrupted;
    private long timeOut;
    private long startTime;

    @Before
    public void setUp() {
        toBeInterrupted = new ThreadToBeInterrupted();
        toBeInterrupted.start();
        timeOut = 500;
    }

    @Test
    public void threadShouldBeInterruptedAtTheRightTime() {

        startTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());

        TimerThread timerThread = new TimerThread(
                startTime,
                timeOut,
                toBeInterrupted);

        timerThread.start();

        while(toBeInterrupted.isAlive()) {}

        long currentTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());

        assertThreadRanForPlusOrMinusTimeOut(currentTime);
    }

    private void assertThreadRanForPlusOrMinusTimeOut(long currentTime) {
        Assert.assertTrue(currentTime - startTime >= timeOut - 50);
        Assert.assertTrue(currentTime - startTime <= timeOut + 50);
    }
}

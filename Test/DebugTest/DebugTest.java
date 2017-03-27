package DebugTest;

import Debug.*;
import org.junit.*;

import java.io.*;
import java.nio.charset.Charset;

public class DebugTest {
    private boolean check;
    File logFile;

    @Before
    public void initializeInstances() {
        check = false;
        logFile = new File("./src/Log/log.log");
    }

    @After
    public void cleanUp() {
        if(logFile.exists())
            logFile.delete();

        logFile = null;
    }

    @Test
    public void theLogFileShouldExists() {
        givenLogFileIsInitialized();
        check = whenTheFileIsCheckedForExistence();
        thenTheFileShouldExist(check);
    }

    private void givenLogFileIsInitialized() {
        Debug.enableLogFile("log.log");
    }

    private boolean whenTheFileIsCheckedForExistence() {
        logFile = new File("./src/Log/log.log");
        return logFile.exists();
    }

    private void thenTheFileShouldExist(boolean checkFile) {
        Assert.assertTrue(checkFile);
    }

    @Test
    public void logFileShouldNotExist() {
        check = whenTheFileIsCheckedForExistence();
        thenTheFileShouldNotExist(check);
    }

    public void thenTheFileShouldNotExist(boolean checkFile) {
        Assert.assertFalse(checkFile);
    }

    @Test
    public void warningLineShouldBeWrittenToFile() throws Exception {
        givenDebugLogFileIsEnabled();
        whenIPrintToDebugWithLevel("Hello, World!", DebugLevel.WARNING);
        thenTheFileShouldHaveTheLine("WARNING: Hello, World!");
    }

    private void givenDebugLogFileIsEnabled() {
        Debug.enableLogFile("log.log");
    }

    private void whenIPrintToDebugWithLevel(String message, DebugLevel level) {
        Debug.print(message, level);
    }

    private void thenTheFileShouldHaveTheLine(String line)  throws Exception{
        String readLine;
        InputStream fis = new FileInputStream("./src/Log/log.log");
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        readLine = br.readLine();
        Assert.assertEquals(line, readLine);

    }

    @Test
    public void infoLineShouldBeWrittenToFile() throws Exception {
        givenDebugLogFileIsEnabled();
        whenIPrintToDebugWithLevel("Hello, World!", DebugLevel.INFO);
        thenTheFileShouldHaveTheLine("INFO: Hello, World!");
    }

    @Test
    public void errorLineShouldBeWrittenToFile() throws Exception {
        givenDebugLogFileIsEnabled();
        whenIPrintToDebugWithLevel("Hello, World!", DebugLevel.ERROR);
        thenTheFileShouldHaveTheLine("ERROR: Hello, World!");
    }
}
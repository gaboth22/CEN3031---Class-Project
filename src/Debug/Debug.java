package Debug;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class Debug
{
    private static boolean Debug_Error;
    private static boolean Debug_Warning;
    private static boolean Debug_Info;
    private static boolean Debug_File;
    private static BufferedWriter writer;
    private static String innerFileName;

    private Debug() {
        this.Debug_Error = false;
        this.Debug_Warning = false;
        this.Debug_Info = false;
        this.Debug_File = false;
    }

    public static void enableLogFile(String fName) {
        try {
            String fileName = "./src/Log/" + fName;
            innerFileName = fileName;
            boolean appendToEndOfFile = true;
            writer = new BufferedWriter(new FileWriter(fileName, appendToEndOfFile));
            Debug_File = true;
        }
        catch(IOException e) {}
    }

    public static void disableLogFile() {
        Debug_File = false;
    }

    public static void enableDebugLevel(DebugLevel level) throws IllegalArgumentException {
        if(level == DebugLevel.ERROR)
            Debug_Error = true;

        else if(level == DebugLevel.WARNING)
            Debug_Warning = true;

        else if(level == DebugLevel.INFO)
            Debug_Info = true;

        else
            throw new IllegalArgumentException("Invalid debug level\n");
    }

    public static void disableDebugLevel(DebugLevel level) throws IllegalArgumentException {
        if(level == DebugLevel.ERROR)
            Debug_Error = false;

        else if(level == DebugLevel.WARNING)
            Debug_Warning = false;

        else if(level == DebugLevel.INFO)
            Debug_Info = false;

        else
            throw new IllegalArgumentException("Invalid debug level\n");
    }

    public static void enableAllDebugLevels() {
        for(DebugLevel level : DebugLevel.values()) {
            enableDebugLevel(level);
        }
    }

    public static void disableAllDebugLevels() {
        for(DebugLevel level : DebugLevel.values()) {
            disableDebugLevel(level);
        }
    }

    public static void print(String message, DebugLevel level) {
        System.out.print(System.nanoTime());
        if(level == DebugLevel.ERROR) {
            if(Debug_Error)
                System.out.println(level + ": " + message);
            if(Debug_File) {
                try {
                    writer = new BufferedWriter(new FileWriter(innerFileName, true));
                    writer.write(level + ": " + message + "\n");
                    writer.close();
                } catch(IOException e) {}
            }
        }

        else if(level == DebugLevel.WARNING) {
            if(Debug_Warning)
                System.out.println(level + ": " + message);
            if(Debug_File) {
                try {
                    writer = new BufferedWriter(new FileWriter(innerFileName, true));
                    writer.write(level + ": " + message + "\n");
                    writer.close();
                } catch(IOException e) {e.getMessage();}
            }
        }

        else if(level == DebugLevel.INFO) {
            if(Debug_Info)
                System.out.println(level + ": " + message);
            if(Debug_File) {
                try {
                    writer = new BufferedWriter(new FileWriter(innerFileName, true));
                    writer.write(level + ": " + message + "\n");
                    writer.close();
                } catch(IOException e) {}
            }
        }

        else
            throw new IllegalArgumentException("Invalid debug level\n");
    }
}
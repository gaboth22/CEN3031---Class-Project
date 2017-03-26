package GUI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Deserializer {
    public static ArrayList<Coordinate> getListOfCoordinates() {
        ArrayList<Coordinate> coordinates = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream("./src/GUI/data/coordinates");
            ObjectInputStream ois = new ObjectInputStream(fis);
            coordinates = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        catch(ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
        }

        return coordinates;
    }
}

package GUI;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class HexagonLayoutBuilder {
    private static ArrayList<Hexagon> listOfHexesOnDisplay;
    private static boolean listBuilt = false;

    public static List<Hexagon> getListOfHexagonsOnDisplay(PApplet parent) {
        buildHexList(parent);
        return listOfHexesOnDisplay;
    }

    private static void buildHexList(PApplet parent) {
        if(!listBuilt) {
            listOfHexesOnDisplay = new ArrayList<>();
            for(int i = 0; i < parent.height; i++) {
                for(int j = 0; j < parent.width; j++) {
                    if(j % 2 == 0) {
                        Hexagon hex = new Hexagon(j * 45, i * 50, 30);
                        hex.setParent(parent);
                        hex.setColor(Color.WHITE);
                        listOfHexesOnDisplay.add(hex);
                    }
                    else {
                        Hexagon hex = new Hexagon(j * 45, i * 50 - 25, 30);
                        hex.setParent(parent);
                        hex.setColor(Color.WHITE);
                        listOfHexesOnDisplay.add(hex);
                    }
                }
            }

            listBuilt = true;
        }
    }
}

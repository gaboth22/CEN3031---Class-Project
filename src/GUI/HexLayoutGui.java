package GUI;

import GamePieceMap.GamePiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.Scanner;

public class HexLayoutGui extends PApplet {

    private ArrayList<Coordinate> coordinates;
    private PImage background;
    private ArrayList<Tile> tilesOnDisplay;
    private ArrayList<GamePiece> piecesOnDisplay;
    private Scanner input;

    public static void main(String[] args) {
        PApplet.main("GUI.HexLayoutGui");
    }

    public void settings() {
        setSizeOfScreen();
    }

    private void setSizeOfScreen() {
        size(800, 600);
    }

    public void setup() {
        tilesOnDisplay = new ArrayList<>();
        piecesOnDisplay = new ArrayList<>();
        input = new Scanner(System.in);
        imageMode(CENTER);
        setFrameRate();
        background = loadImage("./src/GUI/data/hexes.jpeg");
        coordinates = new ArrayList<>();
        coordinates = Deserializer.getListOfCoordinates();

        getUserPlay();
    }

    private void setFrameRate() {
        frameRate(30);
    }

    public void draw() {
        doBackGroundImage();
        highLightMouseLocation();
        displayUpdatedBoard();
    }

    private void doBackGroundImage() {
        image(background, width/2, height/2, width, height);
    }

    private void getUserPlay() {
        System.out.println("Enter play: ");
        String play = input.nextLine();
        GameDataParser parser = new GameDataParser(play);
        BuildPhase buildPhase;
        TilePlacementPhase tilePlacementPhase;

        if(parser.getPlayType() == "piece") {
            try {
                buildPhase = parser.getBuildPhase();
            }
            catch (Exception e) {}
        }
        else {
            try {
                tilePlacementPhase = parser.getTilePlacementPhase();
                Tile toPlace = tilePlacementPhase.getTileToPlace();
                tilesOnDisplay.add(toPlace);
                System.out.println(toPlace.getArrayOfTerrainLocations());
                System.out.println(toPlace.getArrayOfTerrains());
            }
            catch (Exception e) {}
        }

    }

    private void displayUpdatedBoard() {
        for(Tile tile : tilesOnDisplay) {
            Location[] locs = tile.getArrayOfTerrainLocations();
            Terrain[] ters = tile.getArrayOfTerrains();
            for(int i = 0; i < locs.length; i++) {
                if(ters[i] == Terrain.VOLCANO) {
                    renderHexWithVolcanoAt(locs[i].getX(), locs[i].getY());
                }

                else if(ters[i] == Terrain.GRASSLANDS) {
                    renderHexWithGrasslandsAt(locs[i].getX(), locs[i].getY());
                }

                else if(ters[i] == Terrain.LAKE) {
                    renderHexWithLakeAt(locs[i].getX(), locs[i].getY());
                }
            }
        }
    }

    private float distance(Coordinate c1, Coordinate c2) {
        return dist(c1.x + 10, c1.y + 10, c2.x, c2.y);
    }

    private void renderBlackHexAt(Coordinate coordinate) {
        Hexagon hex = new Hexagon(coordinate.x, coordinate.y, 30);
        hex.setColor(Color.BLACK);
        hex.setParent(this);
        hex.render();
    }

    private void renderHexWithVolcanoAt(int x, int y) {
        Hexagon hex = new Hexagon(x, y, 30);
        hex.setColor(Color.RED);
        hex.setParent(this);
        hex.render();
    }

    private void renderHexWithGrasslandsAt(int x, int y) {
        Hexagon hex = new Hexagon(x, y, 30);
        hex.setColor(Color.GREEN);
        hex.setParent(this);
        hex.render();
    }

    private void renderHexWithLakeAt(int x, int y) {
        Hexagon hex = new Hexagon(x, y, 30);
        hex.setColor(Color.RED);
        hex.setParent(this);
        hex.render();
    }

    public void highLightMouseLocation() {
        for(Coordinate coordinate : coordinates) {
            Coordinate mouse = new Coordinate(mouseX, mouseY);
            if(distance(coordinate, mouse) <= 25) {
                System.out.println(coordinate);
                renderBlackHexAt(coordinate);
            }
        }
    }
}

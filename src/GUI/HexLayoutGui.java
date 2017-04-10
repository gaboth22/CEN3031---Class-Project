package GUI;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.List;
import processing.core.PFont;

import java.util.concurrent.ConcurrentLinkedQueue;

public class HexLayoutGui extends PApplet {

    public HexLayoutGui() {
        updates2 = new ConcurrentLinkedQueue<>();
    }

    private ArrayList<Coordinate> coordinates;
    private PImage background;
    private PFont font;
    private List<Tile> tilesOnDisplay;
    private List<GamePiece> piecesOnDisplay;
    private List<Hexagon> hexesOnDisplay;
    private HexMap hexMap;
    private boolean showTiles;
    private volatile ConcurrentLinkedQueue<String> updates2;

    public void updateGui(String update) {
        updates2.add(update);
    }

    public void clearGui() {
        hexesOnDisplay.clear();
    }

    public void settings() {
        setSizeOfScreen();
    }

    private void setSizeOfScreen() {
        size(800, 600);
    }

    public void setup() {
        showTiles = false;
        hexesOnDisplay = new ArrayList<>();
        tilesOnDisplay = new ArrayList<Tile>();
        piecesOnDisplay = new ArrayList<GamePiece>();
        hexMap = new HexMap();
        imageMode(CENTER);
        setFrameRate();
        background = loadImage("./src/GUI/data/hexes.jpeg");
        font = loadFont("./src/GUI/data/AndaleMono-48.vlw");
        textFont(font,10);
        coordinates = new ArrayList<>();
        coordinates = Deserializer.getListOfCoordinates();
        doBackGroundImage();
        writeCoordinatesOnMap();
    }

    private void writeCoordinatesOnMap() {
        for(Coordinate coordinate : coordinates) {
            Location location = hexMap.getLocationFromCoordinate(coordinate);
            if(location != null) {
                String loc = "(" + location.getX() + "," + location.getY() + ")";
                fill(color(0,0,0));
                textSize(10);
                text(loc, coordinate.x - 18, coordinate.y - 10);
            }
        }
    }

    private void setFrameRate() {
        frameRate(30);
    }

    public void draw() {
        getUserPlay();
        displayUpdatedBoard();
    }

    private void doBackGroundImage() {
        image(background, width/2, height/2, width, height);
    }

    public void mouseClicked() {
        showTiles = !showTiles;
    }

    private void getUserPlay() {

        if(updates2.size() > 0) {
            while(!updates2.isEmpty()) {

                GameDataParser parser = new GameDataParser(new String(updates2.remove()));
                BuildPhase buildPhase;
                TilePlacementPhase tilePlacementPhase;

                if (parser.getPlayType().equals("piece")) {
                    try {
                        buildPhase = parser.getBuildPhase();
                        PlayerID pid = buildPhase.getPlayerID();
                        TypeOfPiece piece = buildPhase.getTypeOfPieceToPlace();
                        Location loc = buildPhase.getLocationToPlacePieceOn();
                        String pieceString = "";
                        String pidString = "";
                        if (pid == PlayerID.PLAYER_TWO)
                            pidString = "P2";
                        else
                            pidString = "P1";

                        if (piece == TypeOfPiece.TIGER)
                            pieceString = pidString + "-" + "TI";
                        else if (piece == TypeOfPiece.TOTORO)
                            pieceString = pidString + "-" + "TO";
                        else
                            pieceString = pidString + "-" + "VI";

                        Coordinate coord = hexMap.getCoordinateFromLocation(loc);
                        for (Hexagon hex : hexesOnDisplay) {
                            if (hex.getCenterX() == coord.x && hex.getCenterY() == coord.y) {
                                hex.setPiece(pieceString);
                                break;
                            }
                        }
                    } catch (Exception e) {
                    }
                } else {
                    try {

                        tilePlacementPhase = parser.getTilePlacementPhase();
                        Tile toPlace = tilePlacementPhase.getTileToPlace();
                        Location[] l = toPlace.getArrayOfTerrainLocations();
                        Terrain[] t = toPlace.getArrayOfTerrains();

                        float r = random(0, 255);
                        float g = random(0, 255);
                        float b = random(0, 255);

                        for (Hexagon hex : hexesOnDisplay) {

                            for (int i = 0; i < l.length; i++) {
                                Coordinate c = hexMap.getCoordinateFromLocation(l[i]);
                                if (hex.getCenterX() == c.x && hex.getCenterY() == c.y) {
                                    hex.setTerrain(terrainToString(t[i]));
                                    int prevLevel = Integer.parseInt(hex.getLevel());
                                    hex.setLevel(Integer.toString(prevLevel + 1));
                                    hex.setColor(color(r, g, b, 100));
                                    hex.setPiece("");
                                }
                            }
                        }

                        for (int i = 0; i < t.length; i++) {
                            Hexagon hex = getHex(l[i], color(r, g, b, 100), t[i]);
                            if (!hexesOnDisplay.contains(hex))
                                hexesOnDisplay.add(hex);
                        }
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
                }

            }
        }
    }

    private String terrainToString(Terrain t) {
        if(t == Terrain.GRASSLANDS)
            return "G";
        else if(t == Terrain.JUNGLE)
            return "J";
        else if(t == Terrain.LAKE)
            return "L";
        else if(t == Terrain.ROCKY)
            return "R";
        else
            return"V";
    }

    private Hexagon getHex(Location loc, int color, Terrain t) {
        Coordinate c = hexMap.getCoordinateFromLocation(loc);
        Hexagon hex = new Hexagon(c.x, c.y, 30);
        hex.setParent(this);
        hex.setColor(color);

        if(t == Terrain.GRASSLANDS)
            hex.setTerrain("G");
        else if(t == Terrain.JUNGLE)
            hex.setTerrain("J");
        else if(t == Terrain.LAKE)
            hex.setTerrain("L");
        else if(t == Terrain.ROCKY)
            hex.setTerrain("R");
        else
            hex.setTerrain("V");

        hex.setLevel("1");

        return hex;
    }

    private void displayUpdatedBoard() {
        doBackGroundImage();
        writeCoordinatesOnMap();
        for(Hexagon hex : hexesOnDisplay) {
            hex.render();
        }
    }

    private float distance(Coordinate c1, Coordinate c2) {
        return dist(c1.x + 10, c1.y + 10, c2.x, c2.y);
    }

    private void renderBlackHexAt(Coordinate coordinate) {
        Hexagon hex = new Hexagon(coordinate.x, coordinate.y, 30);
        hex.setColor(color(0,0,0, 100));
        hex.setParent(this);
        hex.render();
    }

    private void renderHexWithVolcanoAt(Location loc, int color) {
        Coordinate toRender = hexMap.getCoordinateFromLocation(loc);
        Hexagon hex = new Hexagon(toRender.x, toRender.y, 30);
        hex.setColor(color(255, 0, 0, 100));
        hex.setParent(this);
        hex.render();
    }

    private void renderHexWithGrasslandsAt(Location loc, int color) {
        Coordinate toRender = hexMap.getCoordinateFromLocation(loc);
        Hexagon hex = new Hexagon(toRender.x, toRender.y, 30);
        hex.setColor(color(0, 255, 0, 100));
        hex.setParent(this);
        hex.render();
    }

    private void renderHexWithLakeAt(Location loc, int color) {
        Coordinate toRender = hexMap.getCoordinateFromLocation(loc);
        Hexagon hex = new Hexagon(toRender.x, toRender.y, 30);
        hex.setColor(color(0, 0, 255, 100));
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

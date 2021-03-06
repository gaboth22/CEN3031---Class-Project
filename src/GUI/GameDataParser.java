package GUI;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Terrain.Terrain.Terrain;
import Tile.Tile.FirstTileImpl;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;

import java.util.Arrays;

public class GameDataParser {
    private PlayerID pid;
    private String[] args;
    private String playType;

    public GameDataParser(String args) {
        this.args = args.split(" ");
        if(this.args[0].equals("1"))
            pid = PlayerID.PLAYER_ONE;
        else
            pid = PlayerID.PLAYER_TWO;

        playType = this.args[1];

//        System.out.println(pid);
//        System.out.println(playType);
    }

    public String getPlayType() {
        return playType;
    }

    public PlayerID getPlayerId() {
        return pid;
    }

    public BuildPhase getBuildPhase() throws Exception {
        GamePiece piece = null;

        if (args[2].equals("vi"))
            piece = new GamePiece(pid, TypeOfPiece.VILLAGER);
        else if (args[2].equals("to"))
            piece = new GamePiece(pid, TypeOfPiece.TOTORO);
        else
            piece = new GamePiece(pid, TypeOfPiece.TIGER);

        Location toPlaceOn = new Location(Integer.parseInt(args[3]), Integer.parseInt(args[4]));

//        System.out.println(piece);
//        System.out.println(toPlaceOn);

        BuildPhase buildPhase = new BuildPhase(piece, toPlaceOn);

        return buildPhase;
    }

    public TilePlacementPhase getTilePlacementPhase() throws Exception {

        if(args.length <= 11) {
            Terrain volc = getTerrainFromString(args[2]);
            Terrain left = getTerrainFromString(args[3]);
            Terrain right = getTerrainFromString(args[4]);
            Location vLoc = new Location(Integer.parseInt(args[5]), Integer.parseInt(args[6]));
            Location lLoc = new Location(Integer.parseInt(args[7]), Integer.parseInt(args[8]));
            Location rLoc = new Location(Integer.parseInt(args[9]), Integer.parseInt(args[10]));
            //System.out.println("" + volc + ", " + left + ", " + right);
            //System.out.println(vLoc + ", " + lLoc + ", " + rLoc);
            Terrain[] terrains = {volc, left, right};
            Location[] locations = {vLoc, lLoc, rLoc};

            return new TilePlacementPhase(pid, new TileImpl(Arrays.asList(terrains), Arrays.asList(locations)));
        }
        else {
            Terrain volc = getTerrainFromString(args[2]);
            Terrain t1 = getTerrainFromString(args[3]);
            Terrain t2 = getTerrainFromString(args[4]);
            Terrain t3 = getTerrainFromString(args[5]);
            Terrain t4 = getTerrainFromString(args[6]);

            Location vLoc = new Location(Integer.parseInt(args[7]), Integer.parseInt(args[8]));
            Location t1Loc = new Location(Integer.parseInt(args[9]), Integer.parseInt(args[10]));
            Location t2Loc = new Location(Integer.parseInt(args[11]), Integer.parseInt(args[12]));
            Location t3Loc = new Location(Integer.parseInt(args[13]), Integer.parseInt(args[14]));
            Location t4Loc = new Location(Integer.parseInt(args[15]), Integer.parseInt(args[16]));

            Terrain[] terrains = {volc, t1, t2, t3, t4};
            Location[] locations = {vLoc, t1Loc, t2Loc, t3Loc, t4Loc};

            Tile firstTile = new FirstTileImpl(Arrays.asList(terrains), Arrays.asList(locations));

            return new TilePlacementPhase(pid, firstTile);
        }
    }

    private Terrain getTerrainFromString(String t) {
        if(t.equals("j"))
            return Terrain.JUNGLE;
        else if(t.equals("l"))
            return Terrain.LAKE;
        else if(t.equals("g"))
            return Terrain.GRASSLANDS;
        else if(t.equals("r"))
            return Terrain.ROCKY;
        else
            return Terrain.VOLCANO;
    }
}

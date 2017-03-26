package GUI;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Terrain.Terrain.Terrain;
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

        System.out.println(pid);
        System.out.println(playType);
    }

    public String getPlayType() {
        return playType;
    }

    public PlayerID getPlayerId() {
        return pid;
    }

    public BuildPhase getBuildPhase() throws Exception {
        GamePiece piece = null;
        if(playType.equals("piece")) {
            if (args[2].equals("villager"))
                piece = new GamePiece(pid, TypeOfPiece.VILLAGER);
            else if (args[2].equals("totoro"))
                piece = new GamePiece(pid, TypeOfPiece.TOTORO);
            else
                piece = new GamePiece(pid, TypeOfPiece.TIGER);
        }

        BuildPhase buildPhase = new BuildPhase(piece,
                new Location(Integer.parseInt(args[3]),
                             Integer.parseInt(args[4])));
         return buildPhase;
    }

    public TilePlacementPhase getTilePlacementPhase() throws Exception {
        Terrain volc = getTerrainFromString(args[2]);
        Terrain left = getTerrainFromString(args[3]);
        Terrain right = getTerrainFromString(args[4]);
        Location vLoc = new Location(Integer.parseInt(args[5]), Integer.parseInt(args[8]));
        Location lLoc = new Location(Integer.parseInt(args[6]), Integer.parseInt(args[9]));
        Location rLoc = new Location(Integer.parseInt(args[7]), Integer.parseInt(args[10]));
        System.out.println("" + volc + ", " + left + ", " + right);
        System.out.println(vLoc + ", " + lLoc + ", " + rLoc);
        Terrain[] terrains = {volc, left, right};
        Location[] locations = {vLoc, lLoc, rLoc};

        return new TilePlacementPhase(pid, new TileImpl(Arrays.asList(terrains), Arrays.asList(locations)));
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

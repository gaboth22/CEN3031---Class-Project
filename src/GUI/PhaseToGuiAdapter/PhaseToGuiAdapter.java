package GUI.PhaseToGuiAdapter;

import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Terrain.Terrain.Terrain;

public class PhaseToGuiAdapter {

    public String adapt(Object phase) {

        if(phase instanceof TilePlacementPhase &&
            ((TilePlacementPhase) phase).getTileToPlace().getArrayOfTerrainLocations().length > 3) {

            TilePlacementPhase tpPhase = ((TilePlacementPhase) phase);

            Location[] locs = tpPhase.getTileToPlace().getArrayOfTerrainLocations();
            Terrain[] ters = tpPhase.getTileToPlace().getArrayOfTerrains();

            String pid = null;
            Character t1;
            Character t2;
            Character t3;
            Character t4;
            Character t5;
            int t1x;
            int t1y;
            int t2x;
            int t2y;
            int t3x;
            int t3y;
            int t4x;
            int t4y;
            int t5x;
            int t5y;

            if(tpPhase.getPlayerID() == PlayerID.PLAYER_ONE)
                pid = "1";
            else if(tpPhase.getPlayerID() == PlayerID.PLAYER_TWO)
                pid = "2";

            t1 = ters[0].name().toLowerCase().charAt(0);
            t2 = ters[1].name().toLowerCase().charAt(0);
            t3 = ters[2].name().toLowerCase().charAt(0);
            t4 = ters[3].name().toLowerCase().charAt(0);
            t5 = ters[4].name().toLowerCase().charAt(0);

            t1x = locs[0].getX();
            t1y = locs[0].getY();
            t2x = locs[1].getX();
            t2y = locs[1].getY();
            t3x = locs[2].getX();
            t3y = locs[2].getY();
            t4x = locs[3].getX();
            t4y = locs[3].getY();
            t5x = locs[4].getX();
            t5y = locs[4].getY();

            return pid+" tile "+t1+" "+t2+
                    " "+t3+" "+t4+" "+t5+
                    " "+t1x+" "+t1y+" "+
                    t2x+" "+t2y+" "+t3x+
                    " "+t3y+" "+t4x+" "+
                    t4y+" "+t5x+" "+t5y;
        }

        if(phase instanceof BuildPhase) {
            BuildPhase bPhase = (BuildPhase) phase;
            String pid = null;
            String pieceType = null;
            String locationX;
            String locationY;

            if(bPhase.getPlayerID() == PlayerID.PLAYER_ONE)
                pid = "1";
            else if(bPhase.getPlayerID() == PlayerID.PLAYER_TWO)
                pid = "2";

            if(bPhase.getTypeOfPieceToPlace() == TypeOfPiece.VILLAGER)
                pieceType = "vi";
            else if(bPhase.getTypeOfPieceToPlace() == TypeOfPiece.TOTORO)
                pieceType = "to";
            else if(bPhase.getTypeOfPieceToPlace() == TypeOfPiece.TIGER)
                pieceType = "ti";

            locationX = Integer.toString(((BuildPhase) phase).getLocationToPlacePieceOn().getX());
            locationY = Integer.toString(((BuildPhase) phase).getLocationToPlacePieceOn().getY());


            return pid+" piece "+pieceType+" "+locationX+" "+locationY;
        }

        else {
            TilePlacementPhase tpPhase = (TilePlacementPhase) phase;
            Location[] locs = tpPhase.getTileToPlace().getArrayOfTerrainLocations();
            Terrain[] ters = tpPhase.getTileToPlace().getArrayOfTerrains();

            String pid = null;
            Character t1;
            Character t2;
            Character t3;
            int t1x;
            int t1y;
            int t2x;
            int t2y;
            int t3x;
            int t3y;

            if(tpPhase.getPlayerID() == PlayerID.PLAYER_ONE)
                pid = "1";
            else if(tpPhase.getPlayerID() == PlayerID.PLAYER_TWO)
                pid = "2";

            t1 = ters[0].name().toLowerCase().charAt(0);
            t2 = ters[1].name().toLowerCase().charAt(0);
            t3 = ters[2].name().toLowerCase().charAt(0);

            t1x = locs[0].getX();
            t1y = locs[0].getY();
            t2x = locs[1].getX();
            t2y = locs[1].getY();
            t3x = locs[2].getX();
            t3y = locs[2].getY();

            return pid+" tile "+t1+" "+t2+" "+t3+" "+t1x+" "+t1y+" "+t2x+" "+t2y+" "+t3x+" "+t3y;
        }
    }
}

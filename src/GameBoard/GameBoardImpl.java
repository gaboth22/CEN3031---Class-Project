package GameBoard;

import GamePieceMap.*;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildPhaseException;
import Play.Rule.PiecePlacementRules.*;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import GamePieceMap.GamePieceMap;
import Location.Location;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Play.Rule.TilePlacementRules.*;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementPhaseException;
import TileMap.*;
import java.util.Map;

public class GameBoardImpl implements GameBoard {
    private GamePieceMap gamePieceMap;
    private TileMap tileMap;
    private int turnNumber;
    private final int FIRST_TURN = 1;

    public GameBoardImpl() {
        this.gamePieceMap = new GamePieceMap();
        this.tileMap = new HexagonMap();
        this.turnNumber = FIRST_TURN;
    }

    @Override
    public void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception {

        if (attemptFirstTilePlacement(tilePlacementPhase)) {
            tileMap.insertTile(tilePlacementPhase.getTileToPlace());
            incrementTurnNumber();
            return;
        }

        else if(attemptSimpleTilePlacement(tilePlacementPhase)) {
            tileMap.insertTile(tilePlacementPhase.getTileToPlace());
            incrementTurnNumber();
            return;
        }

        else if(attemptNuke(tilePlacementPhase)){
            //TODO: Needs to update removed villagers from GamePieceMap
            //TODO: Needs to update TileMap with the newly inserted tile
            return;
        }

        else {
            throw new TilePlacementPhaseException();
        }
    }

    private boolean attemptFirstTilePlacement(TilePlacementPhase placementPhase) {
        try {
            if(turnNumber != FIRST_TURN)
                throw new InvalidTilePlacementRuleException();
            HexesBelowShouldBeAtLevelZeroRule.applyRule(tileMap, placementPhase.getTileToPlace());
            FirstTileMustBePlacedWithVolcanoAtCenterOfBoard.applyRule(tileMap, placementPhase.getTileToPlace());
        }
        catch( InvalidTilePlacementRuleException e) {
            System.out.println(e.getStackTrace());
            return false;
        }

        return true;
    }

    private boolean attemptFirstTilePlacement(TilePlacementPhase placementPhase) {
        try {
            if(turnNumber != FIRST_TURN)
                throw new InvalidTilePlacementRuleException();
            HexesBelowShouldBeAtLevelZeroRule.applyRule(tileMap, placementPhase.getTileToPlace());
            FirstTileMustBePlacedWithVolcanoAtCenterOfBoard.applyRule(tileMap, placementPhase.getTileToPlace());
        }
        catch( InvalidTilePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }

        return true;
    }

    private boolean attemptSimpleTilePlacement(TilePlacementPhase placementPhase) {
        try {
            HexesBelowShouldBeAtLevelZeroRule.applyRule(tileMap, placementPhase.getTileToPlace());
            TileMustTouchOneEdgeRule.applyRule(tileMap, placementPhase.getTileToPlace());
        }
        catch(InvalidTilePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }

        return true;
    }
          
    private boolean attemptNuke(TilePlacementPhase placementPhase) {
        try {
            VolcanoMustBeOnTopOfVolcanoRule.applyRule(tileMap, placementPhase.getTileToPlace());
            HexesBelowTileShouldBeSameLevelRule.applyRule(tileMap, placementPhase.getTileToPlace());
            HexesBelowTileShouldBelongToTwoOrMoreTiles.applyRule(tileMap, placementPhase.getTileToPlace());
            // TODO: this rule is not implemented yet, but we NEED it here -- rule is CannotWipeOutSettlementRule
            // TODO: CannotWipeOutSettlementRule.applyRule()
        }
        catch(InvalidTilePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }

        return true;
    }

    private void incrementTurnNumber() {
        this.turnNumber++;
    }

    @Override
    public int getCurrentTurn() {
        return turnNumber;
    }

    @Override
    public Map<Location, Hexagon> getGameBoardHexagons() {
        return tileMap.getAllHexagons();
    }

//     @Override
//     public boolean hasTileAt(Location[] locationsInTile) {
//         for(int i = 0; i < locationsInTile.length; i++) {
//             if(!tileMap.hasHexagonAt(locationsInTile[i]))
//                 return false;
//         }

//         return true;
//     }

//     @Override
//     public void doBuildPhase(BuildPhase buildPhase) throws Exception {

//         if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.VILLAGER) {

//             if(attemptSettlementExpansion(buildPhase)) {
//                 gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(),
//                                             buildPhase.getGamePiece());
//             }

//             else if(attemptSettlementFoundation(buildPhase)) {
//                 gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(),
//                                             buildPhase.getGamePiece());
//             }

//             else {
//                 throw new BuildPhaseException();
//             }
//         }

//         else if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TIGER ||
//                 buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TOTORO) {
//             if(attemptSpecialConstruction(buildPhase)) {
//                 gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(),
//                                             buildPhase.getGamePiece());
//             }
//             else {
//                 throw new BuildPhaseException();
//             }
//         }
//     }

//     private boolean attemptSettlementExpansion(BuildPhase buildPhase) {
//         try {
//             GamePieceCannotBePlacedOnVolcanoRule.applyRule(tileMap,
//                                                             buildPhase.getGamePiece(),
//                                                             buildPhase.getLocationToPlacePieceOn());
//             HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());
//             HexMustBeNextToPlayerSettlementRule.applyRule(gamePieceMap,
//                                                             buildPhase.getLocationToPlacePieceOn(),
//                                                             buildPhase.getPlayerID());
//         }
//         catch(InvalidPiecePlacementRuleException e) {
//             System.out.println(e.getClass());
//             return false;
//         }

//         return true;
//     }

//     private boolean attemptSettlementFoundation(BuildPhase buildPhase) {
//         try {
//             GamePieceCannotBePlacedOnVolcanoRule.applyRule(tileMap,
//                                                             buildPhase.getGamePiece(),
//                                                             buildPhase.getLocationToPlacePieceOn());
//             HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());

//         }
//         catch(InvalidPiecePlacementRuleException e) {
//             System.out.println(e.getClass());
//             return false;
//         }

//         return true;
//     }

//     private boolean attemptSpecialConstruction(BuildPhase buildPhase) {
//         try {
//             GamePieceCannotBePlacedOnVolcanoRule.applyRule(tileMap,
//                                                             buildPhase.getGamePiece(),
//                                                             buildPhase.getLocationToPlacePieceOn());

//             HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());

//             HexMustBeNextToPlayerSettlementRule.applyRule(gamePieceMap,
//                                                             buildPhase.getLocationToPlacePieceOn(),
//                                                             buildPhase.getPlayerID());

//             if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TIGER) {
//                 HexHeightMustBeThreeOrHigherRule.applyRule(tileMap, buildPhase.getLocationToPlacePieceOn());
//             }

//             else if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TOTORO) {
//                 SettlementSizeMustBeFiveOrGreaterRule.applyRule(gamePieceMap,
//                                                                 buildPhase.getLocationToPlacePieceOn(),
//                                                                 buildPhase.getPlayerID());
//             }

//             SettlementMustNotAlreadyHaveSpecialPieceRule.applyRule(gamePieceMap,
//                                                                 buildPhase.getLocationToPlacePieceOn(),
//                                                                 buildPhase.getPlayerID(),
//                                                                 buildPhase.getTypeOfPieceToPlace());
//         }
//         catch (InvalidPiecePlacementRuleException e) {
//             System.out.println(e.getClass());
//             return false;
//         }

    @Override
    public Map<Location, Hexagon> getGameBoardHexagons() {
        return tileMap.getAllHexagons();
    }

    @Override
    public boolean hasTileAt(Location[] locationsInTile) {
        for(int i = 0; i < locationsInTile.length; i++) {
            if(!tileMap.hasHexagonAt(locationsInTile[i]))
                return false;
        }
      
        return true;
    }

    @Override
    public void doBuildPhase(BuildPhase buildPhase) throws Exception {

        if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.VILLAGER) {

            if(attemptSettlementExpansion(buildPhase)) {
                gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(),
                                            buildPhase.getGamePiece());
            }

            else if(attemptSettlementFoundation(buildPhase)) {
                gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(),
                                            buildPhase.getGamePiece());
            }

            else {
                throw new BuildPhaseException();
            }
        }

        else if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TIGER ||
                buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TOTORO) {
            if(attemptSpecialConstruction(buildPhase)) {
                gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(),
                                            buildPhase.getGamePiece());
            }
            else {
                throw new BuildPhaseException();
            }
        }
    }

    private boolean attemptSettlementExpansion(BuildPhase buildPhase) {
        try {
            GamePieceCannotBePlacedOnVolcanoRule.applyRule(tileMap,
                                                            buildPhase.getGamePiece(),
                                                            buildPhase.getLocationToPlacePieceOn());
            HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());
            HexMustBeNextToPlayerSettlementRule.applyRule(gamePieceMap,
                                                            buildPhase.getLocationToPlacePieceOn(),
                                                            buildPhase.getPlayerID());
        }
        catch(InvalidPiecePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }

        return true;
    }

    private boolean attemptSettlementFoundation(BuildPhase buildPhase) {
        try {
            GamePieceCannotBePlacedOnVolcanoRule.applyRule(tileMap,
                                                            buildPhase.getGamePiece(),
                                                            buildPhase.getLocationToPlacePieceOn());
            HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());

        }
        catch(InvalidPiecePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }

        return true;
    }

    private boolean attemptSpecialConstruction(BuildPhase buildPhase) {
        try {
            GamePieceCannotBePlacedOnVolcanoRule.applyRule(tileMap,
                                                            buildPhase.getGamePiece(),
                                                            buildPhase.getLocationToPlacePieceOn());

            HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());

            HexMustBeNextToPlayerSettlementRule.applyRule(gamePieceMap,
                                                            buildPhase.getLocationToPlacePieceOn(),
                                                            buildPhase.getPlayerID());

            if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TIGER) {
                HexHeightMustBeThreeOrHigherRule.applyRule(tileMap, buildPhase.getLocationToPlacePieceOn());
            }

            else if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TOTORO) {
                SettlementSizeMustBeFiveOrGreaterRule.applyRule(gamePieceMap,
                                                                buildPhase.getLocationToPlacePieceOn(),
                                                                buildPhase.getPlayerID());
            }

            SettlementMustNotAlreadyHaveSpecialPieceRule.applyRule(gamePieceMap,
                                                                buildPhase.getLocationToPlacePieceOn(),
                                                                buildPhase.getPlayerID(),
                                                                buildPhase.getTypeOfPieceToPlace());
        }
        catch (InvalidPiecePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }
      
        return true;
    }

}

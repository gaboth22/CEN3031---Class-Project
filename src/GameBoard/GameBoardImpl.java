package GameBoard;

import GamePieceMap.*;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.BuildPhase.BuildPhase;
import GamePieceMap.GamePieceMap;
import Play.BuildPhase.BuildPhaseException;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementPhaseException;
import Play.TilePlacementPhase.TilePlacementType;
import Player.*;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;
import Terrain.Terrain.Terrain;
import Tile.Tile.FirstTileImpl;
import Tile.Tile.Tile;
import TileMap.*;
import java.util.*;

public class GameBoardImpl implements GameBoard {
    private static final int TOTORO_SCORE = 200;
    private static final int TIGER_SCORE = 75;

    private GamePieceMap gamePieceMap;
    private TileMap tileMap;
    private int turnNumber;
    private final int FIRST_TURN = 1;

    private TilePlacementHelper tilePlacementHelper;
    private NukeTileHelper nukeTileHelper;
    private BuildPhaseHelper buildPhaseHelper;

    private Player playerOne;
    private Player playerTwo;

    private Tile firstTile;

    public GameBoardImpl() {
        this.gamePieceMap = new GamePieceMap();
        this.tileMap = new HexagonMap();
        this.turnNumber = FIRST_TURN;
        this.tilePlacementHelper = new TilePlacementHelper();
        this.nukeTileHelper = new NukeTileHelper();
        this.buildPhaseHelper = new BuildPhaseHelper();
        this.playerOne = new Player(PlayerID.PLAYER_ONE);
        this.playerTwo = new Player(PlayerID.PLAYER_TWO);
        insertFirstTile();
    }

    private void insertFirstTile(){
        Location[] firstTileLocations = new Location[] {
                new Location(0, 0),
                new Location(-1 , 1),
                new Location(1, 0),
                new Location(1, -1),
                new Location(-1, 0)
        };

        Terrain[] firstTileTerrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.LAKE,
                Terrain.GRASSLANDS,
                Terrain.ROCKY,
                Terrain.JUNGLE
        };

        firstTile = new FirstTileImpl(Arrays.asList(firstTileTerrains), Arrays.asList(firstTileLocations));

        try {
            tileMap.insertTile(firstTile);
        } catch(Exception e) {}
    }

    public Tile getFirstTile() {
        return firstTile;
    }

    @Override
    public void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception {
        if (tilePlacementPhase.getTilePlacementType() == TilePlacementType.FIRST_PLACEMENT) {
            if (tilePlacementHelper.attemptFirstTilePlacement(
                    tilePlacementPhase,
                    tileMap)) {

                tilePlacementHelper.insertTile(tilePlacementPhase, tileMap);
            }
            else throw new TilePlacementPhaseException("First placement failed");
        }
        else if (tilePlacementPhase.getTilePlacementType() == TilePlacementType.SIMPLE_PLACEMENT) {
            if (tilePlacementHelper.attemptSimpleTilePlacement(
                    tilePlacementPhase,
                    tileMap)) {

                tilePlacementHelper.insertTile(tilePlacementPhase, tileMap);
            }
            else throw new TilePlacementPhaseException("Simple placement failed");
        }
        else if (tilePlacementPhase.getTilePlacementType() == TilePlacementType.NUKE) {
            if (nukeTileHelper.attemptNuke(
                    tilePlacementPhase,
                    tileMap,
                    gamePieceMap)) {

                nukeTileHelper.updateTileMapWithNewlyInsertedTile(tilePlacementPhase, tileMap);
                nukeTileHelper.removeNukedVillagersFromGamePieceMap(tilePlacementPhase, gamePieceMap);
                settlementListUpdateForTilePhase(tilePlacementPhase);
            }
            else throw new TilePlacementPhaseException("Nuking failed");
        }
    }

    @Override
    public void doBuildPhase(BuildPhase buildPhase) throws Exception {

        Player activePlayer;
        if(buildPhase.getPlayerID() == PlayerID.PLAYER_ONE)
            activePlayer = playerOne;
        else
            activePlayer = playerTwo;

        if(buildPhase.getBuildType() == BuildType.FOUND){

            if(buildPhaseHelper.attemptSettlementFoundation(
                    buildPhase,
                    tileMap,
                    gamePieceMap,
                    activePlayer)){

                buildPhaseHelper.insertVillager(buildPhase, gamePieceMap, tileMap);
                updateScoreWhenVillagerPlaced(buildPhase.getPlayerID());
                villagerPieceAmountDecrement(buildPhase.getPlayerID());
                settlementListUpdateForBuildPhase(buildPhase);
                incrementTurnNumber();

            }
            else throw new BuildPhaseException("Settlement foundation failed");
        }
        else if(buildPhase.getBuildType() == BuildType.EXPAND) {
            if(buildPhaseHelper.attemptSettlementExpansion(
                    buildPhase,
                    tileMap,
                    gamePieceMap,
                    activePlayer)){

                buildPhaseHelper.expandSettlement(buildPhase, tileMap, gamePieceMap);
                updateScoreWhenVillagerPlaced(buildPhase.getPlayerID());
                settlementListUpdateForBuildPhase(buildPhase);
                incrementTurnNumber();
            }
            else throw new BuildPhaseException("Settlement expansion failed");
        }
        else if(buildPhase.getBuildType() == BuildType.PLACE_TOTORO) {
            if(buildPhaseHelper.attemptTotoroPlacement(
                    buildPhase,
                    tileMap,
                    gamePieceMap,
                    activePlayer)){

                buildPhaseHelper.insertSpecialPiece(buildPhase, gamePieceMap);
                updateScoreWhenTigerOrTotoroPlaced(buildPhase.getPlayerID(), buildPhase.getTypeOfPieceToPlace());
                specialPieceAmountDecrement(buildPhase.getPlayerID(), buildPhase.getTypeOfPieceToPlace());
                settlementListUpdateForBuildPhase(buildPhase);
                incrementTurnNumber();

            }
            else throw new BuildPhaseException("Totoro placement failed");
        }
        else if(buildPhase.getBuildType() == BuildType.PLACE_TIGER){
            if(buildPhaseHelper.attemptTigerPlacement(buildPhase, tileMap, gamePieceMap, activePlayer)) {

                buildPhaseHelper.insertSpecialPiece(buildPhase, gamePieceMap);
                updateScoreWhenTigerOrTotoroPlaced(buildPhase.getPlayerID(), buildPhase.getTypeOfPieceToPlace());
                specialPieceAmountDecrement(buildPhase.getPlayerID(), buildPhase.getTypeOfPieceToPlace());
                settlementListUpdateForBuildPhase(buildPhase);
                incrementTurnNumber();
            }
            else throw new BuildPhaseException("Tiger placement failed");
        }
    }

    private void updateScoreWhenVillagerPlaced(PlayerID playerID){
        if(playerID == PlayerID.PLAYER_ONE){
            playerOne.addPoints(buildPhaseHelper.getLastPlayScoreForVillagers());
        }
        else{
            playerTwo.addPoints(buildPhaseHelper.getLastPlayScoreForVillagers());
        }
        buildPhaseHelper.setLastPlayVillagerScoreToZero();
    }

    private void updateScoreWhenTigerOrTotoroPlaced(PlayerID playerID, TypeOfPiece typeOfPiece){
        if(playerID == PlayerID.PLAYER_ONE){
            if(typeOfPiece == TypeOfPiece.TIGER){
                playerOne.addPoints(TIGER_SCORE);
            }
            else{
                playerOne.addPoints(TOTORO_SCORE);
            }
        }
        else{
            if(typeOfPiece == TypeOfPiece.TIGER){
                playerTwo.addPoints(TIGER_SCORE);
            }
            else{
                playerTwo.addPoints(TOTORO_SCORE);
            }
        }
    }

    private void villagerPieceAmountDecrement(PlayerID playerID){
        if(playerID == playerOne.getID()){
            playerOne.decrementVillagerCount(buildPhaseHelper.getLastPlayVillagersUsed());
        }
        else{
            playerTwo.decrementVillagerCount(buildPhaseHelper.getLastPlayVillagersUsed());
        }
        buildPhaseHelper.setLastPlayVillagersUsedToZero();
    }

    private void specialPieceAmountDecrement(PlayerID playerID, TypeOfPiece typeOfPiece){
        if(playerID == PlayerID.PLAYER_ONE){
            if(typeOfPiece == TypeOfPiece.TIGER){
                playerOne.decrementTigerCount();
            }
            else{
                playerOne.decrementTotoroCount();
            }
        }
        else{
            if(typeOfPiece == TypeOfPiece.TIGER){
                playerTwo.decrementTigerCount();
            }
            else{
                playerTwo.decrementTotoroCount();
            }
        }
    }

    private void settlementListUpdateForTilePhase(TilePlacementPhase tilePlacementPhase){
        Map<Location, Hexagon> placedHexes = tileMap.getAllHexagons();
        Set<Location> locationsOfPlacedHexes =  placedHexes.keySet();

        List<Settlement> activePlayerSettlements = new ArrayList<>();
        List<Settlement> otherPlayerSettlements = new ArrayList<>();

        PlayerID activePlayer = tilePlacementPhase.getPlayerID();

        for(Location location : locationsOfPlacedHexes) {
            Settlement settlement = SettlementCreator.getSettlementAt(gamePieceMap, location);

            if (settlement.getSettlementOwner() == activePlayer &&
                    !activePlayerSettlements.contains(settlement))
                activePlayerSettlements.add(settlement);

            if (settlement.getSettlementOwner() != activePlayer &&
                    !otherPlayerSettlements.contains(settlement))
                otherPlayerSettlements.add(settlement);
        }

        if(activePlayer == PlayerID.PLAYER_ONE)
            playerOne.setListOfSettlements(activePlayerSettlements);
        else
            playerOne.setListOfSettlements(otherPlayerSettlements);

        if(activePlayer == PlayerID.PLAYER_TWO)
            playerTwo.setListOfSettlements(activePlayerSettlements);
        else
            playerTwo.setListOfSettlements(otherPlayerSettlements);
    }

    private void settlementListUpdateForBuildPhase(BuildPhase buildPhase){
        Map<Location, Hexagon> placedHexes = tileMap.getAllHexagons();
        Set<Location> locationsOfPlacedHexes =  placedHexes.keySet();

        List<Settlement> activePlayerSettlements = new ArrayList<>();

        for(Location loc : locationsOfPlacedHexes) {
            Settlement settlement = SettlementCreator.getSettlementAt(gamePieceMap, loc);

            if( settlement.getSettlementOwner() == buildPhase.getPlayerID() &&
                    !activePlayerSettlements.contains(settlement))
                activePlayerSettlements.add(settlement);
        }

        if(buildPhase.getPlayerID() == PlayerID.PLAYER_ONE)
            playerOne.setListOfSettlements(activePlayerSettlements);
        else
            playerTwo.setListOfSettlements(activePlayerSettlements);
    }

    private void incrementTurnNumber() {
        this.turnNumber++;
    }

    @Override
    public int getCurrentTurn() {
        return turnNumber;
    }

    @Override
    public Map<Location, Hexagon> getPlacedHexagons() {
        return tileMap.getAllHexagons();
    }

    @Override
    public Player getPlayer(PlayerID playerID){
        if(playerID == PlayerID.PLAYER_ONE) {
            return new Player(playerOne);
        }
        else {
            return new Player(playerTwo);
        }
    }

    @Override
    public int getPlayerOneScore(){
        return playerOne.getScore();
    }

    @Override
    public int getPlayerTwoScore(){
        return playerTwo.getScore();
    }

    @Override
    public List<Settlement> getPlayerOneSettlements(){
        return playerOne.getListOfSettlements();
    }

    @Override
    public List<Settlement> getPlayerTwoSettlements(){
        return playerTwo.getListOfSettlements();
    }

    @Override
    public List<Location> getPlaceableLocations(){
        List<Location> placeableLocations = new ArrayList<Location>();

        Map<Location, Hexagon> placedHexes = tileMap.getAllHexagons();
        Set<Location> locationsOfPlacedHexes =  placedHexes.keySet();

        for(Location loc : locationsOfPlacedHexes){
            Location[] adjacents = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(loc);
            for(int i = 0; i < adjacents.length; i++){
                if(!tileMap.hasHexagonAt(adjacents[i])){
                    placeableLocations.add(adjacents[i]);
                }
            }
        }
        return placeableLocations;
    }

    @Override
    public GamePieceMap getGamePieceMap(){
        return new GamePieceMap(gamePieceMap);
    }
}

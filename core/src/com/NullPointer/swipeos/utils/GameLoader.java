package com.NullPointer.swipeos.utils;

import com.NullPointer.swipeos.Game;
import com.NullPointer.swipeos.Scripts.GameObjectsScripts.PlayerScript;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


/**
 * Created by Khaustov on 05.12.15.
 */
public class GameLoader {
    static Game game;
    static PlayerScript playerScript;
    static LevelLoader levelLoader;
    private final float startLevelYCameraCoord = 320f;
    private final float StartLevelPlayerPosition = 45f;
    private boolean paused;
    private String sceneToLoad = "";
    public boolean isNeedToLoadScene;

    int currentLevel = 1;

    static float levelXStartCoordinate = 0f;

    public GameLoader(Game game){
        this.game = game;
    }

    public void initialize(){
        game.mainViewPort = new ExtendViewport(360,640);
        game.mainSceneLoader = new SceneLoader();
        game.mainSceneLoader.loadScene("MainScene", game.mainViewPort);
        game.mainItemWrapper = new ItemWrapper(game.mainSceneLoader.getRoot());
        //// TODO: добавить загрузку последнего уровня для игрока
        levelLoader = new LevelLoader(game.mainItemWrapper, this);
        levelLoader.loadMainScreen();

        game.setCameraCoords(180f, 320f);
        game.onLoadListener.onLoad();
    }

    public void initializeGameStage_1_10(){
        game.mainSceneLoader.loadScene("levelPack1", game.mainViewPort, this);
        game.mainItemWrapper = new ItemWrapper(game.mainSceneLoader.getRoot());
        //// TODO: добавить загрузку последнего уровня для игрока
        levelLoader = new LevelLoader(game.mainItemWrapper, this);
        levelLoader.loadLevel(currentLevel);
        playerScript = new PlayerScript(levelLoader, this);
        game.mainItemWrapper.getChild("player").addScript(playerScript);
        game.setCameraCoords(getLevelXStartCoordinate(), startLevelYCameraCoord);
    }


    public void nextLevel() {
        ++currentLevel;
        levelLoader.loadLevel(currentLevel);
        playerScript.setPlayerСoordinates(getLevelXStartCoordinate(), StartLevelPlayerPosition);
        game.setCameraCoords(getLevelXStartCoordinate(), startLevelYCameraCoord);
    }

    public void showLevelCompleteWindow(){
        levelLoader.showLevelCompleteWindow();
    }

    public float getPlayerY(){
        return playerScript.playerTransformComponent.y;
    }

    public float getPlayerX(){
        return playerScript.playerTransformComponent.x;
    }


    public float getCameraStopY(){
        return levelLoader.getLevelStopY();
    }

    public void SetCameraCoords(float x, float y){
        game.setCameraCoords(x, y);
    }

    public float getLevelXStartCoordinate() {
        return levelXStartCoordinate;
    }

    public void setLevelXStartCoordinate(float levelXStartCoordinate) {
        this.levelXStartCoordinate = levelXStartCoordinate;
    }
    public Vector3 getCameraCoordinates(){
        return game.mainViewPort.getCamera().position;
    }

    public OrthographicCamera getMainCamera(){
        return (OrthographicCamera) game.mainViewPort.getCamera();
    }


    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void removeAllEntities(){
        game.mainSceneLoader.removeAllEntities();
    }

    public void setSceneToLoad(String sceneToLoad){
        this.sceneToLoad = sceneToLoad;
        this.isNeedToLoadScene = true;
    }

    public void loadCurrentScene(){
        this.isNeedToLoadScene = false;
        initializeGameStage_1_10();
    }
    public static Game getGame(){
        return game;
    }
}

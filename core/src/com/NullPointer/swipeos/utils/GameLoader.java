package com.NullPointer.swipeos.utils;

import com.NullPointer.swipeos.Game;
import com.NullPointer.swipeos.Scripts.PlayerScript;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

/**
 * Created by Khaustov on 05.12.15.
 */
public class GameLoader {
    static Game game;
    static PlayerScript playerScript;
    static LevelLoader levelLoader;
    private final float startLevelYCameraCoord = 320f;
    private final float StartLevelPlayerPosition = 45f;

    int currentLevel = 1;

    static float levelXStartCoordinate = 0f;

    public GameLoader(Game game){
        this.game = game;
    }

    public void initialize(){
        game.mainViewPort = new FitViewport(360,640);
        game.mainSceneLoader = new SceneLoader();
        game.mainSceneLoader.loadScene("levelPack1", game.mainViewPort);
        game.mainItemWrapper = new ItemWrapper(game.mainSceneLoader.getRoot());
        //// TODO: добавить загрузку последнего уровня для игрока
        levelLoader = new LevelLoader(game.mainItemWrapper, this);
        levelLoader.loadLevel(currentLevel);
        playerScript = new PlayerScript(levelLoader, this);
        game.mainItemWrapper.getChild("player").addScript(playerScript);
    }


    public void nextLevel() {
        ++currentLevel;
        levelLoader.loadLevel(currentLevel);
        playerScript.setPlayerСoordinates(getLevelXStartCoordinate(), StartLevelPlayerPosition);
        game.setCameraCoords(getLevelXStartCoordinate(), startLevelYCameraCoord);
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
}

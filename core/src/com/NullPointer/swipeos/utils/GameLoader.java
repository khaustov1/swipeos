package com.NullPointer.swipeos.utils;

import com.NullPointer.swipeos.Game;
import com.NullPointer.swipeos.Scripts.PlayerScript;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

/**
 * Created by Khaustov on 05.12.15.
 */
public class GameLoader {
    static Game game;
    static PlayerScript playerScript;
    static LevelLoader levelLoader;

    int currentLevel = 1;

    public GameLoader(Game game){
        this.game = game;
    }

    public void initialize(){
        game.mainViewPort = new ExtendViewport(360,640);
        game.mainSceneLoader = new SceneLoader();
        game.mainSceneLoader.loadScene("MainScene", game.mainViewPort);
        game.mainItemWrapper = new ItemWrapper(game.mainSceneLoader.getRoot());
        //// TODO: добавить загрузку последнего уровня для игрока
        levelLoader = new LevelLoader(game.mainItemWrapper);
        levelLoader.loadLevel(currentLevel);
        playerScript = new PlayerScript(levelLoader, this);
        game.mainItemWrapper.getChild("player").addScript(playerScript);
    }


    public void nextLevel(){
        ++currentLevel;
        switch (currentLevel){
            case 1:
                break;
            case 2:
                levelLoader.loadLevel(2);
                playerScript.setPlayerСoordinates(10.33f,1170);
                game.setCameraCoords(130f, getLevelY());
                break;
            default:
                break;
        }
    }

    public float getPlayerY(){
        return playerScript.playerTransformComponent.y;
    }

    public float getPlayerX(){
        return playerScript.playerTransformComponent.x;
    }

    public float getLevelY(){
        switch (currentLevel){
            case 1:
                return 180f;
            case 2:
                return 1155f;
            default:
                return 0f;
        }
    }

    public void SetCameraCoords(float x, float y){
        game.setCameraCoords(x, y);
    }

}

package com.NullPointer.swipeos.utils;

import com.NullPointer.swipeos.Game;
import com.NullPointer.swipeos.Scripts.PlayerScript;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khaustov on 05.12.15.
 */
public class GameLoader {
    Game game;
    PlayerScript playerScript;

    int currentLevel = 1;

    public GameLoader(Game game){
        this.game = game;
    }

    public void initialize(){
        game.mainViewPort = new FitViewport(360,640);
        game.mainSceneLoader = new SceneLoader();
        game.mainSceneLoader.loadScene("MainScene", game.mainViewPort);
        game.mainItemWrapper = new ItemWrapper(game.mainSceneLoader.getRoot());
        //// TODO: добавить загрузку последнего уровня для игрока
        playerScript = new PlayerScript(BlocksLevelLoader.getLevelBlocks(game.mainItemWrapper, currentLevel), this);
        game.mainItemWrapper.getChild("player").addScript(playerScript);
    }


    public void nextLevel(){
        ++currentLevel;
        switch (currentLevel){
            case 1:
                break;
            case 2:
                playerScript.setGameObjectList(BlocksLevelLoader.getLevelBlocks(game.mainItemWrapper, currentLevel));
                playerScript.setPlayerСoordinates(820f,10.33f);
                game.setCameraCoords(getLevelX(), 320f);
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

    public float getLevelX(){
        switch (currentLevel){
            case 1:
                return 180f;
            case 2:
                return 964f;
            default:
                return 0f;
        }
    }

}

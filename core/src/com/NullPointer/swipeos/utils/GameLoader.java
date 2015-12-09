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

    public GameLoader(Game game){
        this.game = game;
    }

    public void initialize(){
        game.mainViewPort = new FitViewport(240,426);
        game.mainSceneLoader = new SceneLoader();
        game.mainSceneLoader.loadScene("MainScene", game.mainViewPort);
        game.mainItemWrapper = new ItemWrapper(game.mainSceneLoader.getRoot());
        playerScript = new PlayerScript(getLevelBlocks(game.mainItemWrapper, 8));
        game.mainItemWrapper.getChild("player").addScript(playerScript);
    }

    private List<Wall> getLevelBlocks(ItemWrapper itemWrapper, int numberOfBlocks){
        List<Wall> blocksList = new ArrayList<Wall>();
        for(int i = 0; i < numberOfBlocks; i++){
            Entity blockEntity = itemWrapper.getChild("wall" + i).getEntity();
            TransformComponent blockTransformComponent = blockEntity.getComponent(TransformComponent.class);
            DimensionsComponent blockDimensionComponent = blockEntity.getComponent(DimensionsComponent.class);
            switch (i){
                case 5:
                    blockDimensionComponent.width = 250;
                    break;
                case 6:
                    blockDimensionComponent.height = blockDimensionComponent.height * 100;
                    break;
                case 7:
                    blockDimensionComponent.height = blockDimensionComponent.height * 100;
                    break;
                default:
                    blockDimensionComponent.height = blockDimensionComponent.height / 2;
                    break;
            }
            blocksList.add(new Wall(
                    blockTransformComponent.x,
                    blockTransformComponent.y,
                    blockDimensionComponent.width,
                    blockDimensionComponent.height,
                    false
            ));
        }

        return  blocksList;
    }

    public float getPlayerY(){
        return playerScript.playerTransformComponent.y;
    }

    public float getPlayerX(){
        return playerScript.playerTransformComponent.x;
    }

}

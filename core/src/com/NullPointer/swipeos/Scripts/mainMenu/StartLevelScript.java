package com.NullPointer.swipeos.Scripts.mainMenu;

import com.NullPointer.swipeos.utils.GameLoader;
import com.NullPointer.swipeos.utils.LevelLoader;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 11.04.16.
 */
public class StartLevelScript implements IScript {

    Entity entity;
    GameLoader gameLoader;
    Rectangle buttonRectangle;
    int level;

    public StartLevelScript(GameLoader gameLoader, Entity entity, int level){
        this.gameLoader = gameLoader;
        this.entity = entity;
        TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        DimensionsComponent dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        this.buttonRectangle = new Rectangle(transformComponent.x, transformComponent.y,
                dimensionsComponent.width, dimensionsComponent.height);
        this.level = level;
    }

    @Override
    public void init(Entity entity) {

    }

    @Override
    public void act(float delta) {
        if(Gdx.input.justTouched()){
            Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            gameLoader.getCameraManager().getCamera().unproject(pos);
            if(buttonRectangle.contains(pos.x,pos.y)){
                gameLoader.removeAllEntities();
                int levelPack = 0;
                if(level > 0 && level <= 5){
                    levelPack = 1;
                }
                else if(level > 5 && level <= 10){
                    levelPack = 2;
                }
                else {
                    levelPack = 1;
                }

                switch (gameLoader.getLevelLoader().getSelectedStage()){
                    case 1:
                        break;
                    case 2:
                        level+=10;
                        break;
                    case 3:
                        level+=20;
                        break;
                    default:
                        break;
                }

                gameLoader.setSceneToLoad("levelPack"+levelPack);
                gameLoader.setCurrentLevel(level);
            }
        }
    }

    @Override
    public void dispose() {

    }
}

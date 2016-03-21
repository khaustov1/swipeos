package com.NullPointer.swipeos.Scripts.mainMenu;

import com.NullPointer.swipeos.utils.GameLoader;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 21.03.16.
 */
public class StartStageScript implements IScript{
    private GameLoader gameLoader;
    private Entity entity;
    private Rectangle buttonRectangle;
    TransformComponent transformComponent;
    DimensionsComponent dimensionsComponent;
    int stage;

    public StartStageScript(GameLoader gameLoader, Entity entity, int stage){
        this.gameLoader = gameLoader;
        this.entity = entity;
        this.stage = stage;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        this.buttonRectangle = new Rectangle(transformComponent.x, transformComponent.y,
                dimensionsComponent.width, dimensionsComponent.height);
    }

    @Override
    public void init(Entity entity) {
    }

    @Override
    public void act(float delta) {
        transformComponent.rotation += 20 *delta;
        if(Gdx.input.justTouched()){
            Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            gameLoader.getCameraManager().getCamera().unproject(pos);
            if(buttonRectangle.contains(pos.x,pos.y)){
                gameLoader.removeAllEntities();
                //поменять
                if(stage > 1)
                gameLoader.setCurrentLevel(6);
                gameLoader.setSceneToLoad("levelPack"+stage);
            }
        }
    }

    @Override
    public void dispose() {

    }
}

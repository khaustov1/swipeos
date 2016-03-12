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
 * Created by Khaustov on 06.03.16.
 */
public class PlayButtonScript implements IScript{
    private GameLoader gameLoader;
    private Entity entity;
    private Rectangle buttonRectangle;
    TransformComponent transformComponent;
    DimensionsComponent dimensionsComponent;

    public PlayButtonScript(GameLoader gameLoader, Rectangle rectangle, Entity entity){
        this.gameLoader = gameLoader;
        this.buttonRectangle = rectangle;
        this.entity = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
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
                gameLoader.setSceneToLoad("levelPack1");
            }
        }
    }

    @Override
    public void dispose() {

    }
}

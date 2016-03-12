package com.NullPointer.swipeos.Scripts;

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
 * Created by Khaustov on 28.02.16.
 */
public class NextLevelButtonScript implements IScript{
    private GameLoader gameLoader;
    private Entity entity;
    private Rectangle buttonRectangle;

    public NextLevelButtonScript(GameLoader gameLoader, Rectangle rectangle){
        this.gameLoader = gameLoader;
        this.buttonRectangle = rectangle;
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
                gameLoader.setPaused(false);
                gameLoader.nextLevel();
            }
        }
    }

    @Override
    public void dispose() {

    }
}

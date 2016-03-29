package com.NullPointer.swipeos.Objects;

import com.NullPointer.swipeos.Scripts.GameObjectsScripts.StarScript;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.MainItemComponent;
import com.uwsoft.editor.renderer.components.ScriptComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 27.02.16.
 */
public class Star implements GameObject {
    private Rectangle starRectangle;
    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;
    private Entity starEntity;
    private ScriptComponent scriptComponent;

    public Star(Entity entity){
        starEntity = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        starRectangle = new Rectangle(transformComponent.x, transformComponent.y,
                dimensionsComponent.width, dimensionsComponent.height);
        ScriptComponent scriptComponent = new ScriptComponent();
        scriptComponent.addScript((new StarScript(starEntity)));
        starEntity.add(scriptComponent);
    }

    @Override
    public boolean isDeadly() {
        return false;
    }

    @Override
    public void setY(float y) {

    }

    @Override
    public void setX(float y) {

    }

    public Rectangle getStarRectangle(){
        return starRectangle;
    }

    public void removeStar(){
        starEntity.remove(ScriptComponent.class);
        transformComponent.y = -100;
    }
}

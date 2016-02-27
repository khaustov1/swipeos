package com.NullPointer.swipeos.Objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.MainItemComponent;
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

    public Star(Entity entity){
        starEntity = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        dimensionsComponent.width = dimensionsComponent.width/2;
        dimensionsComponent.height = dimensionsComponent.height/2;
        starRectangle = new Rectangle(transformComponent.x, transformComponent.y,
                dimensionsComponent.width, dimensionsComponent.height);
    }

    @Override
    public boolean isDeadly() {
        return false;
    }

    public Rectangle getStarRectangle(){
        return starRectangle;
    }

    public void removeStar(){
        transformComponent.y = -100;
    }
}

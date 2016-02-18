package com.NullPointer.swipeos.Objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 17.02.16.
 */
public class Wall  implements GameObject{
    private Rectangle wallRectangle;
    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;

    public  Wall(Entity entity){
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        wallRectangle = new Rectangle(transformComponent.x, transformComponent.y,
                dimensionsComponent.width, dimensionsComponent.height);
    }

    public Rectangle getWallRectangle(){
        return wallRectangle;
    }

    public void setX(float x){
        transformComponent.x = x;
        wallRectangle.setX(x);
    }

    public void setY(float y){
        transformComponent.y = y;
        wallRectangle.setY(y);
    }

    public void setPosition(float x, float y){
        transformComponent.x = x;
        wallRectangle.setX(x);
        transformComponent.y = y;
        wallRectangle.setY(y);
    }

    public void increaseX(float increaseValue){
        transformComponent.x += increaseValue;
        wallRectangle.setX(transformComponent.x);
    }

    @Override
    public boolean isPortal() {
        return false;
    }

    @Override
    public boolean isDeadly() {
        return false;
    }
}

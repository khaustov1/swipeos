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

    public float getX(){
        return transformComponent.x;
    }

    public float getY(){
        return transformComponent.y;
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

    public void decreaseX(float decreaseValue){
        transformComponent.x -= decreaseValue;
        wallRectangle.setX(transformComponent.x);
    }

    public void increaseY(float increaseValue){
        transformComponent.y += increaseValue;
        wallRectangle.setY(transformComponent.y);
    }

    public void decreaseY(float decreaseValue){
        transformComponent.y -= decreaseValue;
        wallRectangle.setY(transformComponent.y);
    }


    @Override
    public boolean isDeadly() {
        return false;
    }
}

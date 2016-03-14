package com.NullPointer.swipeos.Objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 13.03.16.
 */
public class Asteroid implements GameObject{
    private Rectangle asteroidRectangle;
    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;
    boolean isDeadly;

    public  Asteroid(Entity entity){
        isDeadly = true;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        dimensionsComponent.width = dimensionsComponent.width/2;
        dimensionsComponent.height = dimensionsComponent.height/2;
        asteroidRectangle = new Rectangle(transformComponent.x, transformComponent.y,
                dimensionsComponent.width, dimensionsComponent.height);
    }

    public Rectangle getRectangle(){
        return asteroidRectangle;
    }

    public void setX(float x){
        transformComponent.x = x;
        asteroidRectangle.setX(x);
    }

    public void setY(float y){
        transformComponent.y = y;
        asteroidRectangle.setY(y);
    }

    public float getX(){
        return transformComponent.x;
    }

    public float getY(){
        return transformComponent.y;
    }

    public void setPosition(float x, float y){
        transformComponent.x = x;
        asteroidRectangle.setX(x);
        transformComponent.y = y;
        asteroidRectangle.setY(y);
    }

    public void increaseX(float increaseValue){
        transformComponent.x += increaseValue;
        asteroidRectangle.setX(transformComponent.x);
    }

    public void decreaseX(float decreaseValue){
        transformComponent.x -= decreaseValue;
        asteroidRectangle.setX(transformComponent.x);
    }

    public void increaseY(float increaseValue){
        transformComponent.y += increaseValue;
        asteroidRectangle.setY(transformComponent.y);
    }

    public void decreaseY(float decreaseValue){
        transformComponent.y -= decreaseValue;
        asteroidRectangle.setY(transformComponent.y);
    }

    public TransformComponent getTransformComponent(){
        return this.transformComponent;
    }


    @Override
    public boolean isDeadly() {
        return isDeadly;
    }

    public void setIsDeadly(boolean isDeadly){
        this.isDeadly = isDeadly;
    }
}
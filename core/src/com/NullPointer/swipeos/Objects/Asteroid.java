package com.NullPointer.swipeos.Objects;

import com.NullPointer.swipeos.Scripts.GameObjectsScripts.PlayerScript;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 13.03.16.
 */
public class Asteroid extends GameObject{
    private Circle asteroidCircle;
    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;
    boolean isDeadly;
    float circleXdifferenece = 0f;
    float circleYdifference = 0f;

    public  Asteroid(Entity entity){
        isDeadly = true;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        circleXdifferenece = dimensionsComponent.width/2;
        circleYdifference = dimensionsComponent.height/2;

        asteroidCircle = new Circle(transformComponent.x + circleXdifferenece,
                transformComponent.y + circleYdifference, circleYdifference);
        shape = new Shape<Circle>(asteroidCircle);
    }

    public void setX(float x){
        transformComponent.x = x;
        asteroidCircle.setX(x);
    }

    public void setY(float y){
        transformComponent.y = y;
        asteroidCircle.setY(y);
    }

    public float getX(){
        return transformComponent.x;
    }

    public float getY(){
        return transformComponent.y;
    }

    public void setPosition(float x, float y){
        transformComponent.x = x;
        asteroidCircle.setX(x);
        transformComponent.y = y;
        asteroidCircle.setY(y);
    }

    public void increaseX(float increaseValue){
        transformComponent.x += increaseValue;
        asteroidCircle.setX(transformComponent.x+circleXdifferenece);
    }

    public void increaseY(float increaseValue){
        transformComponent.y += increaseValue;
        asteroidCircle.setY(transformComponent.y + circleYdifference);
    }


    public TransformComponent getTransformComponent(){
        return this.transformComponent;
    }


    @Override
    public boolean isDeadly() {
        return isDeadly;
    }

    @Override
    public void collideWithPlayer(PlayerScript playerScript) {
        playerScript.die();
    }

    public void setIsDeadly(boolean isDeadly){
        this.isDeadly = isDeadly;
    }
}
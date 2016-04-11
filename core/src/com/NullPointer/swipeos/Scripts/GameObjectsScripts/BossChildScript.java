package com.NullPointer.swipeos.Scripts.GameObjectsScripts;

import com.NullPointer.swipeos.Objects.GameObject;
import com.NullPointer.swipeos.Objects.Shape;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.ScriptComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Khaustov on 10.04.16.
 */
public class BossChildScript extends GameObject implements IScript
{
    private boolean isDeadly;
    private Entity entity;
    private Engine engine;
    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;
    private Circle collisionCircle;

    float circleXdifferenece = 0f;
    float circleYdifference = 0f;

    private float speed = 50f;

    List<GameObject> gameObjectList;
    List<BossChildScript> childList;

    public BossChildScript(boolean isDeadly, Engine engine, Entity entity, List<GameObject> gameObjects,
                           List<BossChildScript> childList){
        this.isDeadly = isDeadly;
        this.engine = engine;
        this.childList = childList;

        this.entity = entity;
        transformComponent = ComponentRetriever.get(this.entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(this.entity, DimensionsComponent.class);
        dimensionsComponent.width = dimensionsComponent.width/3;
        dimensionsComponent.height = dimensionsComponent.height/3;

        circleXdifferenece = dimensionsComponent.width/2;
        circleYdifference = dimensionsComponent.height/2;

        collisionCircle = new Circle(transformComponent.x + circleXdifferenece,
                transformComponent.y + circleYdifference, circleYdifference);
        shape = new Shape<Circle>(collisionCircle);
        gameObjectList = gameObjects;
    }

    @Override
    public void setX(float x) {

    }

    @Override
    public void setY(float y) {

    }

    @Override
    public boolean isDeadly() {
        return isDeadly;
    }

    @Override
    public void collideWithPlayer(PlayerScript playerScript) {
        if(isDeadly){
            entity.remove(ScriptComponent.class);
            engine.removeEntity(entity);
            gameObjectList.remove(this);
            playerScript.die();
        }
        else {
            transformComponent.y = playerScript.getPlayerCircle().y +
                    playerScript.getPlayerCircle().radius;
            collisionCircle.setY(transformComponent.y + circleYdifference);
            speed = - 50f;
        }
    }

    @Override
    public void init(Entity entity) {
    }

    @Override
    public void act(float delta) {
        if(transformComponent.y < - 50 || transformComponent.y > 2000){
            entity.remove(ScriptComponent.class);
            engine.removeEntity(entity);
            childList.remove(this);
            gameObjectList.remove(this);
        }
        transformComponent.y -= speed*delta;
        collisionCircle.setY(transformComponent.y + circleYdifference);
    }

    @Override
    public void dispose() {

    }

    public void collideWithBoss(BossScript bossScript, Iterator<BossChildScript> iterator){
        entity.remove(ScriptComponent.class);
        engine.removeEntity(entity);
        iterator.remove();
        gameObjectList.remove(this);
        bossScript.getDamage();
    }
}

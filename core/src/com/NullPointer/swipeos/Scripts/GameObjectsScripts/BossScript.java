package com.NullPointer.swipeos.Scripts.GameObjectsScripts;

import com.NullPointer.swipeos.Objects.GameObject;
import com.NullPointer.swipeos.Objects.Shape;
import com.NullPointer.swipeos.utils.GameLoader;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.data.SpriteAnimationVO;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Khaustov on 10.04.16.
 */
public class BossScript extends GameObject implements IScript {
    Entity bossEntity;
    Circle bossCircle;
    TransformComponent transformComponent;
    DimensionsComponent dimensionsComponent;

    GameLoader gameLoader;

    float circleXdifferenece = 0f;
    float circleYdifference = 0f;
    float from, to;

    float bossSpeed = 30f;
    boolean back;


    Timer timer = new Timer();

    public BossScript(GameLoader gameLoader){
        this.gameLoader = gameLoader;
    }

    @Override
    public void init(Entity entity) {
        bossEntity = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        circleXdifferenece = dimensionsComponent.width/2;
        circleYdifference = dimensionsComponent.height/2;

        bossCircle = new Circle(transformComponent.x + circleXdifferenece,
                transformComponent.y + circleYdifference, circleYdifference);
        from = transformComponent.x - 100;
        to = transformComponent.x + 100;

        shape = new Shape<Circle>(bossCircle);

        timer.schedule(new TimerTask() {
            public void run() {
                spawnChild();
            }
        }, 0, 60 * 1000);
    }

    @Override
    public void act(float delta) {
        moveBoss(delta);
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    @Override
    public void setX(float x) {
        transformComponent.x = x;
        bossCircle.setX(x);
    }

    @Override
    public void setY(float y) {
        transformComponent.y = y;
        bossCircle.setY(y);
    }

    @Override
    public boolean isDeadly() {
        return true;
    }

    @Override
    public void collideWithPlayer(PlayerScript playerScript) {
        playerScript.die();
    }

    private void increaseX(float increaseValue){
        transformComponent.x += increaseValue;
        bossCircle.setX(transformComponent.x + circleXdifferenece);
    }

    private void moveBoss(float delta){
        this.increaseX(bossSpeed * delta);
        if (!back) {
            if (transformComponent.x >= to) {
                bossSpeed = -bossSpeed;
                back = true;
            }
        }
        else {
            if (transformComponent.x <= from) {
                bossSpeed = -bossSpeed;
                back = false;
            }
        }
    }

    private void spawnChild(){
        int chance = getRandomNumberInRange(0, 10);
        if(chance >= 7) {
            Entity childAnimationExampleEntity = gameLoader.getItemWrapper().getChild("player").getEntity();

            SpriteAnimationVO spriteAnimationVO = new SpriteAnimationVO();
            spriteAnimationVO.loadFromEntity(childAnimationExampleEntity);
            spriteAnimationVO.x = transformComponent.x;
            spriteAnimationVO.y = transformComponent.y - 50;
            spriteAnimationVO.layerName = "popup";
            spriteAnimationVO.playMode = 2;
            Entity bossChild = gameLoader.getGame().mainSceneLoader.entityFactory.createEntity(
                    gameLoader.getGame().mainSceneLoader.getRoot(), spriteAnimationVO);
            gameLoader.getGame().mainSceneLoader.getEngine().addEntity(bossChild);
        }
        else {

        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}

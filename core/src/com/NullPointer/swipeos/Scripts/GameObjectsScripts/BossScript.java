package com.NullPointer.swipeos.Scripts.GameObjectsScripts;

import com.NullPointer.swipeos.Objects.GameObject;
import com.NullPointer.swipeos.Objects.Shape;
import com.NullPointer.swipeos.utils.GameLoader;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.ScriptComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.data.SpriteAnimationVO;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    List<BossChildScript> childList = new ArrayList<BossChildScript>();

    GameLoader gameLoader;

    float circleXdifferenece = 0f;
    float circleYdifference = 0f;
    float from, to;

    float bossSpeed = 50f;
    float bossYCoordinate = 0f;
    boolean back;
    boolean needToCreateChild;

    int health = 5;


    Timer timer = new Timer();

    public BossScript(GameLoader gameLoader, Entity entity){
        this.gameLoader = gameLoader;
        bossEntity = entity;
        transformComponent = ComponentRetriever.get(bossEntity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(bossEntity, DimensionsComponent.class);
        dimensionsComponent.width = dimensionsComponent.width*3;
        dimensionsComponent.height = dimensionsComponent.height*3;
        circleXdifferenece = dimensionsComponent.width/2;
        circleYdifference = dimensionsComponent.height/2;

        bossCircle = new Circle(transformComponent.x + circleXdifferenece,
                transformComponent.y + circleYdifference, circleYdifference);
        bossYCoordinate = transformComponent.y;

        from = transformComponent.x - circleXdifferenece;
        to = transformComponent.x - circleXdifferenece + 100;

        shape = new Shape<Circle>(bossCircle);

        timer.schedule(new TimerTask() {
            public void run() {
                setCreateChildNeeded();
            }
        }, 3*1000, 5 * 1000);
    }

    @Override
    public void init(Entity entity) {

    }

    @Override
    public void act(float delta) {
        moveBoss(delta);

        Iterator<BossChildScript> iter = childList.iterator();
        while(iter.hasNext()){
            BossChildScript child = iter.next();
            if(bossCircle.overlaps((Circle)child.getShape().getCurrentShape()))
            {
                child.collideWithBoss(this, iter);
            }
        }
        if(needToCreateChild){
            spawnChild();
        }
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

    //Сделал создание в основном потоке, чтобы не крашилось
    private void spawnChild(){
        int chance = getRandomNumberInRange(0, 10);
        Entity childAnimationExampleEntity;
        Entity bossChild;
        SpriteAnimationVO spriteAnimationVO = new SpriteAnimationVO();
        ScriptComponent scriptComponent = new ScriptComponent();
        BossChildScript bossChildScript;
        if(chance >= 7) {

            childAnimationExampleEntity = gameLoader.getItemWrapper().getChild("player").getEntity();

            spriteAnimationVO.loadFromEntity(childAnimationExampleEntity);
            spriteAnimationVO.x = transformComponent.x + circleXdifferenece;
            spriteAnimationVO.y = bossCircle.y - 185;
            spriteAnimationVO.layerName = "popup";
            spriteAnimationVO.playMode = 2;
            bossChild = gameLoader.getGame().mainSceneLoader.entityFactory.createEntity(
                    gameLoader.getGame().mainSceneLoader.getRoot(), spriteAnimationVO);
            gameLoader.getGame().mainSceneLoader.getEngine().addEntity(bossChild);
            bossChildScript = new BossChildScript(false,
                    gameLoader.getGame().mainSceneLoader.engine, bossChild,
                    gameLoader.getLevelLoader().getGameObjectList(), childList);
            childList.add(bossChildScript);

        }
        else {
            childAnimationExampleEntity = gameLoader.getItemWrapper().getChild("boss").getEntity();

            spriteAnimationVO.loadFromEntity(childAnimationExampleEntity);
            spriteAnimationVO.x = transformComponent.x + circleXdifferenece;
            spriteAnimationVO.y = transformComponent.y;
            spriteAnimationVO.layerName = "popup";
            spriteAnimationVO.playMode = 2;
            bossChild = gameLoader.getGame().mainSceneLoader.entityFactory.createEntity(
                    gameLoader.getGame().mainSceneLoader.getRoot(), spriteAnimationVO);
            gameLoader.getGame().mainSceneLoader.getEngine().addEntity(bossChild);
            bossChildScript = new BossChildScript(true,
                    gameLoader.getGame().mainSceneLoader.engine, bossChild,
                    gameLoader.getLevelLoader().getGameObjectList(), childList);
        }
        scriptComponent.addScript(bossChildScript);
        bossChild.add(scriptComponent);
        gameLoader.getLevelLoader().getGameObjectList().add(bossChildScript);
        needToCreateChild = false;
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void getDamage(){
        --health;
        if(health <= 0){
            bossEntity.remove(ScriptComponent.class);
            gameLoader.getGame().mainSceneLoader.getEngine().removeEntity(bossEntity);
            timer.cancel();
            gameLoader.getLevelLoader().getLevelPortal().setY(bossYCoordinate);
            //gameObjectList.remove(this);
        }
    }

    public void setCreateChildNeeded(){
        needToCreateChild = true;
    }
}

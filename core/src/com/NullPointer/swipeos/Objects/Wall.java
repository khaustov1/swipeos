package com.NullPointer.swipeos.Objects;

import com.NullPointer.swipeos.Scripts.GameObjectsScripts.PlayerScript;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 17.02.16.
 */
public class Wall  extends GameObject{
    private Rectangle wallRectangle;
    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;
    boolean isDeadly;

    public  Wall(Entity entity){
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        wallRectangle = new Rectangle(transformComponent.x, transformComponent.y,
                dimensionsComponent.width, dimensionsComponent.height);
        shape = new Shape<Rectangle>(wallRectangle);
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


    public void increaseX(float increaseValue){
        transformComponent.x += increaseValue;
        wallRectangle.setX(transformComponent.x);
    }


    public void increaseY(float increaseValue){
        transformComponent.y += increaseValue;
        wallRectangle.setY(transformComponent.y);
    }

    @Override
    public boolean isDeadly() {
        return isDeadly;
    }

    @Override
    public void collideWithPlayer(PlayerScript playerScript) {
        if (this.isDeadly()) {
            playerScript.setPlayerСoordinates(playerScript.getGameLoader().getLevelXStartCoordinate(),
                    45f);
            return;
        }

        if (playerScript.playerSpeed.y > 0) {
            playerScript.playerTransformComponent.y -= 3;
        } else if (playerScript.playerSpeed.y < 0) {
            playerScript.playerTransformComponent.y += 3;
        }
        if (playerScript.playerSpeed.x > 0) {
            playerScript.playerTransformComponent.x -= 3;
        } else if (playerScript.playerSpeed.x < 0) {
            playerScript.playerTransformComponent.x += 3;
        }
        playerScript.playerSpeed.x = -playerScript.playerSpeed.x;
        playerScript.playerSpeed.y = -playerScript.playerSpeed.y;
    }

    public void setIsDeadly(boolean isDeadly){
        this.isDeadly = isDeadly;
    }
}

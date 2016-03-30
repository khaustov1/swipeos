package com.NullPointer.swipeos.Objects;

import com.NullPointer.swipeos.Scripts.GameObjectsScripts.PlayerScript;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Khaustov on 17.02.16.
 */
public class Portal extends GameObject {
    private Circle portalCircle;

    public Portal(float x, float y, float radius){
        portalCircle = new Circle(x, y, radius);
        shape = new Shape<Circle>(portalCircle);
    }

    public Circle getPortalCircle(){
        return portalCircle;
    }

    @Override
    public boolean isDeadly() {
        return false;
    }

    @Override
    public void collideWithPlayer(PlayerScript playerScript) {
        playerScript.playerSpeed.x = 0;
        playerScript.playerSpeed.y = 0;
        playerScript.getGameLoader().showLevelCompleteWindow();
    }

    @Override
    public void setY(float y) {

    }

    @Override
    public void setX(float y) {

    }
}

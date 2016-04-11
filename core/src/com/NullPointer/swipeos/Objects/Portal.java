package com.NullPointer.swipeos.Objects;

import com.NullPointer.swipeos.Scripts.GameObjectsScripts.PlayerScript;
import com.NullPointer.swipeos.Scripts.GameObjectsScripts.PortalScript;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Khaustov on 17.02.16.
 */
public class Portal extends GameObject {
    private Circle portalCircle;
    private PortalScript portalScript;
    float radius;

    public Portal(float x, float y, float radius, PortalScript portalScript){
        portalCircle = new Circle(x, y, radius);
        shape = new Shape<Circle>(portalCircle);
        this.portalScript = portalScript;
        this.radius = radius;
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
        portalCircle.y = y;
        portalScript.setY(y+radius);
    }

    @Override
    public void setX(float x) {
        portalCircle.x = x;
    }

}

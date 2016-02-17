package com.NullPointer.swipeos.Objects;

import com.badlogic.gdx.math.Circle;

/**
 * Created by Khaustov on 17.02.16.
 */
public class Portal implements GameObject {
    private Circle portalCircle;

    public Portal(float x, float y, float radius){
        portalCircle = new Circle(x, y, radius);
    }

    public Circle getPortalCircle(){
        return portalCircle;
    }
    @Override
    public boolean isPortal() {
        return true;
    }

    @Override
    public boolean isDeadly() {
        return false;
    }
}

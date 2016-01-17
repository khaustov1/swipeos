package com.NullPointer.swipeos.Objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Khaustov on 08.12.15.
 */
public class GameObject extends Rectangle {

    private boolean isDeadly; //Умирает ли игрок от соприкосновения с этой стенкой

    private boolean isPortal;

    public boolean isPortal() {
        return isPortal;
    }

    public void setIsPortal(boolean isPortal) {
        this.isPortal = isPortal;
    }

    public boolean isDeadly() {
        return isDeadly;
    }

    public void setIsDeadly(boolean isDeadly) {
        this.isDeadly = isDeadly;
    }

    public GameObject(float x, float y, float width, float height)
    {
        super(x,y,width,height);
    }

    public boolean collisionWithCircle(Circle circle){
        return Intersector.overlaps(circle, this);
    }
}

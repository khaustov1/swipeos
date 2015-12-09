package com.NullPointer.swipeos.utils;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Khaustov on 08.12.15.
 */
public class Wall extends Rectangle {

    private boolean isDeadly; //Умирает ли игрок от соприкосновения с этой стенкой

    public boolean isDeadly() {
        return isDeadly;
    }

    public void setIsDeadly(boolean isDeadly) {
        this.isDeadly = isDeadly;
    }

    public Wall(float x, float y, float width, float height, boolean isDeadly)
    {
        super(x,y,width,height);
        this.isDeadly = isDeadly;
    }
}

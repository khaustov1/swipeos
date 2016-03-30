package com.NullPointer.swipeos.Objects;

import com.NullPointer.swipeos.Scripts.GameObjectsScripts.PlayerScript;

/**
 * Created by Khaustov on 30.03.16.
 */
public abstract class GameObject {
    protected Shape shape;

    public abstract void setX(float x);
    public abstract void setY(float y);
    public abstract boolean isDeadly();
    public abstract void collideWithPlayer(PlayerScript playerScript);

    public Shape getShape(){
        return shape;
    };
}

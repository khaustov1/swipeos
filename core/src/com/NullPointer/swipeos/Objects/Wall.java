package com.NullPointer.swipeos.Objects;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Khaustov on 17.02.16.
 */
public class Wall  implements GameObject{
    private Rectangle wallRectangle;

    public  Wall(float x, float y, float width, float height){
        wallRectangle = new Rectangle(x, y, width, height);
    }

    public Rectangle getWallRectangle(){
        return wallRectangle;
    }

    @Override
    public boolean isPortal() {
        return false;
    }

    @Override
    public boolean isDeadly() {
        return false;
    }
}

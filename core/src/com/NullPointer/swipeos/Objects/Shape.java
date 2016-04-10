package com.NullPointer.swipeos.Objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Khaustov on 30.03.16.
 */
public class Shape<T> {

    private T currentShape;
    private boolean isCircle;
    private boolean isRectangle;

    public Shape(T shape){
        currentShape = shape;
        if(((Class<T>) currentShape.getClass()).equals(Rectangle.class)){
            isRectangle = true;
        }
        else if(((Class<T>) currentShape.getClass()).equals(Circle.class)){
            isCircle = true;
        }
    }

    public T getCurrentShape() {
        return currentShape;
    }


    public boolean isRectangle() {
        return isRectangle;
    }

    public boolean isCircle() {
        return isCircle;
    }
}

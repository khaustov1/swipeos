package com.NullPointer.swipeos.utils;

import com.badlogic.gdx.input.GestureDetector;

/**
 * Created by Khaustov on 05.12.15.
 */
public class DirectionGestureDetector extends GestureDetector {
    public interface DirectionListener {
        void onLeft();

        void onRight();

        void onUp();

        void onDown();

        void onTap();
    }

    public DirectionGestureDetector(DirectionListener directionListener) {
        super(new DirectionGestureListener(directionListener));
    }

    private static class DirectionGestureListener extends GestureAdapter{
        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener){
            this.directionListener = directionListener;
        }

        @Override
        public boolean tap (float x, float y, int count, int button) {
            directionListener.onTap();
            return super.tap(x,y,count,button);
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if(Math.abs(velocityX)>Math.abs(velocityY)){
                if(velocityX>0){
                    directionListener.onRight();
                }else{
                    directionListener.onLeft();
                }
            }else{
                if(velocityY>0){
                    directionListener.onDown();
                }else{
                    directionListener.onUp();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

    }

}

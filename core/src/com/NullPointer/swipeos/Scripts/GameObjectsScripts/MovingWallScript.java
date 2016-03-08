package com.NullPointer.swipeos.Scripts.GameObjectsScripts;

import com.NullPointer.swipeos.Objects.Wall;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.scripts.IScript;

/**
 * Created by Khaustov on 21.02.16.
 */
public class MovingWallScript implements IScript{
    Wall currentWall;
    float from, to;
    Vector2 movingSpeed = new Vector2(0,0);
    boolean isMovingPositive;
    boolean back;
    boolean isAxisX,isAxisY;

    public MovingWallScript(Wall wall, char axis, float from, float to, float speed){
        this.currentWall = wall;
        this.from = from;
        this.to = to;
        switch (axis){
            case 'x':
                isAxisX = true;
                isAxisY = false;
                if(to > from) {
                    movingSpeed.add(speed, 0);
                    isMovingPositive = true;
                }
                else {
                    movingSpeed.add(-speed, 0);
                    isMovingPositive = false;
                }
                break;
            case 'y':
                isAxisX = false;
                isAxisY = true;
                if(to > from) {
                    movingSpeed.add(0, speed);
                    isMovingPositive = true;
                }
                else {
                    movingSpeed.add(0, -speed);
                    isMovingPositive = false;
                }
                default:
                    movingSpeed.add(0,0);
                    break;
        }

    }

    @Override
    public void init(Entity entity) {

    }

    @Override
    public void act(float delta) {
        currentWall.increaseX(movingSpeed.x * delta);
        currentWall.increaseY(movingSpeed.y*delta);
        if(isAxisX){
            if(isMovingPositive){
                if(!back) {
                    if (currentWall.getX() >= to) {
                        movingSpeed.x = -movingSpeed.x;
                        back = true;
                    }
                }
                else {
                    if(currentWall.getX() <= from){
                        movingSpeed.x = -movingSpeed.x;
                        back = false;
                    }
                }
            }
            else {
                if (!back) {
                    if (currentWall.getX() >= to) {
                        movingSpeed.x = -movingSpeed.x;
                        back = true;
                    }
                } else {
                    if (currentWall.getX() >= from) {
                        movingSpeed.x = -movingSpeed.x;
                        back = false;
                    }
                }
            }
        }
        else {
            if(isMovingPositive){
                if(!back) {
                    if (currentWall.getY() >= to) {
                        movingSpeed.y = -movingSpeed.y;
                        back = true;
                    }
                }
                else {
                    if(currentWall.getY() <= from){
                        movingSpeed.y = -movingSpeed.y;
                        back = false;
                    }
                }
            }
            else {
                if (!back) {
                    if (currentWall.getY() >= to) {
                        movingSpeed.y = -movingSpeed.y;
                        back = true;
                    }
                } else {
                    if (currentWall.getY() >= from) {
                        movingSpeed.y = -movingSpeed.y;
                        back = false;
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {

    }
}

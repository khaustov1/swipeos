package com.NullPointer.swipeos.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Khaustov on 12.03.16.
 */
public class CameraManager {
    private OrthographicCamera camera;
    private GameLoader gameLoader;
    private boolean isMovingNow;
    private boolean isMovingBack;
    private float moveToCoordinate = 0f;
    private float movingSpeed = 0f;

    public CameraManager(OrthographicCamera camera, GameLoader gameLoader){
        this.camera = camera;
        this.gameLoader = gameLoader;
    }

    public void stopMoving(){
        isMovingBack = false;
        isMovingNow = false;
        moveToCoordinate = 0f;
    }

    public void updateCamera(float delta){
        if(isMovingNow){
            if(isMovingBack){
                if(camera.position.y <= moveToCoordinate) {
                    isMovingNow = false;
                }
                else {
                    camera.position.y -= 230 * delta;
                }
            }
            else {
                if(camera.position.y >= moveToCoordinate){
                    isMovingNow = false;
                }
                else {
                    camera.position.y += 230 * delta;
                }
            }
        }
        else {
            if (gameLoader.getPlayerY() < gameLoader.getCameraStopY() &&
                    gameLoader.getPlayerY() > 320f) {
                if (gameLoader.getPlayerY() > camera.position.y+150f) {
                    moveToCoordinate = gameLoader.getPlayerY();
                    isMovingNow = true;
                    isMovingBack = false;
                }
                else if (gameLoader.getPlayerY() < camera.position.y-150f) {
                    moveToCoordinate = gameLoader.getPlayerY();
                    isMovingNow = true;
                    isMovingBack = true;
                }
            }
            else if(gameLoader.getPlayerY() > gameLoader.getCameraStopY()){
                isMovingNow = true;
                isMovingBack = false;
                moveToCoordinate = gameLoader.getCameraStopY();
            }
            else if(gameLoader.getPlayerY() < 320f){
                isMovingNow = true;
                isMovingBack = true;
                moveToCoordinate = 320f;
            }
        }
    }

    public void setCameraCoords(float x, float y){
        camera.position.set(x, y, 0);
    }

    public OrthographicCamera getCamera(){
        return this.camera;
    }
}

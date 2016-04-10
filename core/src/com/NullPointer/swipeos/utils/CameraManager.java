package com.NullPointer.swipeos.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Khaustov on 12.03.16.
 */
public class CameraManager {
    private boolean stop;
    private OrthographicCamera camera;
    private GameLoader gameLoader;
    private boolean isMovingNow;
    private boolean isMovingBack;
    private float moveToCoordinate = 0f;
    private float movingSpeed = 230f;

    public CameraManager(OrthographicCamera camera, GameLoader gameLoader){
        this.camera = camera;
        this.gameLoader = gameLoader;
        this.stop = false;
    }

    public void stopMoving(){
        isMovingBack = false;
        isMovingNow = false;
        moveToCoordinate = 0f;
    }

    public void updateCamera(float delta){
        if(!stop) {
            if(Math.abs(camera.position.y - gameLoader.getPlayerY()) > 200)
                movingSpeed = 500f;
            if (isMovingNow) {
                if (isMovingBack) {
                    if (camera.position.y <= moveToCoordinate) {
                        isMovingNow = false;
                        movingSpeed = 230f;
                    } else {
                        camera.position.y -= movingSpeed * delta;
                    }
                } else {
                    if (camera.position.y >= moveToCoordinate) {
                        isMovingNow = false;
                        movingSpeed = 230f;
                    } else {
                        camera.position.y += movingSpeed * delta;
                    }
                }
            } else {
                if (gameLoader.getPlayerY() < gameLoader.getCameraStopY() &&
                        gameLoader.getPlayerY() > 320f) {
                    if (gameLoader.getPlayerY() > camera.position.y + 100f) {
                        moveToCoordinate = gameLoader.getPlayerY();
                        isMovingNow = true;
                        isMovingBack = false;
                    } else if (gameLoader.getPlayerY() < camera.position.y - 100f) {
                        moveToCoordinate = gameLoader.getPlayerY();
                        isMovingNow = true;
                        isMovingBack = true;
                    }
                } else if (gameLoader.getPlayerY() > gameLoader.getCameraStopY()) {
                    isMovingNow = true;
                    isMovingBack = false;
                    moveToCoordinate = gameLoader.getCameraStopY();
                } else if (gameLoader.getPlayerY() < 320f) {
                    isMovingNow = true;
                    isMovingBack = true;
                    moveToCoordinate = 320f;
                }
            }
        }
    }

    public void stop(){
        stop = true;
    }

    public void start(){
        stop = false;
    }

    public void setCameraCoords(float x, float y){
        camera.position.set(x, y, 0);
    }

    public OrthographicCamera getCamera(){
        return this.camera;
    }
}

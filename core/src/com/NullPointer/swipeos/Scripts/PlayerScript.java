package com.NullPointer.swipeos.Scripts;

import com.NullPointer.swipeos.utils.CollisionManager;
import com.NullPointer.swipeos.utils.DirectionGestureDetector;
import com.NullPointer.swipeos.utils.GameLoader;
import com.NullPointer.swipeos.Objects.GameObject;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

import java.util.List;

/**
 * Created by Khaustov on 05.12.15.
 */
public class PlayerScript implements IScript {

    private GameLoader gameLoader;

    public  TransformComponent    playerTransformComponent; //для получения координат персонажа
    private DimensionsComponent   playerDimensionsComponent; //получение размеров персонажа

    private float     friction = 0.1f; // трение, чтобы игрок останавливался
    private float     playerCircleRadius = 0f;
    public Vector2   playerSpeed = new Vector2(0f,0f); //вектор, хранящий в себе скорость пресонажа по осям
    private int       speedLimit = 150;

    private Circle    playerCircle;

    boolean isX_AxisNegative; // проверяем направление движения игрока
    boolean isY_AxisNegative; // проверяем направление движения игрока

    boolean isCollidingNow; // Флаг для того, чтобы не менять направление игрока во время коллизии

    boolean checkForCollision;

    List<GameObject> gameObjectList; // коллекция с препядствиями

    public PlayerScript(List<GameObject> gameObjects, GameLoader gameLoader){
        this.gameObjectList = gameObjects;
        this.gameLoader = gameLoader;
    }

    @Override
    public void init(Entity entity) {
        //Получаю компоненты игрока
        playerTransformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        playerDimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);

        // Уменьшаю размер игрока
        playerDimensionsComponent.width = playerDimensionsComponent.width / 1.3f;
        playerDimensionsComponent.height = playerDimensionsComponent.height / 1.3f;

        playerCircleRadius = playerDimensionsComponent.width/2;
        playerCircle = new Circle(playerTransformComponent.x + playerCircleRadius,
                playerTransformComponent.y + playerCircleRadius,
                playerDimensionsComponent.width/2);

        // Эта штука для отлова жестов свайпа, обработка в классе DirectionGestureDetector.class

        Gdx.input.setInputProcessor(new DirectionGestureDetector(new DirectionGestureDetector.DirectionListener() {

            @Override
            public void onUp() {
                if (!isCollidingNow) {
                    playerSpeed.y += 90;
                    playerSpeed.x = 0;
                    isY_AxisNegative = false;
                }
            }

            @Override
            public void onRight() {
                if (!isCollidingNow) {
                    playerSpeed.x += 90;
                    playerSpeed.y = 0;
                    isX_AxisNegative = false;
                }
            }

            @Override
            public void onLeft() {
                if (!isCollidingNow) {
                    playerSpeed.x -= 90;
                    playerSpeed.y = 0;
                    isX_AxisNegative = true;
                }
            }

            @Override
            public void onDown() {
                if (!isCollidingNow) {
                    playerSpeed.y -= 90;
                    playerSpeed.x = 0;
                    isY_AxisNegative = true;
                }
            }
        }));
    }


    // Здесь идет обработка коллизий и изменение скорости во время рендеринга
    @Override
    public void act(float delta) {
        speedLimit();
        moveCharacter(delta);
        CollisionManager.checkForCollision(this, gameObjectList, playerCircle);
    }

    @Override
    public void dispose() {

    }

    public void startCollision(){
        isCollidingNow = true;
    }

    public void endCollision(){
        isCollidingNow = false;
    }

    public void speedLimit(){
        if(playerSpeed.y < -speedLimit){
            playerSpeed.y = -speedLimit;
        }
        if(playerSpeed.y > speedLimit){
            playerSpeed.y = speedLimit;
        }
        if(playerSpeed.x < -speedLimit){
            playerSpeed.x = -speedLimit;
        }
        if(playerSpeed.x > speedLimit){
            playerSpeed.x = speedLimit;
        }
    }

    public void moveCharacter(float delta){
        //Двигаем персонажа
        playerTransformComponent.x += playerSpeed.x * delta;
        playerTransformComponent.y += playerSpeed.y * delta;

        //Замедляем персонажа из-за трения
        if(playerSpeed.x > 0){
            playerSpeed.x -= friction;
        }
        if(playerSpeed.x < 0 && isX_AxisNegative){
            playerSpeed.x += friction;
        }
        if(playerSpeed.y > 0){
            playerSpeed.y -= friction;
        }
        if(playerSpeed.y < 0 && isY_AxisNegative){
            playerSpeed.y += friction;
        }

        playerCircle.setX(playerTransformComponent.x + playerCircleRadius);
        playerCircle.setY(playerTransformComponent.y + playerCircleRadius);
    }

    public void setPlayerСoordinates(float x, float y){
        playerTransformComponent.x = x;
        playerTransformComponent.y = y;
    }

    public List<GameObject> getGameObjectList() {
        return gameObjectList;
    }

    public void setGameObjectList(List<GameObject> gameObjectList) {
        this.gameObjectList = gameObjectList;
    }

    public GameLoader getGameLoader() {
        return gameLoader;
    }

    public void enableCollisonDetection(){
        this.checkForCollision = true;
    }

    public void disableCollisonDetection(){
        this.checkForCollision = true;
    }

    public boolean getCollisionDetectionStatus(){
        return this.checkForCollision;
    }
}

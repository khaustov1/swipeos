package com.NullPointer.swipeos.Scripts;

import com.NullPointer.swipeos.utils.DirectionGestureDetector;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

import java.util.List;

/**
 * Created by Khaustov on 05.12.15.
 */
public class PlayerScript implements IScript {

    private TransformComponent    playerTransformComponent; //для получения координат персонажа
    private DimensionsComponent   playerDimensionsComponent; //получение размеров персонажа

    private float     friction = 0.1f; // трение, чтобы игрок останавливался
    private Vector2   playerSpeed = new Vector2(0f,0f); //вектор, хранящий в себе скорость пресонажа по осям
    private int       speedLimit = 10000;

    boolean isX_AxisNegative; // проверяем направление движения игрока
    boolean isY_AxisNegative; // проверяем направление движения игрока

    boolean isCollidingNow; // Флаг для того, чтобы не менять направление игрока во время коллизии

    List<Rectangle> blocksList; // коллекция с препядствиями

    public PlayerScript(List<Rectangle> blocks){
        this.blocksList = blocks;
    }

    @Override
    public void init(Entity entity) {
        //Получаю компоненты игрока
        playerTransformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        playerDimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);

        // Уменьшаю размер игрока в три раза
        playerDimensionsComponent.width = playerDimensionsComponent.width / 3;
        playerDimensionsComponent.height = playerDimensionsComponent.height / 3;

        // Эта штука для отлова жестов свайпа, обработка в классе DirectionGestureDetector.class

        Gdx.input.setInputProcessor(new DirectionGestureDetector(new DirectionGestureDetector.DirectionListener() {

            @Override
            public void onUp() {
                if(!isCollidingNow) {
                    playerSpeed.y += 90;
                    playerSpeed.x = 0;
                    isY_AxisNegative = false;
                }
            }

            @Override
            public void onRight() {
                if(!isCollidingNow) {
                    playerSpeed.x += 90;
                    playerSpeed.y = 0;
                    isX_AxisNegative = false;
                }
            }

            @Override
            public void onLeft() {
                if(!isCollidingNow) {
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

        Rectangle playerRectangle = new Rectangle(
                playerTransformComponent.x,
                playerTransformComponent.y,
                playerDimensionsComponent.width,
                playerDimensionsComponent.height);

        // Проверка коллизий
        for(Rectangle block : blocksList){
            if(playerRectangle.overlaps(block) || playerTransformComponent.y < 0){
                //Коллизия началась
                isCollidingNow = true;
                if(playerSpeed.y > 0){
                    playerTransformComponent.y -= 3;
                }
                else if(playerSpeed.y < 0){
                    playerTransformComponent.y += 3;
                }
                if(playerSpeed.x > 0) {
                    playerTransformComponent.x -= 3;
                }
                else if(playerSpeed.x < 0){
                    playerTransformComponent.x += 3;
                }
                playerSpeed.x = - playerSpeed.x;
                playerSpeed.y = - playerSpeed.y;
            }
        }

        //Коллизия закончилась
        isCollidingNow = false;
    }

    @Override
    public void dispose() {

    }

    private void speedLimit(){
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

    private void moveCharacter(float delta){
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
    }
}

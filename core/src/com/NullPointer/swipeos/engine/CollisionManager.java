package com.NullPointer.swipeos.engine;

import com.NullPointer.swipeos.Objects.Asteroid;
import com.NullPointer.swipeos.Objects.GameObject;
import com.NullPointer.swipeos.Objects.Star;
import com.NullPointer.swipeos.Objects.Wall;
import com.NullPointer.swipeos.Scripts.GameObjectsScripts.PlayerScript;
import com.NullPointer.swipeos.utils.LevelLoader;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

/**
 * Created by Khaustov on 17.01.16.
 */
public class CollisionManager {

    public static void checkForCollision(PlayerScript playerScript, LevelLoader levelLoader, Circle playerCircle) {
        // Проверка коллизий
        if (playerScript.playerTransformComponent.y < 0) {
            playerScript.startCollision();

            if (playerScript.playerSpeed.y > 0) {
                playerScript.playerTransformComponent.y -= 3;
            } else if (playerScript.playerSpeed.y < 0) {
                playerScript.playerTransformComponent.y += 3;
            }
            if (playerScript.playerSpeed.x > 0) {
                playerScript.playerTransformComponent.x -= 3;
            } else if (playerScript.playerSpeed.x < 0) {
                playerScript.playerTransformComponent.x += 3;
            }
            playerScript.playerSpeed.x = -playerScript.playerSpeed.x;
            playerScript.playerSpeed.y = -playerScript.playerSpeed.y;
            playerScript.endCollision();

        } else {
            for (GameObject gameObject : levelLoader.getGameObjectList()) {
                try {
                    if (gameObject.getShape().isCircle()) {
                        if (Intersector.overlaps(playerCircle, (Circle) gameObject.getShape().getCurrentShape())) {
                            playerScript.startCollision();
                            gameObject.collideWithPlayer(playerScript);
                            break;
                        }
                    } else if (gameObject.getShape().isRectangle()) {
                        if (Intersector.overlaps(playerCircle, (Rectangle) gameObject.getShape().getCurrentShape())) {
                            playerScript.startCollision();
                            gameObject.collideWithPlayer(playerScript);
                            break;
                        }
                    }
                }
                catch (Exception e){
                    System.out.print(e.toString());
                }
            }
            //Коллизия закончилась
            playerScript.endCollision();
        }

    }
}

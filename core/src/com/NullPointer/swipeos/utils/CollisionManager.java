package com.NullPointer.swipeos.utils;

import com.NullPointer.swipeos.Scripts.PlayerScript;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.List;

/**
 * Created by Khaustov on 17.01.16.
 */
public class CollisionManager {

    public static void checkForCollision(PlayerScript playerScript, List<com.NullPointer.swipeos.Objects.GameObject> gameObjectList, Circle playerCircle) {

        // Проверка коллизий
        for (com.NullPointer.swipeos.Objects.GameObject object : gameObjectList) {
            if (Intersector.overlaps(playerCircle, object) || playerScript.playerTransformComponent.y < 0) {
                //Коллизия началась
                playerScript.startCollision();

                if (object.isPortal()) {
                    playerScript.playerSpeed.x = 0;
                    playerScript.playerSpeed.y = 0;
                    playerScript.getGameLoader().nextLevel();
                }

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
            }
        }
        //Коллизия закончилась
        playerScript.endCollision();
    }

}

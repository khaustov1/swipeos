package com.NullPointer.swipeos.utils;

import com.NullPointer.swipeos.Objects.Wall;
import com.NullPointer.swipeos.Scripts.PlayerScript;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.List;

/**
 * Created by Khaustov on 17.01.16.
 */
public class CollisionManager {

    public static void checkForCollision(PlayerScript playerScript, LevelLoader levelLoader, Circle playerCircle) {

        // Проверка коллизий
        for (Wall wall : levelLoader.getLevelWalls()) {
            if (Intersector.overlaps(playerCircle, wall.getWallRectangle()) || playerScript.playerTransformComponent.y < 0) {
                //Коллизия началась
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
            }
        }
        if(Intersector.overlaps(playerCircle, levelLoader.getLevelPortal().getPortalCircle()))
        {
                playerScript.playerSpeed.x = 0;
                playerScript.playerSpeed.y = 0;
                playerScript.getGameLoader().nextLevel();
        }
        //Коллизия закончилась
        playerScript.endCollision();
    }

}

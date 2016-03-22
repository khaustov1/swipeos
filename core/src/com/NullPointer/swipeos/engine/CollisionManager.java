package com.NullPointer.swipeos.engine;

import com.NullPointer.swipeos.Objects.Asteroid;
import com.NullPointer.swipeos.Objects.Star;
import com.NullPointer.swipeos.Objects.Wall;
import com.NullPointer.swipeos.Scripts.GameObjectsScripts.PlayerScript;
import com.NullPointer.swipeos.utils.LevelLoader;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Iterator;

/**
 * Created by Khaustov on 17.01.16.
 */
public class CollisionManager {

    public static void checkForCollision(PlayerScript playerScript, LevelLoader levelLoader, Circle playerCircle) {

        // Проверка коллизий
        if(playerScript.playerTransformComponent.y < 0){
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

        }
        else {
            for (Wall wall : levelLoader.getLevelWalls()) {
                if (Intersector.overlaps(playerCircle, wall.getWallRectangle())) {
                    //Коллизия началась
                    playerScript.startCollision();

                    if (wall.isDeadly()) {
                        playerScript.setPlayerСoordinates(playerScript.getGameLoader().getLevelXStartCoordinate(),
                                45f);
                        return;
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

                    break;
                }
            }
            for (Iterator<Star> it = levelLoader.getLevelStars().iterator(); it.hasNext(); ) {
                Star star = it.next();
                if (Intersector.overlaps(playerCircle, star.getStarRectangle())) {
                    star.removeStar();
                }
            }
            for (Iterator<Asteroid> it = levelLoader.getLevelAsteroids().iterator(); it.hasNext(); ) {
                Asteroid asteroid = it.next();
                if (Intersector.overlaps(playerCircle, asteroid.getAsteroidCircle())) {
                    playerScript.setPlayerСoordinates(playerScript.getGameLoader().getLevelXStartCoordinate(),
                            45f);
                }
            }
            if (Intersector.overlaps(playerCircle, levelLoader.getLevelPortal().getPortalCircle())) {
                playerScript.playerSpeed.x = 0;
                playerScript.playerSpeed.y = 0;
                playerScript.getGameLoader().showLevelCompleteWindow();
            }
            //Коллизия закончилась
            playerScript.endCollision();
        }
    }

}

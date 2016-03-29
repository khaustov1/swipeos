package com.NullPointer.swipeos.Scripts.GameObjectsScripts;

import com.NullPointer.swipeos.Objects.Asteroid;
import com.NullPointer.swipeos.Objects.Wall;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.scripts.IScript;

/**
 * Created by Khaustov on 21.02.16.
 */
public class AsteroidScript implements IScript{
    Asteroid asteroid;
    float from, to;
    float movingSpeed = 0f;
    float range = 40f;
    boolean back;
    boolean reverse;

    public AsteroidScript(Asteroid asteroid, float speed, float moveTo, boolean reverse, float range){
        this.asteroid = asteroid;
        this.from = asteroid.getX();
        this.to = moveTo;
        this.reverse = reverse;
        movingSpeed = speed;
        this.range = range;
        if(reverse){
            movingSpeed = -movingSpeed;
        }
    }

    @Override
    public void init(Entity entity) {

    }

    @Override
    public void act(float delta) {
        asteroid.increaseX(movingSpeed * delta);
        asteroid.increaseY(range*delta);
        asteroid.getTransformComponent().rotation += 40*delta;
        if(!reverse) {
            if (!back) {
                if (asteroid.getX() >= to) {
                    movingSpeed = -movingSpeed;
                    range = -range;
                    back = true;
                }
            } else {
                if (asteroid.getX() <= from) {
                    movingSpeed = -movingSpeed;
                    range = -range;
                    back = false;
                }
            }
        }
        else {
            if (!back) {
                if (asteroid.getX() <= to) {
                    movingSpeed = -movingSpeed;
                    range = -range;
                    back = true;
                }
            } else {
                if (asteroid.getX() >= from) {
                    movingSpeed = -movingSpeed;
                    range = -range;
                    back = false;
                }
            }
        }
    }

    @Override
    public void dispose() {

    }
}

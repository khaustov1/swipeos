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

    public AsteroidScript(Asteroid asteroid, float speed){
        this.asteroid = asteroid;
        this.from = asteroid.getX();
        this.to = asteroid.getX() + 700f;
        movingSpeed = speed;
    }

    @Override
    public void init(Entity entity) {

    }

    @Override
    public void act(float delta) {
        asteroid.increaseX(movingSpeed*delta);
        asteroid.increaseY(range*delta);
        //asteroid.getTransformComponent().rotation += 40*delta;
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

    @Override
    public void dispose() {

    }
}

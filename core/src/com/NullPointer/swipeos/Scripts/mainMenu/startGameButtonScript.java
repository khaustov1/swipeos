package com.NullPointer.swipeos.Scripts.mainMenu;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 08.03.16.
 */
public class startGameButtonScript implements IScript{
    private Entity entity;
    TransformComponent transformComponent;
    DimensionsComponent dimensionsComponent;
    private float increment = 1f;
    private float startWidth;
    private float startHeight;
    private Vector2 center = new Vector2(0,0);

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        startHeight = dimensionsComponent.height;
        startWidth = dimensionsComponent.width;
        center.x = transformComponent.x + dimensionsComponent.width/2;
        center.y = transformComponent.y + dimensionsComponent.height/2;
    }

    @Override
    public void act(float delta) {
        if (increment > 0) {
            if (dimensionsComponent.width <= startWidth + 50) {
                dimensionsComponent.width += increment;
                dimensionsComponent.height += increment;
                transformComponent.y = center.y - dimensionsComponent.height/2;
                transformComponent.x = center.x - dimensionsComponent.width/2;
            } else if (dimensionsComponent.width >= startWidth + 50) {
                increment = -increment;
            }
        }
        else {
            if (dimensionsComponent.width >= startWidth) {
                dimensionsComponent.width += increment;
                dimensionsComponent.height += increment;
                transformComponent.y = center.y - dimensionsComponent.height/2;
                transformComponent.x = center.x - dimensionsComponent.width/2;
            } else if (dimensionsComponent.width <= startWidth) {
                increment = -increment;
            }
        }
    }


    @Override
    public void dispose() {

    }
}

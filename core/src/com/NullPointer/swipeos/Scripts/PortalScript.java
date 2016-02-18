package com.NullPointer.swipeos.Scripts;

import com.badlogic.ashley.core.Entity;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 18.02.16.
 */
public class PortalScript implements IScript {
    TransformComponent transformComponent;
    DimensionsComponent dimensionsComponent;
    float portalRadius;
    double centerX, centerY;

    @Override
    public void init(Entity entity) {
        transformComponent = ComponentRetriever.get(entity,TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        portalRadius = (float) (Math.sqrt((dimensionsComponent.width/2)*dimensionsComponent.width/2)
                        + (dimensionsComponent.height/2)*(dimensionsComponent.height/2));
        portalRadius = 10;
        centerX = transformComponent.x+dimensionsComponent.width/2;
        centerY = transformComponent.y-dimensionsComponent.height/2;
        transformComponent.scaleX = 2f;
        transformComponent.scaleY = 2f;
    }

    @Override
    public void act(float delta) {
        //transformComponent.x = (float) (centerX + portalRadius * Math.cos(angle));
        //transformComponent.y = (float) (centerY + portalRadius*Math.sin(angle));
        transformComponent.rotation += 40 *delta;
    }

    @Override
    public void dispose() {

    }
}

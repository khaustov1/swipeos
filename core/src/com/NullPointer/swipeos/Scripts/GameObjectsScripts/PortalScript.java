package com.NullPointer.swipeos.Scripts.GameObjectsScripts;

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

    @Override
    public void init(Entity entity) {
        transformComponent = ComponentRetriever.get(entity,TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        transformComponent.scaleX = 2f;
        transformComponent.scaleY = 2f;
    }

    @Override
    public void act(float delta) {
        transformComponent.rotation -= 40 *delta;
    }

    @Override
    public void dispose() {

    }
}

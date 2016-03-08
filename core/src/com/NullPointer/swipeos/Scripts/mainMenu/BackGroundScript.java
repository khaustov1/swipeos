package com.NullPointer.swipeos.Scripts.mainMenu;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.MainItemComponent;
import com.uwsoft.editor.renderer.components.NodeComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 08.03.16.
 */
public class BackGroundScript implements IScript{
    private Entity entity;
    TransformComponent transformComponent_left;
    DimensionsComponent dimensionsComponent_left;
    TransformComponent transformComponent_right;
    DimensionsComponent dimensionsComponent_right;
    private float increment = 0.10f;
    private float startWidth_left;
    private float startWidth_right;
    private float size = 30f;

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        NodeComponent windowNodeComponent = ComponentRetriever.get(entity, NodeComponent.class);
        for(Entity child :windowNodeComponent.children) {
            MainItemComponent childMainItemComponent = child.getComponent(MainItemComponent.class);
            if (childMainItemComponent.itemIdentifier.equals("bg_right")) {
                dimensionsComponent_right = ComponentRetriever.get(child, DimensionsComponent.class);
                transformComponent_right = ComponentRetriever.get(child, TransformComponent.class);
                startWidth_right = dimensionsComponent_right.width;
            }
            if (childMainItemComponent.itemIdentifier.equals("bg_left")) {
                dimensionsComponent_left = ComponentRetriever.get(child, DimensionsComponent.class);
                transformComponent_left = ComponentRetriever.get(child, TransformComponent.class);
                startWidth_left = dimensionsComponent_left.width;
            }
        }
    }

    @Override
    public void act(float delta) {
        if (increment > 0) {
            if (dimensionsComponent_right.width <= startWidth_right + size) {
                dimensionsComponent_right.width += increment;
                dimensionsComponent_right.height += increment;
                dimensionsComponent_left.width += increment;
                dimensionsComponent_left.height += increment;
                transformComponent_left.x = transformComponent_right.x - dimensionsComponent_left.width;
            } else if (dimensionsComponent_right.width >= startWidth_right + size) {
                increment = -increment;
            }
        }
        else {
            if (dimensionsComponent_right.width >= startWidth_right) {
                dimensionsComponent_right.width += increment;
                dimensionsComponent_right.height += increment;
                dimensionsComponent_left.width += increment;
                dimensionsComponent_left.height += increment;
                transformComponent_left.x = transformComponent_right.x - dimensionsComponent_left.width;
            } else if (dimensionsComponent_right.width <= startWidth_right) {
                increment = -increment;
            }
        }
    }


    @Override
    public void dispose() {

    }
}

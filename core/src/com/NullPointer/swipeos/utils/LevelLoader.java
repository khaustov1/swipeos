package com.NullPointer.swipeos.utils;

import com.NullPointer.swipeos.Objects.Portal;
import com.NullPointer.swipeos.Objects.Wall;
import com.badlogic.ashley.core.Entity;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khaustov on 04.01.16.
 */
public class LevelLoader {
    private List<Wall> walls = new ArrayList<Wall>();
    private Portal portal;
    private ItemWrapper itemWrapper;


    public LevelLoader(ItemWrapper itemWrapper)
    {
        this.itemWrapper = itemWrapper;
    }

    public void loadLevel(int level){
        switch (level){
            case 1:
                loadLevel_1(8, itemWrapper);
                break;
            case 2:
                loadLevel_2(8, itemWrapper);
                break;
            default:

                break;
        }
    }

    public List<Wall> getLevelWalls(){
        return walls;
    }

    public Portal getLevelPortal(){
        return portal;
    }

    private void loadLevel_1(int numberOfBlocks, ItemWrapper itemWrapper){
        for(int i = 0; i < numberOfBlocks; i++){
            Entity blockEntity = itemWrapper.getChild("wall" + i).getEntity();
            TransformComponent objectTransformComponent = blockEntity.getComponent(TransformComponent.class);
            DimensionsComponent objectDimensionComponent = blockEntity.getComponent(DimensionsComponent.class);
            switch (i){
                case 5:
                    objectDimensionComponent.width = 350;
                    break;
                case 6:
                    objectDimensionComponent.height = objectDimensionComponent.height * 100;
                    break;
                case 7:
                    objectDimensionComponent.height = objectDimensionComponent.height * 100;
                    break;
                default:
                    break;
            }
            walls.add(new Wall(
                    objectTransformComponent.x,
                    objectTransformComponent.y,
                    objectDimensionComponent.width,
                    objectDimensionComponent.height
            ));
        }
        Entity portalEntity = itemWrapper.getChild("portal1").getEntity();
        TransformComponent portalTransformComponent = portalEntity.getComponent(TransformComponent.class);
        DimensionsComponent portalDimensionComponent = portalEntity.getComponent(DimensionsComponent.class);
        float portalRadius = portalDimensionComponent.width/2;
        portal = new Portal(portalTransformComponent.x + portalRadius,
                portalTransformComponent.y + portalRadius,
                portalRadius);
    }

    private void loadLevel_2(int numberOfBlocks, ItemWrapper itemWrapper){
        List<com.NullPointer.swipeos.Objects.GameObject> objectList = new ArrayList<com.NullPointer.swipeos.Objects.GameObject>();
        for(int i = 0; i < numberOfBlocks; i++){
            Entity blockEntity = itemWrapper.getChild("wall2_" + i).getEntity();
            TransformComponent objectTransformComponent = blockEntity.getComponent(TransformComponent.class);
            DimensionsComponent objectDimensionComponent = blockEntity.getComponent(DimensionsComponent.class);
            switch (i){
                case 5:
                    objectDimensionComponent.width = 350;
                    break;
                case 6:
                    objectDimensionComponent.height = objectDimensionComponent.height * 100;
                    break;
                case 7:
                    objectDimensionComponent.height = objectDimensionComponent.height * 100;
                    break;
                default:
                    break;
            }
            objectList.add(new Wall(
                    objectTransformComponent.x,
                    objectTransformComponent.y,
                    objectDimensionComponent.width,
                    objectDimensionComponent.height
            ));
        }

        Entity portalEntity = itemWrapper.getChild("portal2").getEntity();
        TransformComponent portalTransformComponent = portalEntity.getComponent(TransformComponent.class);
        DimensionsComponent portalDimensionComponent = portalEntity.getComponent(DimensionsComponent.class);
        float portalRadius = portalDimensionComponent.width/2;
        portal = new Portal(portalTransformComponent.x + portalRadius,
                portalTransformComponent.y + portalRadius,
                portalRadius);
    }
}

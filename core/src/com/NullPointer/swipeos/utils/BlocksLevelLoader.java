package com.NullPointer.swipeos.utils;

import com.badlogic.ashley.core.Entity;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khaustov on 04.01.16.
 */
public class BlocksLevelLoader {
    public BlocksLevelLoader()
    {

    }

    public static List<com.NullPointer.swipeos.Objects.GameObject> getLevelBlocks(ItemWrapper itemWrapper, int level){
        switch (level){
            case 1:
                return loadBlocksLevel_1(8, itemWrapper);
            case 2:
                return loadBlocksLevel_2(8, itemWrapper);
            default:
                return null;
        }
    }

    private static List<com.NullPointer.swipeos.Objects.GameObject> loadBlocksLevel_1(int numberOfBlocks, ItemWrapper itemWrapper){
        List<com.NullPointer.swipeos.Objects.GameObject> objectList = new ArrayList<com.NullPointer.swipeos.Objects.GameObject>();
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
            objectList.add(new com.NullPointer.swipeos.Objects.GameObject(
                    objectTransformComponent.x,
                    objectTransformComponent.y,
                    objectDimensionComponent.width,
                    objectDimensionComponent.height
            ));
        }

        Entity portalEntity = itemWrapper.getChild("portal1").getEntity();
        TransformComponent portalTransformComponent = portalEntity.getComponent(TransformComponent.class);
        DimensionsComponent portalDimensionComponent = portalEntity.getComponent(DimensionsComponent.class);
        com.NullPointer.swipeos.Objects.GameObject portal = new com.NullPointer.swipeos.Objects.GameObject(
                portalTransformComponent.x,
                portalTransformComponent.y,
                portalDimensionComponent.width,
                portalDimensionComponent.height
        );
        portal.setIsPortal(true);
        objectList.add(portal);

        return  objectList;
    }

    private static List<com.NullPointer.swipeos.Objects.GameObject> loadBlocksLevel_2(int numberOfBlocks, ItemWrapper itemWrapper){
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
            objectList.add(new com.NullPointer.swipeos.Objects.GameObject(
                    objectTransformComponent.x,
                    objectTransformComponent.y,
                    objectDimensionComponent.width,
                    objectDimensionComponent.height
            ));
        }

        Entity portalEntity = itemWrapper.getChild("portal2").getEntity();
        TransformComponent portalTransformComponent = portalEntity.getComponent(TransformComponent.class);
        DimensionsComponent portalDimensionComponent = portalEntity.getComponent(DimensionsComponent.class);
        com.NullPointer.swipeos.Objects.GameObject portal = new com.NullPointer.swipeos.Objects.GameObject(
                portalTransformComponent.x,
                portalTransformComponent.y,
                portalDimensionComponent.width,
                portalDimensionComponent.height
        );
        portal.setIsPortal(true);
        objectList.add(portal);

        return  objectList;
    }
}

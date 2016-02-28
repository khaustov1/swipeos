package com.NullPointer.swipeos.utils;

import com.NullPointer.swipeos.Game;
import com.NullPointer.swipeos.Objects.Portal;
import com.NullPointer.swipeos.Objects.Star;
import com.NullPointer.swipeos.Objects.Wall;
import com.NullPointer.swipeos.Scripts.NextLevelButtonScript;
import com.NullPointer.swipeos.Scripts.PortalScript;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.MainItemComponent;
import com.uwsoft.editor.renderer.components.NodeComponent;
import com.uwsoft.editor.renderer.components.ScriptComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.additional.ButtonComponent;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;
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
    private GameLoader gameLoader;
    private Wall stopWall;
    private List<Star> stars = new ArrayList<Star>();


    public LevelLoader(ItemWrapper itemWrapper, GameLoader gameLoader)
    {
        this.itemWrapper = itemWrapper;
        this.gameLoader = gameLoader;
    }

    public void loadLevel(int level){
        switch (level){
            case 1:
                loadLevel_1();
                break;
            case 2:
                loadLevel_2();
                break;
            case 3:
                loadLevel_3();
                break;
            case 4:
                loadLevel_4();
                break;
            default:

                break;
        }
    }

    private void loadLevel_1(){
        int level = 1;
        fillLevelWalls(2, level);
        fillLevelPortal(level);
        setLevelStopWall(level);
        setLevelStartCoordinate(level);
    }

    private void loadLevel_2(){
        int level = 2;
        fillLevelWalls(2, level);
        fillLevelStars(3, level);
        fillLevelPortal(level);
        setLevelStopWall(level);
        setLevelStartCoordinate(level);
    }

    private void loadLevel_3(){
        int level = 3;
        fillLevelWalls(5, level);
        fillLevelStars(3, level);
        fillLevelPortal(level);
        setLevelStopWall(level);
        setLevelStartCoordinate(level);
    }

    private void loadLevel_4(){
        int level = 4;
        fillLevelWalls(9, level);
        fillLevelStars(3, level);
        fillLevelPortal(level);
        setLevelStopWall(level);
        setLevelStartCoordinate(level);
    }

    private void fillLevelStars(int starsCount, int level){
        stars.clear();
        for(int i = 0; i < starsCount; i++){
            Entity starEntity = itemWrapper.getChild("star"+level+"_" + i).getEntity();
            stars.add(new Star(starEntity));
        }
    }

    private void fillLevelWalls(int wallsCount, int level){
        walls.clear();
        for(int i = 0; i < wallsCount; i++){
            Entity blockEntity = itemWrapper.getChild("wall"+level+"_" + i).getEntity();
            walls.add(new Wall(blockEntity));
        }
    }

    private void fillLevelPortal(int level){
        Entity portalEntity = itemWrapper.getChild("portal"+level).getEntity();
        TransformComponent portalTransformComponent = portalEntity.getComponent(TransformComponent.class);
        DimensionsComponent portalDimensionComponent = portalEntity.getComponent(DimensionsComponent.class);
        float portalRadius = portalDimensionComponent.width/2;
        portal = new Portal(portalTransformComponent.x + portalRadius,
                portalTransformComponent.y + portalRadius,
                portalRadius);

        itemWrapper.getChild("portal"+level).addScript(new PortalScript());
    }

    private void setLevelStopWall(int level){
        stopWall = new Wall(itemWrapper.getChild("stopWall_"+level).getEntity());
        walls.add(stopWall);
    }

    public float getLevelStopY(){
        if(stopWall != null) {
            return stopWall.getY() - 323f;
        }
        else {
            return 0f;
        }
    }

    public void showLevelCompleteWindow(){
        Entity windowEntity = itemWrapper.getChild("levelComplete").getEntity();
        TransformComponent windowTransformComponent = ComponentRetriever.get(windowEntity, TransformComponent.class);
        DimensionsComponent windowDimensionComponent = ComponentRetriever.get(windowEntity, DimensionsComponent.class);
        Vector3 cameraCoordinates = gameLoader.getCameraCoordinates();
        windowTransformComponent.x = cameraCoordinates.x - windowDimensionComponent.width/2;
        windowTransformComponent.y = cameraCoordinates.y - windowDimensionComponent.height/2;
        initLevelCompleteWindow();
    }

    public void initLevelCompleteWindow(){
        gameLoader.setPaused(true);
        Entity windowEntity = itemWrapper.getChild("levelComplete").getEntity();
        TransformComponent windowTransformComponent = ComponentRetriever.get(windowEntity, TransformComponent.class);
        NodeComponent windowNodeComponent = ComponentRetriever.get(windowEntity, NodeComponent.class);
        for(Entity child :windowNodeComponent.children){
            MainItemComponent childMainItemComponent = child.getComponent(MainItemComponent.class);
            if(childMainItemComponent.itemIdentifier.equals("NextLevelButton")){
                ScriptComponent scriptComponent = new ScriptComponent();
                //Удалить скрипт, если есть
                TransformComponent transformComponent = ComponentRetriever.get(child, TransformComponent.class);
                DimensionsComponent dimensionsComponent = ComponentRetriever.get(child, DimensionsComponent.class);
                Rectangle buttonRectangle = new Rectangle(transformComponent.x+windowTransformComponent.x,
                        transformComponent.y + windowTransformComponent.y,
                        dimensionsComponent.width, dimensionsComponent.height);
                scriptComponent.addScript((new NextLevelButtonScript(gameLoader, buttonRectangle)));
                child.add(scriptComponent);
            }
        }
    }

    private void setLevelStartCoordinate(int level){
        TransformComponent transformComponent = ComponentRetriever.get(itemWrapper.getChild("level"+level).getEntity(),
                TransformComponent.class);
        gameLoader.setLevelXStartCoordinate(transformComponent.x+180f);
    }

    public List<Wall> getLevelWalls(){
        return walls;
    }

    public List<Star> getLevelStars(){
        return stars;
    }

    public Portal getLevelPortal(){
        return portal;
    }
}

package com.NullPointer.swipeos.utils;

import com.NullPointer.swipeos.Game;
import com.NullPointer.swipeos.Objects.Portal;
import com.NullPointer.swipeos.Objects.Star;
import com.NullPointer.swipeos.Objects.Wall;
import com.NullPointer.swipeos.Scripts.PortalScript;
import com.badlogic.ashley.core.Entity;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
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
        fillLevelWalls(2, level);
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
            return stopWall.getY() - 320f;
        }
        else {
            return 0f;
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

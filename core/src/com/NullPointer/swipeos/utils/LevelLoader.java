package com.NullPointer.swipeos.utils;

import com.NullPointer.swipeos.Objects.Asteroid;
import com.NullPointer.swipeos.Objects.GameObject;
import com.NullPointer.swipeos.Objects.Portal;
import com.NullPointer.swipeos.Objects.Star;
import com.NullPointer.swipeos.Objects.Wall;
import com.NullPointer.swipeos.Scripts.GameObjectsScripts.AsteroidScript;
import com.NullPointer.swipeos.Scripts.GameObjectsScripts.MovingWallScript;
import com.NullPointer.swipeos.Scripts.NextLevelButtonScript;
import com.NullPointer.swipeos.Scripts.GameObjectsScripts.PortalScript;
import com.NullPointer.swipeos.Scripts.mainMenu.BackGroundScript;
import com.NullPointer.swipeos.Scripts.mainMenu.PlayButtonScript;
import com.NullPointer.swipeos.Scripts.mainMenu.StartStageScript;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.MainItemComponent;
import com.uwsoft.editor.renderer.components.NodeComponent;
import com.uwsoft.editor.renderer.components.ScriptComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sun.rmi.runtime.Log;

/**
 * Created by Khaustov on 04.01.16.
 */
public class LevelLoader {
    private List<Wall> walls = new ArrayList<Wall>();
    private Portal portal;
    private com.NullPointer.swipeos.engine.ItemWrapper itemWrapper;
    private GameLoader gameLoader;
    private Wall stopWall;
    private List<Star> stars = new ArrayList<Star>();
    private Music menuMusic;
    private List<Asteroid> asteroids = new ArrayList<Asteroid>();
    private List<GameObject> gameObjectList = new ArrayList<GameObject>();


    public LevelLoader(com.NullPointer.swipeos.engine.ItemWrapper itemWrapper, GameLoader gameLoader)
    {
        this.itemWrapper = itemWrapper;
        this.gameLoader = gameLoader;
    }

    public void loadMainScreen(){
        Entity playButtonEntity = itemWrapper.getChild("playButton").getEntity();
        TransformComponent playButtonTransformComponent = playButtonEntity.getComponent(TransformComponent.class);
        DimensionsComponent playButtonDimensionComponent = playButtonEntity.getComponent(DimensionsComponent.class);
        Rectangle playButtonRectangle = new Rectangle(playButtonTransformComponent.x, playButtonTransformComponent.y,
                playButtonDimensionComponent.width, playButtonDimensionComponent.height) ;

        playButtonTransformComponent.scaleY = 1.5f;
        playButtonTransformComponent.scaleX = 1.5f;

        Entity dustEntity = itemWrapper.getChild("dust").getEntity();
        TransformComponent dustTransformComponent = dustEntity.getComponent(TransformComponent.class);

        dustTransformComponent.scaleY = 1.5f;
        dustTransformComponent.scaleX = 1.5f;

        itemWrapper.getChild("playButton").addScript(new PlayButtonScript(gameLoader, playButtonRectangle, playButtonEntity));
        itemWrapper.getChild("bg").addScript(new BackGroundScript());
        itemWrapper.getChild("bg1").addScript(new BackGroundScript());
        Entity loadStage1Entity = itemWrapper.getChild("stage1Button").getEntity();
        itemWrapper.getChild("stage1Button").addScript(new StartStageScript(gameLoader, loadStage1Entity, 1));
        Entity loadStage2Entity = itemWrapper.getChild("stage2Button").getEntity();
        itemWrapper.getChild("stage2Button").addScript(new StartStageScript(gameLoader, loadStage2Entity, 2));
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Calm.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();
    }

    public void loadLevel(int level){
        fillLevelContent(level);
        clearRender();
    }

    private void fillLevelContent(int level){
        gameObjectList.clear();
        fillLevelWalls(level);
        fillLevelStars(level);
        fillLevelPortal(level);
        fillLevelAsteroids(level);
        fillLevelMovingWalls(level);
        setLevelStopWall(level);
        setLevelStartCoordinate(level);
        gameObjectList.addAll(asteroids);
        gameObjectList.addAll(stars);
        gameObjectList.addAll(walls);
        gameObjectList.add(portal);
    }

    private void fillLevelStars(int level){
        stars.clear();
        List<Entity> starList = itemWrapper.getChildrenContains("star"+level+"_");
        for(Entity star : starList){
            stars.add(new Star(star));
        }
    }

    private void clearRender(){
        float levelStartX = stopWall.getWallRectangle().getX();
        float levelEndX = levelStartX + stopWall.getWallRectangle().width;

        for(Entity stageEntity : gameLoader.getGame().mainSceneLoader.getEngine().getEntities()){
            TransformComponent entityTransformComponent = stageEntity.getComponent(TransformComponent.class);
            MainItemComponent entityMainItemComponent = stageEntity.getComponent(MainItemComponent.class);
            if(entityTransformComponent.x > levelStartX && entityTransformComponent.x < levelEndX){
                entityMainItemComponent.visible = true;
            }
            else {
                entityMainItemComponent.visible = false;
            }
        }
    }

    private void fillLevelMovingWalls(int level){
        List<Entity> movingWallsList = itemWrapper.getChildrenContains("moving"+level);
        for (Entity movingWall : movingWallsList){
            try {
                Wall currentWall = new Wall(movingWall);
                ScriptComponent scriptComponent = new ScriptComponent();
                MainItemComponent mainItemComponent = movingWall.getComponent(MainItemComponent.class);
                String info = mainItemComponent.itemIdentifier.substring(
                        mainItemComponent.itemIdentifier.indexOf(":") + 1
                );
                char axis = info.charAt(0);
                String[] fromAndToAsString = info.substring(2).split("[\\t\\u007c]");
                int from = Integer.parseInt(fromAndToAsString[0]);
                int to = Integer.parseInt(fromAndToAsString[1]);
                int speed = Integer.parseInt(fromAndToAsString[2]);
                scriptComponent.addScript(new MovingWallScript(currentWall,
                        axis,
                        currentWall.getX() + from,
                        currentWall.getX() + to,
                        speed));
                currentWall.setIsDeadly(true);
                movingWall.add(scriptComponent);
                walls.add(currentWall);
            }
            catch (Exception e){
                e.toString();
            }
        }
    }

    private void fillLevelWalls(int level){
        walls.clear();
        List<Entity> wallsList = itemWrapper.getChildrenContains("wall"+level+"_");
        for(Entity wall : wallsList){
            walls.add(new Wall(wall));
        }
    }

    private void fillLevelAsteroids(int level){
        asteroids.clear();
        float range;
        List<Entity> asteroidList = itemWrapper.getChildrenContains("asteroid"+level);
        for(Entity asteroid : asteroidList){
            Asteroid asteroidObject = new Asteroid(asteroid);
            ScriptComponent scriptComponent = new ScriptComponent();
            MainItemComponent mainItemComponent = asteroid.getComponent(MainItemComponent.class);
            String itemIdentifier = mainItemComponent.itemIdentifier;
            try {
                range = Float.parseFloat(itemIdentifier.substring(itemIdentifier.indexOf(":") + 1));
            }
            catch (Exception e){
                range = 40f;
            }
            if(itemIdentifier.contains("asteroid"+level+"_r")){
                scriptComponent.addScript(new AsteroidScript(asteroidObject,150f,
                        asteroidObject.getX() - 300f,
                        true, range));
            }
            else {
                scriptComponent.addScript(new AsteroidScript(asteroidObject, 150f,
                        asteroidObject.getX() + 300f,
                        false, range));
            }
            asteroid.add(scriptComponent);
            asteroids.add(asteroidObject);
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

    public List<GameObject> getGameObjectList(){
        return gameObjectList;
    }
}

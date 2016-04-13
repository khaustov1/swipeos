package com.NullPointer.swipeos.Scripts.mainMenu;

import com.NullPointer.swipeos.utils.GameLoader;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.label.LabelComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Khaustov on 06.03.16.
 */
public class PlayButtonScript implements IScript{
    private GameLoader gameLoader;
    private Entity entity;
    private Rectangle buttonRectangle;
    private int stage;
    private boolean isResizing, isRotating;
    TransformComponent transformComponent;
    DimensionsComponent dimensionsComponent;
    private float increment = 0.25f;
    private float startWidth;

    private Vector2 center = new Vector2(0,0);
    private float size = 10f;
    float moveTo = 0f;


    public PlayButtonScript(GameLoader gameLoader,  Entity entity, float moveTo,
                            int stage, boolean resizing, boolean isRotating){
        this.gameLoader = gameLoader;
        this.entity = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);

        this.buttonRectangle = new Rectangle(transformComponent.x, transformComponent.y,
                dimensionsComponent.width, dimensionsComponent.height);
        this.moveTo = moveTo;
        this.stage = stage;
        this.isResizing = resizing;
        this.isRotating = isRotating;

        startWidth = dimensionsComponent.width;
        center.x = transformComponent.x + dimensionsComponent.width/2;
        center.y = transformComponent.y + dimensionsComponent.height/2;
    }

    @Override
    public void init(Entity entity) {
    }

    @Override
    public void act(float delta) {
        if(isRotating) {
            transformComponent.rotation += 20 * delta;
        }
        if(isResizing){
            resize();
        }
        if(Gdx.input.justTouched()){
            Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            gameLoader.getCameraManager().getCamera().unproject(pos);
            if(buttonRectangle.contains(pos.x, pos.y)){
                for(int i = 1; i < 11; i++){
                    Entity labelEntity = gameLoader.getGame().mainItemWrapper.getChild("label"+i).getEntity();
                    LabelComponent labelComponent = labelEntity.getComponent(LabelComponent.class);
                    int iTemp = i;
                    switch (stage){
                        case 1:
                            break;
                        case 2:
                            iTemp+=10;
                            break;
                        case 3:
                            iTemp+=20;
                    }
                    labelComponent.setText(""+iTemp);
                }
                gameLoader.getCameraManager().setCameraCoords(moveTo,360f);
                gameLoader.getLevelLoader().setSelectedStage(stage);
            }
        }
    }


    private void resize(){
        if (increment > 0) {
            if (dimensionsComponent.width <= startWidth + size) {
                dimensionsComponent.width += increment;
                dimensionsComponent.height += increment;
                transformComponent.y = center.y - dimensionsComponent.height/2;
                transformComponent.x = center.x - dimensionsComponent.width/2;
            } else if (dimensionsComponent.width >= startWidth + size) {
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

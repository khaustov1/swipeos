package com.NullPointer.swipeos.utils;

import com.badlogic.ashley.core.Entity;
import com.uwsoft.editor.renderer.scripts.IScript;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khaustov on 13.04.16.
 */
public class LevelScriptManager implements IScript {
    List<IScript> scriptList = new ArrayList<IScript>();

    @Override
    public void init(Entity entity) {

    }

    @Override
    public void act(float delta) {
        for(IScript script : scriptList){
            script.act(delta);
        }
    }

    @Override
    public void dispose() {
        scriptList.clear();
    }

    public void addScript(IScript script){
        scriptList.add(script);
    }

    public void clearScriptList(){
        scriptList.clear();
    }
}

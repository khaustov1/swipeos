package com.NullPointer.swipeos;

import com.NullPointer.swipeos.utils.GameLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

public class Game extends com.badlogic.gdx.Game {
	public  Viewport       mainViewPort;
    public  SceneLoader    mainSceneLoader;
    public  ItemWrapper    mainItemWrapper;

    private GameLoader gameLoader;
	
	@Override
	public void create () {
		gameLoader = new GameLoader(this);
		gameLoader.initialize();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainSceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());

        setCamera();
	}

    private void setCamera(){
        if(gameLoader.getPlayerY() > 210f) //Экспериментально рассчитанное число
        ((OrthographicCamera) mainViewPort.getCamera()).position.set(180f, gameLoader.getPlayerY() + 110f, 0);
    }

	public void setCameraCoords(float x, float y){
		((OrthographicCamera) mainViewPort.getCamera()).position.set(x, y, 0);
	}
}

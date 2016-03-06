package com.NullPointer.swipeos;

import com.NullPointer.swipeos.utils.GameLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

public class Game extends com.badlogic.gdx.Game {
	public  Viewport       mainViewPort;
    public com.NullPointer.swipeos.utils.SceneLoader mainSceneLoader;
    public  ItemWrapper    mainItemWrapper;

    private GameLoader gameLoader;
    private Game game;
    public static OnLoadListener onLoadListener;

    public Game(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }


    @Override
	public void create () {
        game = this;
        gameLoader = new GameLoader(game);
        gameLoader.initialize();
    }

    @Override
    public void render () {
        if(mainSceneLoader.getEngine().getEntities().size() == 0 && gameLoader.isNeedToLoadScene){
            gameLoader.loadCurrentScene();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainSceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());
        setCamera();
    }

    private void setCamera(){
		if(!(mainViewPort.getCamera().position.y >= gameLoader.getCameraStopY())) {
			if (gameLoader.getPlayerY() > 210f) //Экспериментально рассчитанное число
				((OrthographicCamera) mainViewPort.getCamera()).position.set(gameLoader.getLevelXStartCoordinate(), gameLoader.getPlayerY() + 110f, 0);
		}
    }

	public void setCameraCoords(float x, float y){
		((OrthographicCamera) mainViewPort.getCamera()).position.set(x, y, 0);
	}

}

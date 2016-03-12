package com.NullPointer.swipeos;

import com.NullPointer.swipeos.utils.CameraManager;
import com.NullPointer.swipeos.utils.GameLoader;
import com.NullPointer.swipeos.utils.ItemWrapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game extends com.badlogic.gdx.Game {
	public  Viewport       mainViewPort;
    public com.NullPointer.swipeos.utils.SceneLoader mainSceneLoader;
    public ItemWrapper mainItemWrapper;
    public CameraManager cameraManager;

    private GameLoader gameLoader;
    private Game game;
    public static OnLoadListener onLoadListener;
    private float oldCameraY = 320f;

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
    }

}

package com.NullPointer.swipeos.android;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.NullPointer.swipeos.OnLoadListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.NullPointer.swipeos.Game;

import java.io.IOException;

public class AndroidLauncher extends AndroidApplication {
	private FrameLayout baseLayout;
	private ImageView loadingImage;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new Game(), config);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		baseLayout = new FrameLayout(this);
		FrameLayout.LayoutParams frameParams =  new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		baseLayout.setLayoutParams(frameParams);

		createGameView(config);
		createLoadingImage();

		setContentView(baseLayout);
	}

	private void createLoadingImage()
	{
		try
		{
			loadingImage = new ImageView(this);
			Drawable drawable = Drawable.createFromStream(getAssets().open("fone_front_2.png"), null);
			loadingImage.setImageDrawable(drawable);
			FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			loadingImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			loadingImage.setLayoutParams(imageParams);
			baseLayout.addView(loadingImage);
		} catch (IOException e)
		{
			//No Image for you
		}
	}

	private void createGameView(AndroidApplicationConfiguration cfg)
	{
		Game gameObject = new Game(new OnLoadListener()
		{
			@Override
			public void onLoad()
			{
				//Very important to run this on the UI thread or it will crash
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						baseLayout.removeView(loadingImage);
					}
				});
			}

		});
        View gameView = initializeForView(gameObject, cfg);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
		gameView.setLayoutParams(params);
		baseLayout.addView(gameView);
	}

}

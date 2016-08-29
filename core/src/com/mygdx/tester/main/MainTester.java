package com.mygdx.tester.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.tester.handlers.StateManager;

import box2dLight.RayHandler;

import static com.mygdx.tester.handlers.B2Dhelpers.PPM;


public class MainTester implements ApplicationListener {

	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
//	public static final float STEP = 1 / 60f;

	//ray handler


//	private float accum;
	
	private SpriteBatch spriteBatch;
	private OrthographicCamera cam;
	
	private StateManager stateManager;
	
	public void create() {
		spriteBatch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		stateManager = new StateManager(this);

	}
	
	public void render() {
		stateManager.update(Gdx.graphics.getDeltaTime());
		stateManager.render();
	}
	
	public void dispose() {spriteBatch.dispose();}
	
	public SpriteBatch getSpriteBatch() { return spriteBatch; }
	public OrthographicCamera getCamera() { return cam; }
	
	public void resize(int w, int h) {}
	public void pause() {}
	public void resume() {}
	
}

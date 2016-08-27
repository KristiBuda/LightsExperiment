package com.mygdx.tester.states;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.tester.handlers.StateManager;
import com.mygdx.tester.main.MainTester;


public abstract class ApplicationState extends InputAdapter {
	
	protected StateManager stateManager;
	protected MainTester mainTester;
	
	protected SpriteBatch spriteBatch;
	protected OrthographicCamera cam;
	
	protected ApplicationState(StateManager stateManager) {
		this.stateManager = stateManager;
		mainTester = stateManager.getMainTester();
		spriteBatch = mainTester.getSpriteBatch();
		cam = mainTester.getCamera();
	}

	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
}










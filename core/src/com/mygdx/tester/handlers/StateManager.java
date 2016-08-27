package com.mygdx.tester.handlers;

import com.mygdx.tester.main.MainTester;
import com.mygdx.tester.states.ApplicationState;
import com.mygdx.tester.states.Factory;
import java.util.Stack;



public class StateManager {
	
	private MainTester mainTester;
	private Stack<ApplicationState> gameStates;
	public static final int PLAY = 912837;
	
	public StateManager(MainTester mainTester) {
		this.mainTester = mainTester;
		gameStates = new Stack<ApplicationState>();
		pushState(PLAY);
	}
	
	public MainTester getMainTester() {

		return mainTester;
	}

	public void update(float dt) {

		gameStates.peek().update(dt);
	}

	public void render() {

		gameStates.peek().render();
	}
	
	private ApplicationState getState(int state) {
		if(state == PLAY) return new Factory(this);
		return null;
	}
	
	public void setState(int state) {
		popState();
		pushState(state);
	}
	
	public void pushState(int state) {
		gameStates.push(getState(state));
	}
	
	public void popState() {
		ApplicationState g = gameStates.pop();
		g.dispose();
	}
	
}
















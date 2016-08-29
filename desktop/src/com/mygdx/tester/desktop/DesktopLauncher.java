package com.mygdx.tester.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.tester.main.MainTester;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = MainTester.V_WIDTH * MainTester.SCALE;
        config.height = MainTester.V_HEIGHT * MainTester.SCALE;
        config.useGL30 = false;

        new LwjglApplication(new MainTester(), config);
    }
}

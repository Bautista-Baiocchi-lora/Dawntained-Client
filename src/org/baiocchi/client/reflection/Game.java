package org.baiocchi.client.reflection;

import java.applet.Applet;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;

public class Game {

	private GameLoader loader;

	public Game() {
		try {
			loader = new GameLoader();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public GameLoader getLoader() {
		return loader;
	}

	public Applet getApplet() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<?> client = loader.loadClass("Client");
		Applet applet = (Applet) client.newInstance();
		/*Class<?> initClass = loader.loadClass("ah");
		Method initMethod = initClass.getDeclaredMethod("a", null);
		initMethod.setAccessible(true);
		initMethod.invoke(null, new Object[] {});*/
		applet.setStub(new GameStub());
		return applet;
	}

}

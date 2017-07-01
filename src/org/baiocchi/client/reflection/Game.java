package org.baiocchi.client.reflection;

import java.applet.Applet;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

	private void invokeInit() {
		System.setProperty("java.net.preferIPv4Stack", "true");
		try {
			Class<?> p = loader.loadClass("p");
			Field b = p.getDeclaredField("b");
			b.setAccessible(true);
			Field c = p.getDeclaredField("c");
			c.setAccessible(true);
			if (false) {
				b.set(null, true);
				c.set(null, false);
			} else {
				b.set(null, false);
				c.set(null, true);
			}
			Class<?> client = loader.loadClass("Client");
			Field iV = client.getField("iV");
			iV.setAccessible(true);
			Method U = client.getMethod("U");
			boolean uReturn = (boolean) U.invoke(null);
			iV.set(null, uReturn);
			Field iJ = client.getDeclaredField("iJ");
			iJ.setAccessible(true);
			iJ.set(null, (String) System.getProperty("user.home"));

		} catch (NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException
				| IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	public Applet getApplet() {
		invokeInit();
		return null;
	}

}

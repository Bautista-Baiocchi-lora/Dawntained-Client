package org.baiocchi.client.reflection;

import java.applet.Applet;
import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

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

	public Applet getApplet() {
		System.setProperty("java.net.preferIPv4Stack", "true");
		try {
			Class<?> p = loader.loadClass("p");
			Field b = p.getDeclaredField("b");
			b.setAccessible(true);
			Field c = p.getDeclaredField("c");
			c.setAccessible(true);
			b.setBoolean(null, false);
			c.setBoolean(null, true);

			Class<?> client = loader.loadClass("Client");
			Field iV = client.getField("iV");
			iV.setAccessible(true);
			iV.setBoolean(null, false);

			Field iJ = client.getDeclaredField("iJ");
			iJ.setAccessible(true);
			iJ.set(null, (String) System.getProperty("user.home"));

			Class<?> ah = loader.loadClass("ah");
			Method a = ah.getDeclaredMethods()[0];
			a.setAccessible(true);
			a.invoke(null);

			Applet applet = (Applet) client.newInstance();

			Method B = client.getDeclaredMethod("B");
			int height = (int) B.invoke(applet);

			Method C = client.getDeclaredMethod("C");
			int width = (int) C.invoke(applet);
			
			/*Field je = client.getSuperclass().getDeclaredField("je");
			je.setAccessible(true);
			je.setInt(applet, width);

			Field jf = client.getSuperclass().getDeclaredField("jf");
			jf.setAccessible(true);
			jf.setInt(applet, height);

			Method n = client.getSuperclass().getDeclaredMethod("n");
			n.setAccessible(true);
			Component component = (Component) n.invoke(applet);
			Field jg = client.getSuperclass().getDeclaredField("jg");
			jg.setAccessible(true);
			jg.set(applet, component.getGraphics());

			Field jh = client.getSuperclass().getDeclaredField("jh");
			jh.setAccessible(true);
			Method A = client.getDeclaredMethod("a", int.class, int.class, Component.class);
			A.setAccessible(true);
			// invoking method A throws an exception
			jh.set(applet, A.invoke(applet, width, height, component));*/

			Method g = client.getSuperclass().getDeclaredMethod("g", int.class, int.class);
			g.setAccessible(true);
			g.invoke(applet, height, width);

			return applet;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
				| InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.getCause().printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}

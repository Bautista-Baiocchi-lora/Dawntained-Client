package org.baiocchi.client.reflection;

import java.applet.Applet;
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

	private String E() {
		try {
			InetAddress localInetAddress = InetAddress.getLocalHost();
			NetworkInterface localNetworkInterface = NetworkInterface.getByInetAddress(localInetAddress);
			byte[] arrayOfByte = localNetworkInterface.getHardwareAddress();
			if (arrayOfByte == null) {
				return "INVALID1";
			}
			StringBuilder localStringBuilder = new StringBuilder();
			for (int i1 = 0; i1 < arrayOfByte.length; i1++) {
				localStringBuilder.append(String.format("%02X%s",
						new Object[] { Byte.valueOf(arrayOfByte[i1]), i1 < arrayOfByte.length - 1 ? "-" : "" }));
			}
			return localStringBuilder.toString();
		} catch (SocketException | UnknownHostException localSocketException) {
			localSocketException.printStackTrace();
		}
		return "INVALID2";
	}

	public Applet getApplet() {
		System.setProperty("java.net.preferIPv4Stack", "true");
		try {
			Class<?> w = loader.loadClass("w");
			Method ar = w.getDeclaredMethod("a");
			ar.setAccessible(true);
			String ir = (String) ar.invoke(null);
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
			Field dU = client.getField("dU");
			Field dV = client.getField("dV");
			Field dX = client.getField("dX");
			dU.setInt(null, 10);
			dV.setInt(null, 0);
			dX.setBoolean(null, true);
			Class<?> cp = loader.loadClass("cp");
			Field d = cp.getDeclaredField("d");
			d.setInt(null, 32);
			Method a = cp.getMethod("a", InetAddress.class);
			a.invoke(null, InetAddress.getLocalHost());
			Method E = client.getDeclaredMethod("E");
			E.setAccessible(true);

			Field iq = client.getDeclaredField("iq");
			System.out.println(E());
			Field irr = client.getDeclaredField("ir");
			irr.setAccessible(true);
			irr.set(null, ir);
			return (Applet) client.newInstance();
		} catch (NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException
				| IllegalAccessException | InvocationTargetException | UnknownHostException
				| InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

}

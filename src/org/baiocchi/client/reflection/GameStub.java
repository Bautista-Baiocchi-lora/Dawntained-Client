package org.baiocchi.client.reflection;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.MalformedURLException;
import java.net.URL;

public class GameStub implements AppletStub {

	public GameStub() {
	}

	@Override
	public void appletResize(int arg0, int arg1) {

	}

	@Override
	public AppletContext getAppletContext() {
		return null;
	}

	@Override
	public URL getCodeBase() {
		try {
			return new URL("http://176.31.249.209/cache");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public URL getDocumentBase() {
		return getCodeBase();
	}

	@Override
	public String getParameter(String name) {
		return "";
	}

	@Override
	public boolean isActive() {
		return true;
	}

}

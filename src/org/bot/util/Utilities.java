package org.bot.util;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Created by Ethan on 7/3/2017.
 */
public class Utilities {
	public static Image getLocalImage(String file) {
		try {

			return new ImageIcon(Utilities.class.getClass().getResource(file)).getImage();
		} catch (NullPointerException e) {
			System.out.println("[Error] Cannot load this Image " + file);
			e.printStackTrace();
			return null;
		}
	}

	public static String capitalize(String text) {
		if (text == null || text.length() == 0) {
			return null;
		}
		String[] arr = text.split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
		}
		return sb.toString().trim();
	}
}

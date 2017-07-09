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
	public static boolean inArray(String string, String[] strings) {
		if (string == null)
			return false;
		for (String s : strings) {
			if (s != null && s.equalsIgnoreCase(string))
				return true;
		}
		return false;
	}

	public static boolean inArray(int i, int[] array) {
		for (int j : array) {
			if (j == i)
				return true;
		}
		return false;
	}
}

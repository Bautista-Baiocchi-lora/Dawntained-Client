package org.ubot.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Utilities {


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

	public static ImageIcon getIcon(String path) {
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(ImageIO.read(Utilities.class.getClassLoader().getResource(path)));
		} catch (IllegalArgumentException e) {
		} catch (IOException e) {
		}

		if (icon == null) {
			icon = new ImageIcon(path);
		}

		return icon;
	}

	public static BufferedImage getImage(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Utilities.class.getClassLoader().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}

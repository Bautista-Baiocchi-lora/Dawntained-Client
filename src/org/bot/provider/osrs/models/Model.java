package org.bot.provider.osrs.models;

/**
 * Created by Ethan on 7/12/2017.
 */

import org.bot.Engine;
import org.bot.util.Utilities;

import java.awt.*;
import java.util.Arrays;

public class Model {

	Object model;
	int orientation;

	private int[] orginal_x;
	private int[] orginal_z;
	private int[] trianglesX;
	private int[] trianglesZ;
	private int[] trianglesY;
	private int[] verticesX;
	private int[] verticesZ;
	private int[] verticesY;
	private int gridX;
	private int gridY;
	private int z;

	public Model(Object model) {
		if (model != null) {
			this.orientation = 0;
			this.gridY = 0;
			this.gridX = 0;
			this.z = 0;
			this.model = model;
			set(getVertX(model), getVertY(model), getVertZ(model), getTriX(model), getTriY(model), getTriZ(model),
					this.orientation);

		}
	}

	public Model(Model wrapper, int orientation, int x, int y, int z) {
		this.orientation = orientation;
		this.gridY = y;
		this.gridX = x;
		this.z = z;

		this.orientation = orientation;
		this.verticesX = wrapper.getXVertices();
		this.verticesY = wrapper.getYVertices();
		this.verticesZ = wrapper.getZVertices();
		this.trianglesX = wrapper.getXTriangles();
		this.trianglesY = wrapper.getYTriangles();
		this.trianglesZ = wrapper.getZTriangles();

		if (orientation != 0) {
			orginal_x = new int[verticesX.length];
			orginal_z = new int[verticesZ.length];
			orginal_x = Arrays.copyOfRange(this.verticesX, 0, verticesX.length);
			orginal_z = Arrays.copyOfRange(this.verticesZ, 0, verticesZ.length);
			verticesX = new int[orginal_x.length];
			verticesZ = new int[orginal_z.length];
			int theta = orientation & 0x3fff;
			int sin = Utilities.SINE[theta];
			int cos = Utilities.COSINE[theta];
			for (int i = 0; i < orginal_x.length; ++i) {
				verticesX[i] = (orginal_x[i] * cos + orginal_z[i] * sin >> 15) >> 1;
				verticesZ[i] = (orginal_z[i] * cos - orginal_x[i] * sin >> 15) >> 1;
			}
		}
	}

	public void set(int[] verticesX, int[] verticesY, int[] verticesZ, int[] trianglesX, int[] trianglesY,
	                int[] trianglesZ, int orientation) {
		this.verticesX = new int[verticesX.length];
		this.verticesY = new int[verticesY.length];
		this.verticesZ = new int[verticesZ.length];
		this.trianglesX = new int[trianglesX.length];
		this.trianglesY = new int[trianglesY.length];
		this.trianglesZ = new int[trianglesZ.length];

		this.verticesX = Arrays.copyOfRange(verticesX, 0, verticesX.length);
		this.verticesY = Arrays.copyOfRange(verticesY, 0, verticesY.length);
		this.verticesZ = Arrays.copyOfRange(verticesZ, 0, verticesZ.length);
		this.trianglesX = Arrays.copyOfRange(trianglesX, 0, trianglesX.length);
		this.trianglesY = Arrays.copyOfRange(trianglesY, 0, trianglesY.length);
		this.trianglesZ = Arrays.copyOfRange(trianglesZ, 0, trianglesZ.length);

		this.orientation = orientation;

		if (orientation != 0) {
			orginal_x = new int[verticesX.length];
			orginal_z = new int[verticesZ.length];
			orginal_x = Arrays.copyOfRange(verticesX, 0, verticesX.length);
			orginal_z = Arrays.copyOfRange(verticesZ, 0, verticesZ.length);
			rotate();
		}
	}

	public void rotate() {
		int theta = orientation & 0x3fff;
		int sin = Utilities.SINE[theta];
		int cos = Utilities.COSINE[theta];
		for (int i = 0; i < orginal_x.length; ++i) {
			verticesX[i] = (orginal_x[i] * cos + orginal_z[i] * sin >> 15) >> 1;
			verticesZ[i] = (orginal_z[i] * cos - orginal_x[i] * sin >> 15) >> 1;
		}
	}

	public void draw(Graphics2D graphics, Color color) {
	} // I OVERRIDE THIS

	public Polygon[] getTriangles() { // I OVERRIDE THIS
		return null;
	}

	public int getTrianglesLength() { // I OVERRIDE THIS
		if (getXTriangles().length > 0)
			return getXTriangles().length;

		return -1;
	}

	public Point getRandomPoint() { // I OVERRIDE THIS
		return null;
	}


	public boolean contains(int x, int y) {
		for (final Polygon polygon : getTriangles()) {
			if (polygon.contains(x, y)) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(Point p) {
		return contains(p.x, p.y);
	}

	public int[] getXTriangles() {
		if (trianglesX.length > 0)
			return trianglesX;

		return new int[]{-1};
	}

	public int[] getYTriangles() {
		if (trianglesY.length > 0)
			return trianglesY;

		return new int[]{-1};
	}

	public int[] getZTriangles() {
		if (trianglesZ.length > 0)
			return trianglesZ;

		return new int[]{-1};
	}

	public int[] getXVertices() {
		if (verticesX.length > 0)
			return verticesX;
		return new int[]{-1};
	}

	public int[] getYVertices() {
		if (verticesY.length > 0)
			return verticesY;
		return new int[]{-1};
	}

	public int[] getZVertices() {
		if (verticesZ.length > 0)
			return verticesZ;
		return new int[]{-1};
	}

	public boolean isOnScreen() {
		return false;
	}

	public boolean isValid() {
		return model != null;
	}

	public int[] getVertX(Object model) {
		int[] i = (int[]) Engine.getReflectionEngine().getFieldHookValue("ModelVertX", model);
		if (i.length > 0)
			return i;
		return new int[]{-1};
	}

	public int[] getVertY(Object model) {
		int[] i = (int[]) Engine.getReflectionEngine().getFieldHookValue("ModelVertY", model);
		if (i.length > 0)
			return i;
		return new int[]{-1};
	}

	public int[] getVertZ(Object model) {
		int[] i = (int[]) Engine.getReflectionEngine().getFieldHookValue("ModelVertZ", model);
		if (i.length > 0)
			return i;
		return new int[]{-1};
	}

	public int[] getTriX(Object model) {
		int[] i = (int[]) Engine.getReflectionEngine().getFieldHookValue("ModelTriX", model);
		if (i.length > 0)
			return i;
		return new int[]{-1};
	}

	public int[] getTriY(Object model) {
		int[] i = (int[]) Engine.getReflectionEngine().getFieldHookValue("ModelTriY", model);
		if (i.length > 0)
			return i;
		return new int[]{-1};
	}

	public int[] getTriZ(Object model) {
		int[] i = (int[]) Engine.getReflectionEngine().getFieldHookValue("ModelTriZ", model);
		if (i.length > 0)
			return i;
		return new int[]{-1};
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public int getZ() {
		return z;
	}
}

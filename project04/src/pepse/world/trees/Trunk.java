package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

public class Trunk extends GameObject {

	public final Vector2 trunkSize;


	public Trunk(Vector2 topLeftCorner, Renderable renderable) {
		super(topLeftCorner, new Vector2(100, 160), renderable);
		this.trunkSize = getRandomTrunkSize();
		physics().preventIntersectionsFromDirection(Vector2.ZERO);
		physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);


	}

	private Vector2 getRandomTrunkSize() {
		Random random = new Random();
		int width = 20;  // Minimum width of the trunk
		int minHeight = 40; // Minimum height of the trunk
		int maxHeight = 100; // Maximum height of the trunk

		int height = random.nextInt(maxHeight - minHeight + 1) + minHeight;
		Vector2 newTrunkDim = new Vector2(width, height);
		this.setDimensions(newTrunkDim);

		return newTrunkDim;
	}

	public Vector2 getTrunkSize() {
		return this.trunkSize;
	}
}

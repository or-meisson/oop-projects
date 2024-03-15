package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


import java.util.Random;

/**
 * The trunk is a brown rectangle that is centered at the bottom of the
 * tree and has a random width and height.
 */
public class Trunk extends GameObject {

	private static final int MIN_WIDTH_FOR_TRUNK = 20;
	private static final int MIN_HEIGHT_FOR_TRUNK = 40;
	private static final int MAX_HEIGHT_FOR_TRUNK = 100;
	private final Vector2 trunkSize;


	/**
	 * Creates a trunk game object that is centered at the bottom of the tree and has
	 * a random width and height.
	 * @param topLeftCorner the top left corner of the trunk
	 * @param renderable the renderable of the trunk
	 */
	public Trunk(Vector2 topLeftCorner, Renderable renderable) {
		super(topLeftCorner, Vector2.ONES, renderable);
		this.trunkSize = getRandomTrunkSize();
		physics().preventIntersectionsFromDirection(Vector2.ZERO);
		physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);


	}

	/**
	 * Returns a random size for the trunk
	 * @return a random size for the trunk
	 */
	private Vector2 getRandomTrunkSize() {
		Random random = new Random();
		int minHeight = MIN_HEIGHT_FOR_TRUNK; // Minimum height of the trunk

		int height = random.nextInt(MAX_HEIGHT_FOR_TRUNK - minHeight + 1) + minHeight;
		Vector2 newTrunkDim = new Vector2(MIN_WIDTH_FOR_TRUNK, height);
		this.setDimensions(newTrunkDim);

		return newTrunkDim;
	}

	/**
	 * Returns the size of the trunk
	 * @return the size of the trunk
	 */
	public Vector2 getTrunkSize() {
		return this.trunkSize;
	}



}

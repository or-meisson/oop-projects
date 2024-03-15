package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.function.BiConsumer;

/**
 * The tree is a game object that has a trunk, leaves, and fruit.
 */
public class Tree  {

	private static final float TRUNK_ֹCENTER_ֹMULT_FACTOR = 0.5F;
	private static final int LEAVES_LOC_FROM_TOP_OF_TRUNK = 50;
	private  final Color LEAVES_COLOR = new Color(LEAVES_LOC_FROM_TOP_OF_TRUNK, 200, 30);

	private  final Color TRUNK_COLOR = new Color(100, LEAVES_LOC_FROM_TOP_OF_TRUNK, 20);

	private final Trunk trunk;
	private final Leaves leaves;
	private final Fruit fruit;
	private static final Color[] FRUIT_COLORS = {
			Color.RED,
			Color.ORANGE,
			Color.YELLOW,
			 } ;

	/**
	 * Creates a tree game object that is centered in the top left corner
	 * and has a trunk, leaves, and fruit.
	 * @param topLeftCorner the top left corner of the tree
	 * @param renderable the renderable of the tree
	 * @param onCollisionOfFruit the action to perform when the fruit collides with another game object
	 */
	public Tree(Vector2 topLeftCorner, Renderable renderable,
				BiConsumer<GameObject, GameObject> onCollisionOfFruit) {
//		super(topLeftCorner, Vector2.ONES, renderable);
		Renderable trunkRenderable = new RectangleRenderable(TRUNK_COLOR);
		this.trunk = new Trunk(topLeftCorner, trunkRenderable);
		this.leaves = new Leaves(topLeftCorner,
				new RectangleRenderable(LEAVES_COLOR));
		this.fruit = new Fruit(topLeftCorner,
				new OvalRenderable(Color.RED),onCollisionOfFruit);
		this.fruit.setCenter(this.leaves.getCenter());
	}

	/**
	 * Returns the trunk of the tree
	 * @return the trunk of the tree
	 */
	public Trunk getTrunk() {
		return this.trunk;
	}

	/**
	 * Sets the top left corner of the trunk
	 * @param topLeftCorner the top left corner of the trunk
	 */
	public void setTrunkTopLeftCorner(Vector2 topLeftCorner) {
		this.trunk.setTopLeftCorner(topLeftCorner);
	}

	/**
	 * Sets the center of the leaves
	 */
	public void setLeavesCenter() {
		this.leaves.setCenter(new Vector2(getTrunk().getTopLeftCorner().add
				(getTrunk().getDimensions()
				.mult(TRUNK_ֹCENTER_ֹMULT_FACTOR)).x(), getTrunk().getTopLeftCorner().y() -
				LEAVES_LOC_FROM_TOP_OF_TRUNK));
	}

	/**
	 * Returns the leaves of the tree
	 * @return the leaves of the tree
	 */
	public Leaves getLeaves() {
		return this.leaves;
	}


	/**
	 * Returns the fruit of the tree
	 * @return the fruit of the tree
	 */
	public GameObject getFruit() {
		return this.fruit;
	}

	/**
	 * Sets the center of the fruit
	 */
	public void setFruitCenter() {
		this.fruit.setCenter(this.leaves.getCenter());
	}

	/**
	 * Rotates the leaves and changes the renderable of the trunk and fruit when the player jumps.
	 */
	public void onJump() {

		this.leaves.onJump();
		this.trunk.renderer().setRenderable
				(new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR)));
		this.fruit.renderer().setRenderable
				(new OvalRenderable(FRUIT_COLORS[(int) (Math.random() * FRUIT_COLORS.length)]));


	}
}

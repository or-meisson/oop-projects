package pepse.world;
import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A block is a game object that is immovable and has a size of 30x30.
 */
public class Block extends GameObject{

	/**
	 * the size of the block
	 */
	public static final int SIZE = 30;

	/**
	 * Creates a block game object that is immovable and has a size of 30x30.
	 * @param topLeftCorner the top left corner of the block
	 * @param renderable the renderable of the block
	 */
	public Block(Vector2 topLeftCorner, Renderable renderable) {
		super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
		physics().preventIntersectionsFromDirection(Vector2.ZERO);
		physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);

	}




}

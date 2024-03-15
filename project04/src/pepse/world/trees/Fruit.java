package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.Component;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.function.BiConsumer;

/**
 * The fruit is a red oval that is centered on the leaves and has a radius of 30.
 */
public class Fruit extends GameObject {

	private final Vector2 FRUIT_SIZE = new Vector2(30, 30);
	private final BiConsumer<GameObject, GameObject> onCollisionOfFruit;


	/**
	 * Creates a fruit game object that is centered on the leaves and has a radius of 30.
	 * @param topLeftCorner the top left corner of the fruit
	 * @param renderable the renderable of the fruit
	 * @param onCollisionOfFruit the action to perform when the fruit collides with another game object
	 */
	public Fruit(Vector2 topLeftCorner, Renderable renderable,
				 BiConsumer<GameObject, GameObject> onCollisionOfFruit) {
		super(topLeftCorner, Vector2.ONES, renderable);
		this.setDimensions(FRUIT_SIZE);
		this.onCollisionOfFruit = onCollisionOfFruit;
		this.setDimensions(FRUIT_SIZE);





	}

	/**
	 * Performs the action to perform when the fruit collides with another game object.
	 * @param other the other game object
	 * @param collision the collision
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		this.onCollisionOfFruit.accept(this, other);


	}





}

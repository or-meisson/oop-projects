
package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a ball GameObject that can collide with other objects.
 *
 * @author Or Meissonnier
 *
 */
public class Ball extends GameObject {

	private final Sound collisionSound;
	private int collisionCounter = 0;
	private final Counter ballExtraPaddleCollision;
	private final String tagToNotice;


	/**
	 * Construct a new Ball instance.
	 *
	 * @param topLeftCorner Position of the object, in window coordinates (pixels).
	 *                      Note that (0,0) is the top-left corner of the window.
	 * @param dimensions    Width and height in window coordinates.
	 * @param renderable    The renderable representing the object. Can be null, in which case
	 *                      the GameObject will not be rendered.
	 */
	public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound CollisionSound,
				Counter ballExtraPaddleCollision, String tagToNotice) {
		super(topLeftCorner, dimensions, renderable);
		this.collisionSound = CollisionSound;
		this.ballExtraPaddleCollision = ballExtraPaddleCollision;
		this.tagToNotice = tagToNotice;
	}


	/**
	 * Retrieves the collision counter.
	 *
	 * @return The number of collisions.
	 */
	public int getCollisionCounter() {
		return collisionCounter;
	}

	/**
	 * Handles the collision event with another GameObject.
	 *
	 * @param other     The GameObject collided with.
	 * @param collision The collision details.
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		collisionCounter++;
		if (other.getTag().equals(tagToNotice)){
			ballExtraPaddleCollision.increment();
		}
		super.onCollisionEnter(other, collision);
		Vector2 newVel = getVelocity().flipped(collision.getNormal());
		setVelocity(newVel);
		collisionSound.play();
	}


}

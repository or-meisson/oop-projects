
package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a falling heart GameObject.
 *
 * @author Or Meissonnier
 *
 */
public class FallingHeart extends Heart{


	private final String tagToNotice;
	private boolean hasCollidedWithPaddle=false;


	/**
	 * Constructs a new FallingHeart instance.
	 *
	 * @param topLeftCorner  Position of the top-left corner of the heart, in window coordinates (pixels).
	 *                       Note that (0,0) is the top-left corner of the window.
	 * @param dimensions     Width and height of the heart in window coordinates.
	 * @param renderable     The renderable representing the heart.
	 * @param tagToNotice    The tag of the GameObject to notice for collision detection.
	 */
	public FallingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
						String tagToNotice) {
		super(topLeftCorner, dimensions, renderable);
		this.tagToNotice = tagToNotice;
	}


	/**
	 * Handles the collision event when the heart collides with another GameObject.
	 *
	 * @param other     The GameObject collided with.
	 * @param collision The collision details.
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		setHasCollidedWithPaddle(true);
		super.onCollisionEnter(other, collision);
	}

	/**
	 * Sets whether the heart has collided with the paddle.
	 *
	 * @param hasCollidedWithPaddle True if the heart has collided with the paddle, false otherwise.
	 */
	private void setHasCollidedWithPaddle(boolean hasCollidedWithPaddle) {
		this.hasCollidedWithPaddle = hasCollidedWithPaddle;
	}

	/**
	 * Checks if the heart has collided with the paddle.
	 *
	 * @return True if the heart has collided with the paddle, false otherwise.
	 */
	public boolean isHasCollidedWithPaddle() {
		return hasCollidedWithPaddle;
	}

	/**
	 * Determines whether the heart should collide with the specified GameObject.
	 *
	 * @param other The GameObject to check collision with.
	 * @return True if the heart should collide with the specified GameObject, false otherwise.
	 */

	@Override
	public boolean shouldCollideWith(GameObject other) {
		if(other.getTag().equals(tagToNotice)){
			return true;
		}
		return false;
	}
}

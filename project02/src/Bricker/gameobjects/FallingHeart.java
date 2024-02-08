package Bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class FallingHeart extends Heart{


	private final String tagToNotice;
	private boolean hasCollidedWithPaddle=false;

	/**
	 * Construct a new GameObject instance.
	 *
	 * @param topLeftCorner Position of the object, in window coordinates (pixels).
	 *                      Note that (0,0) is the top-left corner of the window.
	 * @param dimensions    Width and height in window coordinates.
	 * @param renderable    The renderable representing the object. Can be null, in which case
	 *                      the GameObject will not be rendered.
	 */
	public FallingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
						String tagToNotice) {
		super(topLeftCorner, dimensions, renderable);
		this.tagToNotice = tagToNotice;
	}
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		setHasCollidedWithPaddle(true);
		super.onCollisionEnter(other, collision);
	}

	private void setHasCollidedWithPaddle(boolean hasCollidedWithPaddle) {
		this.hasCollidedWithPaddle = hasCollidedWithPaddle;
	}

	public boolean isHasCollidedWithPaddle() {
		return hasCollidedWithPaddle;
	}

	@Override
	public boolean shouldCollideWith(GameObject other) {
		if(other.getTag().equals(tagToNotice)){
			return true;
		}
		return false;
	}
}

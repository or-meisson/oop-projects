package Bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class FallingHeart extends Heart{
	public boolean hasCollidedWithPaddle=false;

	/**
	 * Construct a new GameObject instance.
	 *
	 * @param topLeftCorner Position of the object, in window coordinates (pixels).
	 *                      Note that (0,0) is the top-left corner of the window.
	 * @param dimensions    Width and height in window coordinates.
	 * @param renderable    The renderable representing the object. Can be null, in which case
	 *                      the GameObject will not be rendered.
	 */
	public FallingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
		super(topLeftCorner, dimensions, renderable);
	}
	//todo falling heart still remains when its passes 500
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		hasCollidedWithPaddle= true; //TODO GETTER
		super.onCollisionEnter(other, collision);
	}

	@Override
	public boolean shouldCollideWith(GameObject other) {
		if(other.getTag().equals("mainPaddle")){ //TODO GET FROM CONSTRUCTOR
//			System.out.println("collidin!");
			return true;
		}
		return false;
	}
}

package Bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Ball extends GameObject {

	private final Sound collisionSound;
	private int collisionCounter = 0;
	private Counter ballExtraPaddleCollision;


	/**
	 * Construct a new GameObject instance.
	 *
	 * @param topLeftCorner Position of the object, in window coordinates (pixels).
	 *                      Note that (0,0) is the top-left corner of the window.
	 * @param dimensions    Width and height in window coordinates.
	 * @param renderable    The renderable representing the object. Can be null, in which case
	 *                      the GameObject will not be rendered.
	 */
	public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound CollisionSound,
				Counter ballExtraPaddleCollision) {
		super(topLeftCorner, dimensions, renderable);
		this.collisionSound = CollisionSound;
		this.ballExtraPaddleCollision = ballExtraPaddleCollision;
	}

	public int getCollisionCounter() {
		return collisionCounter;
	}

	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		collisionCounter++;
		if (other.getTag().equals("extraPaddle")){ //TODO GET IN CONSTRUCTOR
//			System.out.println("coolision of ball with extra paddle");
			ballExtraPaddleCollision.increment(); //TODO COUNT IN EXTRA PADDLE
		}
		super.onCollisionEnter(other, collision);
		Vector2 newVel = getVelocity().flipped(collision.getNormal());
		setVelocity(newVel);
		collisionSound.play();
	}


}


package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Represents a paddle GameObject that can be controlled by user input.
 *
 * @author Or Meissonnier
 *
 */
public class Paddle extends GameObject {
	private static final float MOVEMENT_SPEED = 300;
	private static final int LEFT_X_POINT_FOR_PADDLE = 0;
	private final UserInputListener inputListener;
	private final float leftXPointForPaddle;
	private final float rightXPointForPaddle;
	private final List<String> tagsToNotice;
	private final float windowWidth;

	/**
	 * Constructs a new Paddle instance.
	 *
	 * @param topLeftCorner       Position of the top-left corner of the paddle,
	 *                              in window coordinates (pixels).
	 *                            Note that (0,0) is the top-left corner of the window.
	 * @param dimensions          Width and height of the paddle in window coordinates.
	 * @param renderable          The renderable representing the paddle.
	 * @param inputListener       The user input listener to handle paddle movement.
	 * @param leftXPointForPaddle The leftmost x-coordinate point where the paddle can move.
	 * @param rightXPointForPaddle The rightmost x-coordinate point where the paddle can move.
	 */
	public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
				  UserInputListener inputListener, float leftXPointForPaddle, float rightXPointForPaddle,
				  List<String> tagsToNotice, float windowWidth) {
		super(topLeftCorner, dimensions, renderable);
		this.inputListener = inputListener;
		this.leftXPointForPaddle = leftXPointForPaddle;
		this.rightXPointForPaddle = rightXPointForPaddle;
		this.tagsToNotice = tagsToNotice;
		this.windowWidth = windowWidth;
	}

	/**
	 * Checks whether the paddle should collide with another GameObject.
	 *
	 * @param other The other GameObject.
	 * @return true if the paddle should collide with the other GameObject, false otherwise.
	 */
	@Override
	public boolean shouldCollideWith(GameObject other) {
		if(tagsToNotice.contains(other.getTag()) || super.shouldCollideWith(other)){
			return true;
		}
		return false;

	}

	/**
	 * Updates the paddle's position based on user input.
	 *
	 * @param deltaTime The time elapsed since the last update, in seconds.
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		Vector2 movementDir = Vector2.ZERO;
		if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
			movementDir = movementDir.add(Vector2.LEFT);
		}
		if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
			movementDir = movementDir.add(Vector2.RIGHT);
		}
		setVelocity(movementDir.mult(MOVEMENT_SPEED));
		Vector2 topLeftCorner = getTopLeftCorner();

		if (topLeftCorner.x() < leftXPointForPaddle) {
			setTopLeftCorner(new Vector2(LEFT_X_POINT_FOR_PADDLE, topLeftCorner.y()));

		}

		if (topLeftCorner.x() >= rightXPointForPaddle){
			setTopLeftCorner(new Vector2(windowWidth - getDimensions().x(), topLeftCorner.y()));
		}
	}

}

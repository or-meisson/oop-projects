
package bricker.gameobjects;

import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.List;

/**
 * Represents an extra paddle GameObject.
 *
 * @author Or Meissonnier
 *
 */
public class ExtraPaddle extends Paddle{
	private boolean isShowing = false;


	/**
	 * Constructs a new ExtraPaddle instance.
	 *
	 * @param topLeftCorner         Position of the top-left corner of the paddle, in window
	 *                                coordinates (pixels).
	 *                              Note that (0,0) is the top-left corner of the window.
	 * @param dimensions            Width and height of the paddle in window coordinates.
	 * @param renderable            The renderable representing the paddle.
	 * @param inputListener         The user input listener for paddle movement.
	 * @param leftXPointForPaddle   The leftmost x-coordinate where the paddle can move.
	 * @param rightXPointForPaddle  The rightmost x-coordinate where the paddle can move.
	 */
	public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
					   UserInputListener inputListener, float leftXPointForPaddle, float
							   rightXPointForPaddle, List<String> tagsToNotice , float windowWidth) {
		super(topLeftCorner, dimensions, renderable, inputListener, leftXPointForPaddle,
				rightXPointForPaddle, tagsToNotice, windowWidth);

	}
	/**
	 * Checks if the extra paddle is currently showing.
	 *
	 * @return True if the paddle is showing, false otherwise.
	 */
	public boolean isShowing() {
		return isShowing;
	}

	/**
	 * Sets whether the extra paddle should be showing.
	 *
	 * @param showing True to show the paddle, false to hide it.
	 */
	public void setShowing(boolean showing) {
		isShowing = showing;
	}
}

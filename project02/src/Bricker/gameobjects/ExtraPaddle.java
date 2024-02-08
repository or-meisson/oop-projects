package Bricker.gameobjects;

import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraPaddle extends Paddle{



	private boolean isShowing = false;


	public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
					   UserInputListener inputListener, float leftXPointForPaddle, float rightXPointForPaddle ) {
		super(topLeftCorner, dimensions, renderable, inputListener, leftXPointForPaddle, rightXPointForPaddle);

	}

	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean showing) {
		isShowing = showing;
	}
}

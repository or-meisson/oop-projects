package Bricker.gameobjects;

import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraPaddle extends Paddle{



	private boolean isShowing = false;


	public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, boolean isExtraPaddle) {
		super(topLeftCorner, dimensions, renderable, inputListener, isExtraPaddle);

	}

	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean showing) {
		isShowing = showing;
	}
}

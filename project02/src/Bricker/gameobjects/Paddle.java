package Bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
	private static final float MOVEMENT_SPEED = 300;
	private final UserInputListener inputListener;



	public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
				  UserInputListener inputListener, boolean isExtraPaddle) {
		super(topLeftCorner, dimensions, renderable);
//		this.isExtraPaddle = isExtraPaddle;
		this.inputListener = inputListener;
	}

	@Override
	public boolean shouldCollideWith(GameObject other) {
		if (other.getTag().equals("ball") || other.getTag().equals("fallingHeart") || super.shouldCollideWith(other)){
			return true;
		}
		return false;
	}

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

		if (topLeftCorner.x() < 0) {
			setTopLeftCorner(new Vector2(0, topLeftCorner.y()));

		}

		if (topLeftCorner.x() >= 600){ //TODO GET FROM CONSTRUCTOR
			setTopLeftCorner(new Vector2(700 -getDimensions().x(), topLeftCorner.y()));
		}
	}

}

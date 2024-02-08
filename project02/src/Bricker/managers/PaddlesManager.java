package Bricker.managers;

import Bricker.gameobjects.ExtraPaddle;
import Bricker.gameobjects.Paddle;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class PaddlesManager {


	private static final int HITS_NUM_TO_REMOVE_EXTRA_PADDLE = 4;
	private static final String PADDLE_IMG_PATH = "assets/paddle.png";
	private static final Vector2 EXTRA_PADDLE_DIMENSIONS = new Vector2(100, 15);
	private final Vector2 windowDimensions;
	private final GameObjectCollection gameObjects;
	private final Renderable paddleImage;
	private final UserInputListener inputListener;
	private final float PADDLE_HEIGHT = 15;
	private final float PADDLE_WIDTH = 100;
	private final Counter ballExtraPaddleCollision;
	private ExtraPaddle extraPaddle;



	public PaddlesManager(ImageReader imageReader, Vector2 windowDimensions,
						  GameObjectCollection gameObjects, UserInputListener inputListener) {
		this.windowDimensions = windowDimensions;
		this.gameObjects = gameObjects;
		paddleImage = imageReader.readImage(PADDLE_IMG_PATH, true);
		this.inputListener = inputListener;
		ballExtraPaddleCollision =  new Counter(0);

	}

	public Counter getBallExtraPaddleCollision() {
		return ballExtraPaddleCollision;
	}


	public void createMainPaddle(){

		Paddle userPaddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage,
				inputListener, 0,
				windowDimensions.x() - PADDLE_WIDTH);
		userPaddle.setCenter(new Vector2(windowDimensions.x()/2,
				(int) windowDimensions.y()-30)); //TODO HOW TO MAKE CONSTANT?
		gameObjects.addGameObject(userPaddle);
		userPaddle.setTag("mainPaddle");


	}


	public void createExtraPaddle(){
		ExtraPaddle extraPaddle = new ExtraPaddle(Vector2.ZERO, EXTRA_PADDLE_DIMENSIONS, paddleImage,
				inputListener, 0, windowDimensions.x() - PADDLE_WIDTH);
		extraPaddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()/2)); //TODO HOW TO MAKE CONSTANT?
		this.extraPaddle = extraPaddle;
		extraPaddle.setTag("extraPaddle");



	}

	public ExtraPaddle getExtraPaddle() {
		return extraPaddle;
	}

	public void checkIfNeedToRemoveExtraPaddle() {
		if(ballExtraPaddleCollision.value() == HITS_NUM_TO_REMOVE_EXTRA_PADDLE){
			gameObjects.removeGameObject(extraPaddle);
			extraPaddle.setShowing(false);
		}


	}






}

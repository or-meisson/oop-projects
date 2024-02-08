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


	public static final int HITS_NUM_TO_REMOVE_EXTRA_PADDLE = 4;
	private final ImageReader imageReader;
	private Vector2 windowDimensions;
	private final GameObjectCollection gameObjects;
	private Renderable paddleImage;
	private final UserInputListener inputListener;
	private final Vector2 PADDLE_SIZE = new Vector2(100, 15);
//	private final float PADDLE_POSITION_X
//	private final Vector2 MAIN_PADDLE_POSITION = new Vector2(windowDimensions.x()/2,
//		(int) windowDimensions.y()-30);
	private Counter ballExtraPaddleCollision;
	private ExtraPaddle extraPaddle;



	public PaddlesManager(ImageReader imageReader, Vector2 windowDimensions,
						  GameObjectCollection gameObjects, UserInputListener inputListener) {
		this.imageReader = imageReader;
		this.windowDimensions = windowDimensions;
		this.gameObjects = gameObjects;

		paddleImage = imageReader.readImage("assets/paddle.png", true);
		this.inputListener = inputListener;
		ballExtraPaddleCollision =  new Counter(0);

	}

	public Counter getBallExtraPaddleCollision() {
		return ballExtraPaddleCollision;
	}


	public Paddle createMainPaddle(){

		Paddle userPaddle = new Paddle(Vector2.ZERO, PADDLE_SIZE, paddleImage, inputListener, false);
		userPaddle.setCenter(new Vector2(windowDimensions.x()/2,
				(int) windowDimensions.y()-30));
		gameObjects.addGameObject(userPaddle);
		return userPaddle;

	}


	public Paddle createExtraPaddle(){
		ExtraPaddle extraPaddle = new ExtraPaddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener,
				true);
		extraPaddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()/2));
		this.extraPaddle = extraPaddle;
		return extraPaddle;


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

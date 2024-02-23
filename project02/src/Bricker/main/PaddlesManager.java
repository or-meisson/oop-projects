
package bricker.main;

import bricker.gameobjects.ExtraPaddle;
import bricker.gameobjects.Paddle;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The PaddlesManager class manages the creation and behavior of paddles in the game.
 * It handles the main paddle controlled by the user and any extra paddles that may be introduced.
 * Additionally, it monitors collisions between the ball and extra paddles.
 *
 * @author Or Meissonnier
 */
public class PaddlesManager {
	private static final String EXTRA_PADDLE_TAG = "extraPaddle";
	private static final int HITS_NUM_TO_REMOVE_EXTRA_PADDLE = 4;
	private static final String PADDLE_IMG_PATH = "assets/paddle.png";
	private static final Vector2 EXTRA_PADDLE_DIMENSIONS = new Vector2(100, 15);
	private static final int PADDLE_GAP_FROM_BOTTOM = 30;
	private static final float CENTER_MULT_FACTOR = 0.5f;
	private static final int LEFT_X_POINT_FOR_PADDLE = 0;
	private final Vector2 windowDimensions;
	private final GameObjectCollection gameObjects;
	private final Renderable paddleImage;
	private final UserInputListener inputListener;
	private final float PADDLE_HEIGHT = 15;
	private final float PADDLE_WIDTH = 100;
	private final Counter ballExtraPaddleCollision;
	private ExtraPaddle extraPaddle;
	private final List<String> tagsToNoticeForPaddle = new ArrayList<>(Arrays.asList("ball",
			"fallingHeart"));


	/**
	 * Constructs a PaddlesManager with the specified parameters.
	 *
	 * @param imageReader      the image reader for loading paddle images
	 * @param windowDimensions the dimensions of the game window
	 * @param gameObjects      the collection of game objects
	 * @param inputListener    the user input listener for controlling paddles
	 */
	public PaddlesManager(ImageReader imageReader, Vector2 windowDimensions,
						  GameObjectCollection gameObjects, UserInputListener inputListener) {
		this.windowDimensions = windowDimensions;
		this.gameObjects = gameObjects;
		paddleImage = imageReader.readImage(PADDLE_IMG_PATH, true);
		this.inputListener = inputListener;
		ballExtraPaddleCollision =  new Counter(0);

	}

	/**
	 Getter for ballExtraPaddleCollision counter
	 */
	public Counter getBallExtraPaddleCollision() {
		return ballExtraPaddleCollision;
	}

	/**
	 * Creates the main paddle and adds it to the game objects collection.
	 */
	public void createMainPaddle(){
		Paddle userPaddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage,
				inputListener, 0,
				windowDimensions.x() - PADDLE_WIDTH, tagsToNoticeForPaddle,
				windowDimensions.x());
		userPaddle.setCenter(new Vector2(windowDimensions.x() * CENTER_MULT_FACTOR,
				(int) windowDimensions.y()- PADDLE_GAP_FROM_BOTTOM));
		gameObjects.addGameObject(userPaddle);
		userPaddle.setTag("mainPaddle");


	}

	/**
	 * Creates an extra paddle and sets its initial position.
	 */
	public void createExtraPaddle(){
		ExtraPaddle extraPaddle = new ExtraPaddle(Vector2.ZERO, EXTRA_PADDLE_DIMENSIONS, paddleImage,
				inputListener, LEFT_X_POINT_FOR_PADDLE, windowDimensions.x() - PADDLE_WIDTH,
				tagsToNoticeForPaddle, windowDimensions.x());
		extraPaddle.setCenter(new Vector2(windowDimensions.x() * CENTER_MULT_FACTOR,
				windowDimensions.y()* CENTER_MULT_FACTOR));
		this.extraPaddle = extraPaddle;
		extraPaddle.setTag(EXTRA_PADDLE_TAG);




	}

	/**
	 Getter for extra paddle
	 */
	public ExtraPaddle getExtraPaddle() {
		return extraPaddle;
	}

	/**
	 * Checks if the extra paddle needs to be removed based on collision hits with the ball.
	 */
	public void checkIfNeedToRemoveExtraPaddle() {
		if(ballExtraPaddleCollision.value() == HITS_NUM_TO_REMOVE_EXTRA_PADDLE){
			gameObjects.removeGameObject(extraPaddle);
			extraPaddle.setShowing(false);
		}


	}






}

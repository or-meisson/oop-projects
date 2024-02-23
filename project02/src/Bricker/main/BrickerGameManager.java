


package bricker.main;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;

import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;


/**
 * The BrickerGameManager class represents the main game manager for the "Bouncing Ball" game.
 * It extends the GameManager class from the danogl package and manages the initialization,
 * update, and game logic of the Bricker game.
 *
 * The class handles the creation of game objects such as paddles, balls, bricks, walls, and background,
 * as well as managing user input, collision detection, and game end conditions.
 *
 * @author Or Meissonnier
 */
public class BrickerGameManager extends GameManager {
	private static final Vector2 TOP_LEFT_CORNER_RIGHT_WALL = new Vector2(699, 0);
	private static final Vector2 DIMENSIONS_RIGHT_WALL = new Vector2(3, 500);
	private static final Vector2 TOP_LEFT_CORNER_LEFT_WALL = Vector2.ZERO;
	private static final Vector2 DIMENSIONS_LEFT_WALL = new Vector2(3, 500);
	private static final Vector2 TOP_LEFT_CORNER_UPPER_WALL = Vector2.ZERO;
	private static final Vector2 DIMENSIONS_UPPER_WALL = new Vector2(700, 3);
	private static final RectangleRenderable WALL_COLOR = null;
	private static final String LOSING_PROMPT = "You Lose! Play again?";
	private static final String WINNING_PROMPT = "You win! Play again?";
	private static final float FACTOR_FOR_CAMERA = 1.2F;
	private static final String BACKGROUND_IMG_PATH = "assets/DARK_BG2_small.jpeg";
	private static final Vector2 WINDOW_DIMENSIONS = new Vector2(700, 500);
	private static final String WINDOW_TITLE = "Bouncing Ball";
	private static final int DEFAULT_BRICK_IN_ROW = 7;
	private static final int DEFAULT_ROWS_OF_BRICKS = 8;
	private static final int VALID_ARG_NUM = 2;
	private final Vector2 MY_WINDOW_DIMENSIONS = new Vector2(700, 500);
	private final int rowsOfBricks;
	private final int brickInRow;
	private Ball ball;
	private UserInputListener inputListener;
	private WindowController windowController;
	private LivesManager livesManager;
	private PaddlesManager paddlesManager;
	private CameraManager cameraManager;
	private ImageReader imageReader;
	private BricksManager bricksManager;
	private BallsManager ballsManager;


	/**
	 * Constructs a BrickerGameManager with the specified window title and dimensions.
	 *
	 * @param windowTitle      the title of the game window
	 * @param windowDimensions the dimensions of the game window
	 */
	public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
		this(windowTitle, windowDimensions, DEFAULT_BRICK_IN_ROW, DEFAULT_ROWS_OF_BRICKS);
	}

	/**
	 * Constructs a BrickerGameManager with the specified window title, dimensions, brick in row,
	 * and rows of bricks.
	 *
	 * @param windowTitle      the title of the game window
	 * @param windowDimensions the dimensions of the game window
	 * @param brickInRow       the number of bricks in each row
	 * @param rowsOfBricks     the number of rows of bricks
	 */
	public BrickerGameManager(String windowTitle, Vector2 windowDimensions,
							  int brickInRow, int rowsOfBricks) {
		super(windowTitle, windowDimensions);
		this.rowsOfBricks = rowsOfBricks;
		this.brickInRow = brickInRow;
	}

	/**
	 * Initializes the game by setting up game objects, user input, window controller, etc.
	 *
	 * @param imageReader      the image reader for loading images
	 * @param soundReader      the sound reader for loading sounds
	 * @param inputListener    the user input listener for capturing user input
	 * @param windowController the window controller for managing the game window
	 */
	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader,
							   UserInputListener inputListener, WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		this.inputListener = inputListener;
		this.windowController = windowController;
		this.imageReader = imageReader;
		Vector2 windowDimensions = windowController.getWindowDimensions();
		//creating background
		createBackground();

		//create paddle
		this.paddlesManager = new PaddlesManager(imageReader, windowDimensions,
				gameObjects(), inputListener);
		paddlesManager.createMainPaddle();
		Counter ballExtraPaddleCollision = paddlesManager.getBallExtraPaddleCollision();

		//create ball
		this.ballsManager = new BallsManager(imageReader, soundReader, windowDimensions,
				gameObjects(), ballExtraPaddleCollision);
		Ball ball = ballsManager.createBall();
		this.ball = ball;

		//creating bricks
		this.bricksManager = new BricksManager(gameObjects(), imageReader, rowsOfBricks, brickInRow
				, windowDimensions);

		//creating camera
		Camera camera = new Camera(ball, Vector2.ZERO, windowController.
				getWindowDimensions().mult(FACTOR_FOR_CAMERA),
				windowController.getWindowDimensions());
		this.cameraManager = new CameraManager(this, camera, ball);

		//creating walls
		create_walls();

		//create extra paddle
		paddlesManager.createExtraPaddle();

		//creating hearts
		this.livesManager = new LivesManager(gameObjects(), imageReader, windowDimensions);
		livesManager.showLives();

		//create collision strategy manager
		CollisionStrategyManager collisionStrategyManager = new CollisionStrategyManager(gameObjects(),
				bricksManager.getBrickCounter(), ballsManager, paddlesManager, cameraManager, livesManager);

		bricksManager.initializeBricks(collisionStrategyManager);
	}


	/**
	 * Creates walls for the game environment.
	 */
	private void create_walls() {
		GameObject rightWall = new GameObject(TOP_LEFT_CORNER_RIGHT_WALL, DIMENSIONS_RIGHT_WALL,
				WALL_COLOR);
		gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
		GameObject leftWall = new GameObject(TOP_LEFT_CORNER_LEFT_WALL, DIMENSIONS_LEFT_WALL,
				WALL_COLOR);
		gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
		GameObject upperWall = new GameObject(TOP_LEFT_CORNER_UPPER_WALL, DIMENSIONS_UPPER_WALL,
				WALL_COLOR);
		gameObjects().addGameObject(upperWall, Layer.STATIC_OBJECTS);
	}


	/**
	 * Creates the background of the game.
	 */
	private void createBackground() {
		Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMG_PATH, false);
		GameObject background = new GameObject(Vector2.ZERO, MY_WINDOW_DIMENSIONS, backgroundImage);
		background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		gameObjects().addGameObject(background, Layer.BACKGROUND);
	}


	/**
	 * Checks for game end conditions and handles game over scenarios.
	 */
	private void checkForGameEnd() {
		livesManager.decrementLives();
		if (livesManager.getLives() == 0) { //we lose
			if (windowController.openYesNoDialog(LOSING_PROMPT)) {
				windowController.resetGame();
			} else {
				windowController.closeWindow();
			}
		} else {//we still have lives
			this.livesManager.handleLife();


		}

	}


	/**
	 * Updates the game state.
	 *
	 * @param deltaTime the time elapsed since the last update
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (bricksManager.areAllBricksGone()) {
			makeWinGame();
		}
		checkForPressedW();

		ballsManager.checkIfPuckBallGoneAndRemove();

		this.livesManager.addLivesHeartPaddleCollision();

		this.cameraManager.updateCamera();

		paddlesManager.checkIfNeedToRemoveExtraPaddle();
		if (ballsManager.checkIfBallTooHigh(ball)) {
			checkForGameEnd();
			ballsManager.repositionBall(ball);
		}
		this.livesManager.removeHighFallingHearts();

	}


	/**
	 * Checks if the 'W' key is pressed and triggers a win game scenario.
	 */
	private void checkForPressedW() {
		if (inputListener.isKeyPressed(KeyEvent.VK_W)) {
			makeWinGame();
		}

	}

	/**
	 * Handles the win game scenario.
	 */
	private void makeWinGame() {
		if (windowController.openYesNoDialog(WINNING_PROMPT)) {
			windowController.resetGame();
		} else {
			windowController.closeWindow();
		}
	}


	/**
	 * The entry point for the Bricker game. Creates and runs a new instance of the game manager.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		if (args.length != VALID_ARG_NUM) {
			GameManager gameManager = new BrickerGameManager(WINDOW_TITLE, WINDOW_DIMENSIONS);
			gameManager.run();
		} else {
			int brickInRow = Integer.parseInt(args[0]);
			int rowsOfBricks = Integer.parseInt(args[1]);
			GameManager gameManager = new BrickerGameManager(WINDOW_TITLE,
					WINDOW_DIMENSIONS, brickInRow, rowsOfBricks);
			gameManager.run();

		}

	}
}

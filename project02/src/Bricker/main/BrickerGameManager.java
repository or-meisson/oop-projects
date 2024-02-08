


package Bricker.main;
import Bricker.managers.*;
import Bricker.gameobjects.*;
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

import java.awt.*;
import java.awt.event.KeyEvent;


public class BrickerGameManager extends GameManager{
	private static final Vector2 TOP_LEFT_CORNER_RIGHT_WALL = new Vector2(699, 0);
	private static final Vector2 DIMENSIONS_RIGHT_WALL = new Vector2(3, 500);
	private static final Vector2 TOP_LEFT_CORNER_LEFT_WALL = Vector2.ZERO;
	private static final Vector2 DIMENSIONS_LEFT_WALL = new Vector2(3, 500);
	private static final Vector2 TOP_LEFT_CORNER_UPPER_WALL = Vector2.ZERO;
	private static final Vector2 DIMENSIONS_UPPER_WALL = new Vector2(700, 3);
	private static final RectangleRenderable WALL_COLOR = new RectangleRenderable(Color.CYAN);
	private static final String LOSING_PROMPT = "You Lose! Play again?";
	private static final String WINNING_PROMPT = "You win! Play again?";
	private static final float FACTOR_FOR_CAMERA = 1.2F;
	private static final String BACKGROUND_IMG_PATH = "assets/DARK_BG2_small.jpeg";
	private static final Vector2 WINDOW_DIMENSIONS = new Vector2(700, 500);
	private static final String WINDOW_TITLE = "Bouncing Ball";
	public static final int DEFAULT_BRICK_IN_ROW = 7;
	public static final int DEFAULT_ROWS_OF_BRICKS = 8;

	private final Vector2 MY_WINDOW_DIMENSIONS = new Vector2(700, 500);

	private final int rowsOfBricks;
	private final int brickInRow;
	private Ball ball;
	private UserInputListener inputListener;
	//todo https://courses.campus.gov.il/courses/course-v1:HUJI+ACD_RFP4_ObjectOrientedProgramming_HE+2022_1/courseware/3c5eb13522a844dc90e602010cf74fcb/038e48e9c496488487acdbfc7a2dd2e6/
	private WindowController windowController;
	private LivesManager livesManager;
	private PaddlesManager paddlesManager;
	private CameraManager cameraManager;
	private ImageReader imageReader;
	private BricksManager bricksManager;
	private BallsManager ballsManager;



	public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
		this(windowTitle,windowDimensions, DEFAULT_BRICK_IN_ROW, DEFAULT_ROWS_OF_BRICKS);
	}


	public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int brickInRow, int rowsOfBricks) {
		super(windowTitle, windowDimensions);
		this.rowsOfBricks = rowsOfBricks;
		this.brickInRow = brickInRow;
	}

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
		this.paddlesManager = new PaddlesManager(imageReader, windowDimensions, gameObjects(), inputListener);
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
		Camera camera = new Camera(ball, Vector2.ZERO, windowController.getWindowDimensions().mult(FACTOR_FOR_CAMERA),
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
		//todo dont forget that you winning is not goos bricks dissapear
	}

	private void create_walls() {//todo check color
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

	private void createBackground(){
		Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMG_PATH, false);
		GameObject background = new GameObject(Vector2.ZERO, MY_WINDOW_DIMENSIONS, backgroundImage);
		background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		gameObjects().addGameObject(background, Layer.BACKGROUND);
	}






	private void checkForGameEnd() {
		livesManager.decrementLives();
		if (livesManager.getLives() == 0){ //we lose
			if(windowController.openYesNoDialog(LOSING_PROMPT)){
				windowController.resetGame();
			}
			else{
				windowController.closeWindow();
			}
		}
		else {//we still have lives
			this.livesManager.handleLife();


		}

	}




	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(bricksManager.areAllBricksGone()){
			makeWinGame();
		}
		checkForPressedW();

		ballsManager.checkIfPuckBallGoneAndRemove();

		this.livesManager.addLivesHeartPaddleCollision();

		this.cameraManager.updateCamera();

		paddlesManager.checkIfNeedToRemoveExtraPaddle();
		if(ballsManager.checkIfBallToHigh(ball)){
			checkForGameEnd();
			ballsManager.repositionBall(ball);
		}
		this.livesManager.removeHighFallingHearts();

	}






	private void checkForPressedW() {
		if(inputListener.isKeyPressed(KeyEvent.VK_W)){
			makeWinGame();
		}

	}

	private void makeWinGame() {
		if(windowController.openYesNoDialog(WINNING_PROMPT)){
				windowController.resetGame();
			}
			else{
				windowController.closeWindow();
			}
	}



	public static void main(String[] args) {
		GameManager gameManager = new BrickerGameManager(WINDOW_TITLE, WINDOW_DIMENSIONS);
		gameManager.run();

		}

}

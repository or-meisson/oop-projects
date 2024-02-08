package Bricker.main;
//import Bricker.GraphicalLives;
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
	public static final Vector2 TOP_LEFT_CORNER_RIGHT_WALL = new Vector2(699, 0);
	public static final Vector2 DIMENSIONS_RIGHT_WALL = new Vector2(3, 500);
	public static final Vector2 TOP_LEFT_CORNER_LEFT_WALL = Vector2.ZERO;
	public static final Vector2 DIMENSIONS_LEFT_WALL = new Vector2(3, 500);
	public static final Vector2 TOP_LEFT_CORNER_UPPER_WALL = Vector2.ZERO;
	public static final Vector2 DIMENSIONS_UPPER_WALL = new Vector2(700, 3);
	public static final RectangleRenderable WALL_COLOR = new RectangleRenderable(Color.CYAN);
	public static final String LOSING_PROMPT = "You Lose! Play again?";
	public static final int LOSING_BALL_HEIGHT = 500;
	public static final String WINNING_PROMPT = "You win! Play again?";
	private int DEFAULT_ROWS_OF_BRICKS_NUM = 8;

	private int DEFAULT_NUM_OF_BRICKS_IN_ROW = 7;
	private Vector2 MY_WINDOW_DIMENSIONS = new Vector2(700, 500);


//	private static final float BALL_SPEED = 200;
	private final int rowsOfBricks;
	private final int brickInRow;
	private Ball ball;
	private Vector2 windowDimensions;
	Counter brickCounter;
	Counter LIVES; //todo what does it man be able to not vhange code to change this
	private UserInputListener inputListener;
	//todo https://courses.campus.gov.il/courses/course-v1:HUJI+ACD_RFP4_ObjectOrientedProgramming_HE+2022_1/courseware/3c5eb13522a844dc90e602010cf74fcb/038e48e9c496488487acdbfc7a2dd2e6/
	private WindowController windowController;
	private LivesManager livesManager;
//	private Sound collisionSound;
	private Renderable puckImage;
	PaddlesManager paddlesManager;
	private Renderable paddleImage;
	private Counter ballExtraPaddleCollision;
//	private Paddle extraPaddle;
	private Renderable heartImage;


	CameraManager cameraManager;
	private ImageReader imageReader;
	private BricksManager bricksManager;
	private BallsManager ballsManager;

//	List<Ball> listOfPuckBalls = new ArrayList<>();;


	public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
		this(windowTitle,windowDimensions,7,8);
	}


	public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int brickInRow, int rowsOfBricks
							  ) {
		super(windowTitle, windowDimensions);
		this.rowsOfBricks = rowsOfBricks;
		this.brickInRow = brickInRow;
	}

	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader,
							   UserInputListener inputListener, WindowController windowController
							   ) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		this.inputListener = inputListener;
		this.windowController = windowController;
		this.imageReader = imageReader;




//		ballExtraPaddleCollision =  new Counter(0);



		//creating background
		createBackground();


//        TODO create initializer for ball, paddle ...
		//creating main ball
//		collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");



//		//creating camera
//		Camera camera = new Camera(ball, Vector2.ZERO, windowController.getWindowDimensions().mult(1.2F),
//				windowController.getWindowDimensions());
//		cameraManager = new CameraManager(this, gameObjects(), brickCounter, camera(), camera,
//				ball);



//		puckImage = imageReader.readImage("assets/mockBall.png", true);






//
//		//creating hearts
//		heartImage = imageReader.readImage("assets/heart.png", true);
//		this.livesManager = new LivesManager(LIVES, gameObjects());
//		livesManager.setLives(windowDimensions, heartImage); //TODO pass to constructor\

//////////////////////////////////////////////////////////////////////////////////////////////////////////////


		windowDimensions = windowController.getWindowDimensions();


		//create paddle
		paddlesManager = new PaddlesManager(imageReader, windowDimensions, gameObjects(), inputListener);
		Paddle mainPaddle = paddlesManager.createMainPaddle();
		mainPaddle.setTag("mainPaddle");//TODO all these maybe change to another different class
		ballExtraPaddleCollision =  paddlesManager.getBallExtraPaddleCollision();


		//create ball

		BallsManager ballsManager = new BallsManager(imageReader, soundReader, windowDimensions,
				gameObjects(), ballExtraPaddleCollision);
		this.ballsManager = ballsManager;
		Ball ball = ballsManager.createBall();
		ball.setTag("mainBall"); //TODO all these maybe change to another different class
		this.ball = ball;





		//creating bricks



		BricksManager bricksManager = new BricksManager(gameObjects(), imageReader, rowsOfBricks, brickInRow
				, windowDimensions);
		this.bricksManager = bricksManager;







		//creating camera

		Camera camera = new Camera(ball, Vector2.ZERO, windowController.getWindowDimensions().mult(1.2F),
				windowController.getWindowDimensions());
		cameraManager = new CameraManager(this, gameObjects(), bricksManager.getBrickCounter(), camera(), camera,
				ball);



		//creating walls
		create_walls();



		//create extra paddle

		Paddle extraPaddle = paddlesManager.createExtraPaddle();
		extraPaddle.setTag("extraPaddle");


		//creating hearts
		livesManager = new LivesManager(gameObjects(), imageReader, windowDimensions);
		livesManager.showLives();

		//create collision strategy manager
		CollisionStrategyManager collisionStrategyManager = new CollisionStrategyManager(gameObjects(),
				bricksManager.getBrickCounter(), ballsManager, paddlesManager, cameraManager, livesManager); //todo put managers in fields?


		bricksManager.initializeBricks(collisionStrategyManager);



//		BasicCollisionStrategy basicCollisionStrategy =
//				new BasicCollisionStrategy( gameObjects(), brickCounter);
//		createBricks(rowsOfBricks, brickInRow, windowDimensions, brickImage); //TODO bricks manager
//		Brick brick = new Brick(new Vector2(50, 50), new Vector2(50, 50), brickImage, basicCollisionStrategy);
//		brickCounter.increment();
//		gameObjects().addGameObject(brick);


		//create special bricks


		//todo dont forget that you winning is not goos bricks dissapear
		//create puck balls



//		Ball puckBall = createPuckBall(puckImage);
//
//		Ball puckBall2 = new Ball(Vector2.ZERO, new Vector2(37.5F, 37.5F), puckImage, collisionSound);
//		gameObjects().addGameObject(puckBall2);
//		Ball puckBall2 = new Ball(Vector2.ZERO, new Vector2(37.5F, 37.5F), puckImage, collisionSound);
//		puckBall1.setVelocity(new Vector2(0, BALL_SPEED));
//		puckBall2.setVelocity(new Vector2(0, BALL_SPEED));
//		randomizeDirectionForBall(puckBall1);
//		randomizeDirectionForBall(puckBall1);

		//create bricks with extra balls
//		createExtraBallsBricks();
//		ExtraBallsCollisionStrategy extraBallsCollisionStrategy =
//				new ExtraBallsCollisionStrategy(gameObjects(), brickCounter, puckBall1, puckBall2);



//		GraphicalLivesManager graphicalLives = new GraphicalLivesManager(gameObjects());
//		graphicalLives.initializeHearts(windowDimensions, heartImage);



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
		Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
		GameObject background = new GameObject(Vector2.ZERO, MY_WINDOW_DIMENSIONS, backgroundImage);
		background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		gameObjects().addGameObject(background, Layer.BACKGROUND);


	}






	public void checkForGameEnd() {
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



	private void createExtraBallsBricks() {


	}


	private void createBricks(int rowsOfBricks, int brickInRow, Vector2 windowDimensions, //make bricks
							  // manager??
							  Renderable brickImage
	){

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
//		this.livesManager.addLivesHeartPaddleCollision();
		if(ballsManager.checkIfBallToHigh(ball)){
			checkForGameEnd();
			//reposition ball
			ball.setCenter(windowDimensions.mult(0.5F));
		}

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
		// TODO make constants
		GameManager gameManager = new BrickerGameManager("Bouncing Ball", new Vector2(700, 500));
		gameManager.run();

		}

}

package Bricker.main;
import Bricker.brick_straegies.BasicCollisionStrategy;
import Bricker.brick_straegies.CollisionStrategy;
import Bricker.gameobjects.AiPaddle;
import Bricker.gameobjects.Brick;
import Bricker.gameobjects.UserPaddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;

import danogl.util.Vector2;
import Bricker.gameobjects.Ball;

import java.awt.*;
import java.util.Random;


public class BrickerGameManager extends GameManager{
	private static final float BALL_SPEED = 200;
	private final int rowsOfBricks;
	private final int brickInRow;
	private final int BRICK_HEIGHT = 15;
	private final int GAP = 5;
	private Ball ball;
	private Vector2 windowDimensions;
	private int lives = 3; //todo what does it man be able to not vhange code to change this
	//todo https://courses.campus.gov.il/courses/course-v1:HUJI+ACD_RFP4_ObjectOrientedProgramming_HE+2022_1/courseware/3c5eb13522a844dc90e602010cf74fcb/038e48e9c496488487acdbfc7a2dd2e6/
	private WindowController windowController;


	public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
		super(windowTitle, windowDimensions);
		rowsOfBricks = 7;
		brickInRow = 8;

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
		this.windowController = windowController;
		super.initializeGame(imageReader, soundReader, inputListener, windowController);

		//creating background
		Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
		GameObject background = new GameObject(Vector2.ZERO, new Vector2(700, 500), backgroundImage);
		background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		gameObjects().addGameObject(background, Layer.BACKGROUND);


		//creating ball
		Renderable ballImage = imageReader.readImage("assets/ball.png", true);
		Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");

		Ball ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ballImage, collisionSound);
		ball.setVelocity(new Vector2(0, BALL_SPEED));
		windowDimensions = windowController.getWindowDimensions();
		ball.setCenter(windowDimensions.mult(0.5F));
		float ballVelX = BALL_SPEED;
		float ballVely = BALL_SPEED;
		Random rand = new Random();
		if(rand.nextBoolean()){
			ballVelX *= -1;
			ballVely *= -1;
		}

		ball.setVelocity(new Vector2(ballVelX, ballVely));
		this.ball = ball;
		gameObjects().addGameObject(ball);


		//create paddles
		Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);

		//create user paddle
//		int[]  = {(int) windowDimensions.y()-30, 30};
		UserPaddle userPaddle = new UserPaddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener);
		userPaddle.setCenter(new Vector2(windowDimensions.x()/2,(int) windowDimensions.y()-30));
		gameObjects().addGameObject(userPaddle);



		//creating walls
		GameObject rightWall = new GameObject(new Vector2(699, 0), new Vector2(3, 500),
				new RectangleRenderable(Color.CYAN));
		gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
		GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(3, 500),
				new RectangleRenderable(Color.CYAN));
		gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
		GameObject upperWall = new GameObject(Vector2.ZERO, new Vector2(700, 3),
				new RectangleRenderable(Color.CYAN));
		gameObjects().addGameObject(upperWall, Layer.STATIC_OBJECTS);

		//creating bricks
		Renderable brickImage = imageReader.readImage("assets/brick.png", false);
		BasicCollisionStrategy basicCollisionStrategy =
				new BasicCollisionStrategy( gameObjects());
		createBricks(rowsOfBricks, brickInRow, windowDimensions, brickImage, basicCollisionStrategy);


	}

	@Override
	public void update(float deltaTime) {
//		System.out.println("hi");
		super.update(deltaTime);
		checkForGameEnd();
	}

	private void checkForGameEnd() {
		String prompt = "";
		float ballHeight = ball.getCenter().y();
		if(ballHeight<0){
			lives--;
			if (lives==0){ //we lose
				prompt = "You Lose! Play again?";
				if(windowController.openYesNoDialog(prompt)){
					windowController.resetGame();
				}
				else{
					windowController.closeWindow();
				}
			}

		}
	}

	private void createBricks(int rowsOfBricks, int brickInRow, Vector2 windowDimensions,
							  Renderable brickImage, CollisionStrategy strategy
	){
		float windowWidth = windowDimensions.x();
		int totalGapWidth = (brickInRow - 1) * GAP;
		float brickWidth = (windowWidth - totalGapWidth) / brickInRow; // Calculate width dynamically
		float totalBricksWidth = brickInRow * brickWidth + totalGapWidth;
		float startX = (windowWidth - totalBricksWidth) / 2;
		float startY = 0;


		for (int row = 0; row < rowsOfBricks; row++) {
			for (int col = 0; col < brickInRow; col++) {
				float x = startX + col * (brickWidth + GAP);
				float y = startY + row * (BRICK_HEIGHT + GAP);
				Brick brick = new Brick(new Vector2(x, y), new Vector2(brickWidth, BRICK_HEIGHT), brickImage,
						strategy);
				gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);

			}
		}


	}


	public static void main(String[] args) {
		GameManager gameManager = new BrickerGameManager("Bouncing Ball", new Vector2(700, 500));
		gameManager.run();


		}

}

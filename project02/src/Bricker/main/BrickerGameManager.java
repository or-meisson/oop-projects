package Bricker.main;
//import Bricker.GraphicalLives;
import Bricker.LivesManager;
import Bricker.brick_straegies.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BrickerGameManager extends GameManager{
	private static final float BALL_SPEED = 200;
	private final int rowsOfBricks;
	private final int brickInRow;
	private final int BRICK_HEIGHT = 15;
	private final int GAP = 5;
	private Ball ball;
	private Vector2 windowDimensions;
	Counter brickCounter;
	Counter LIVES; //todo what does it man be able to not vhange code to change this
	private UserInputListener inputListener;
	//todo https://courses.campus.gov.il/courses/course-v1:HUJI+ACD_RFP4_ObjectOrientedProgramming_HE+2022_1/courseware/3c5eb13522a844dc90e602010cf74fcb/038e48e9c496488487acdbfc7a2dd2e6/
	private WindowController windowController;
	private LivesManager livesManager;
	private Sound collisionSound;
	private Renderable puckImage;
	private Renderable paddleImage;
	private Counter ballExtraPaddleCollision;
	private Paddle extraPaddle;
	private Renderable heartImage;

	CameraManager cameraManager;

	List<Ball> listOfPuckBalls = new ArrayList<>();;


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
		brickCounter = new Counter(0);
		ballExtraPaddleCollision =  new Counter(0);

 		LIVES = new Counter(3); //TODO MAYBE PUT IN LIVES MANAGER?
		this.inputListener = inputListener;
		this.windowController = windowController;

		super.initializeGame(imageReader, soundReader, inputListener, windowController);

		//creating background
		Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
		GameObject background = new GameObject(Vector2.ZERO, new Vector2(700, 500), backgroundImage);
		background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		gameObjects().addGameObject(background, Layer.BACKGROUND);


		//creating main ball
		Renderable ballImage = imageReader.readImage("assets/ball.png", true);
		collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");

		Ball ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ballImage, collisionSound, ballExtraPaddleCollision);
		ball.setTag("mainBall"); //all these maybe change to another different class
//		System.out.println(ball.getTag());
		ball.setVelocity(new Vector2(0, BALL_SPEED));
		windowDimensions = windowController.getWindowDimensions();
		ball.setCenter(windowDimensions.mult(0.5F));
		randomizeDirectionForBall(ball);

		this.ball = ball;
		gameObjects().addGameObject(ball);


		//create paddles
		paddleImage = imageReader.readImage("assets/paddle.png", true);


		Paddle userPaddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener, false);
		userPaddle.setTag("mainPaddle");

		userPaddle.setCenter(new Vector2(windowDimensions.x()/2,(int) windowDimensions.y()-30));
		gameObjects().addGameObject(userPaddle);

		//creating camera
		Camera camera = new Camera(ball, Vector2.ZERO, windowController.getWindowDimensions().mult(1.2F),
				windowController.getWindowDimensions());
		cameraManager = new CameraManager(this, gameObjects(), brickCounter, camera(), camera,
				ball);

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

		puckImage = imageReader.readImage("assets/mockBall.png", true);



		extraPaddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener,
				true);
		extraPaddle.setTag("extraPaddle");

		extraPaddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()/2));



		//creating hearts
		heartImage = imageReader.readImage("assets/heart.png", true);
		this.livesManager = new LivesManager(LIVES, gameObjects());
		livesManager.setLives(windowDimensions, heartImage); //TODO THIS HEARTS ARE COLLIDING EITH BALL!!



		//creating bricks
		Renderable brickImage = imageReader.readImage("assets/brick.png", false);
		BasicCollisionStrategy basicCollisionStrategy =
				new BasicCollisionStrategy( gameObjects(), brickCounter);
		createBricks(rowsOfBricks, brickInRow, windowDimensions, brickImage);
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



	Ball createPuckBall(Renderable puckImage){
		Ball puckBall1 = new Ball(Vector2.ZERO, new Vector2(37.5F, 37.5F), puckImage, collisionSound, ballExtraPaddleCollision);
		listOfPuckBalls.add(puckBall1);
		puckBall1.setVelocity(new Vector2(0, BALL_SPEED));
		randomizeDirectionForBall(puckBall1);
		return puckBall1;
	}

	CollisionStrategy randomizeCollisionStrategy() { //do i really want the pucks here?
		int randomNumber = new Random().nextInt(100); // Generate a random number between 0 and 99
//		Renderable puckImage = imageReader.readImage("assets/mockBall.png", true);

		if (randomNumber < 50) {
			return new BasicCollisionStrategy(gameObjects(), brickCounter);
		}
		return randomizeSpecialCollisionStrategyOutOf5();


	}


	private CollisionStrategy randomizeSpecialCollisionStrategyOutOf4() {


		int randomIndex = new Random().nextInt(4); // Generate a random number between 0 and 3
		switch (randomIndex) {
			case 0:
				//create puck balls
				Ball puck1 = createPuckBall(puckImage);
				Ball puck2 = createPuckBall(puckImage);
				puck1.setTag("puckBall");
				puck2.setTag("puckBall");

				return new ExtraBallsCollisionStrategy(gameObjects(), brickCounter, puck1, puck2);
			case 1:
				//strategy 3
				return new ExtraPaddleCollisionStrategy(gameObjects(), brickCounter, extraPaddle);
			case 2:
				//strategy 4
//				setCamera();

//				System.out.println(ball.getTag());


				return new CameraCollisionStrategy(gameObjects(), brickCounter, cameraManager);

//				return new Collision4();
			case 3:
				//strategy 4
				Heart fallingHeart = this.livesManager.createFallingHeart();
				return new ReturnLifeCollisionStrategy(gameObjects(), brickCounter, fallingHeart);
//			return new Collision4();

		}
		return null;
	}



	private CollisionStrategy randomizeSpecialCollisionStrategyOutOf5() {
			int randomIndex = new Random().nextInt(5); // Generate a random number between 0 and 4
			switch (randomIndex) {
				case 0:
				case 1:
				case 2:
				case 3:
					return randomizeSpecialCollisionStrategyOutOf4();

				case 4:
					//strategy 5
//					boolean isSecondLayerOfSpecialStrategy = false;
					List<CollisionStrategy> strategies = new ArrayList<>();

					CollisionStrategy firstStrategy = randomizeSpecialCollisionStrategyOutOf5();
					if(firstStrategy.getStrategyType().equals("extraStrategy")){
						CollisionStrategy strategy1 = randomizeSpecialCollisionStrategyOutOf4();
						CollisionStrategy strategy2 = randomizeSpecialCollisionStrategyOutOf4();
						CollisionStrategy strategy3 = randomizeSpecialCollisionStrategyOutOf4();
						strategy2.setExtraStrategy(true);
						strategy3.setExtraStrategy(true);
						strategies.add(strategy1);
						strategies.add(strategy2);
						strategies.add(strategy3);
					}
					else{
						strategies.add(firstStrategy);
						CollisionStrategy strategy1 = randomizeSpecialCollisionStrategyOutOf5();
						if(strategy1.getStrategyType().equals("extraStrategy")){
							CollisionStrategy strategy2 = randomizeSpecialCollisionStrategyOutOf4();
							CollisionStrategy strategy3 = randomizeSpecialCollisionStrategyOutOf4();
							strategy2.setExtraStrategy(true);
							strategy3.setExtraStrategy(true);
							strategies.add(strategy2);
							strategies.add(strategy3);

						}
						else{
							strategies.add(strategy1);
							strategy1.setExtraStrategy(true);

						}

					}
//					strategies.forEach(System.out::println);
					return new ExtraStrategyCollisionStrategy(gameObjects(), brickCounter, strategies);



			}





	return null;

		}



	private void randomizeDirectionForBall(Ball ball) {
		float ballVelX = BALL_SPEED;
		float ballVelY = BALL_SPEED;
		Random rand = new Random();
		if(rand.nextBoolean()){
			ballVelX *= -1;
			ballVelY *= -1;
		}
		ball.setVelocity(new Vector2(ballVelX, ballVelY));
	}

	private void createExtraBallsBricks() {
	}


	private void createBricks(int rowsOfBricks, int brickInRow, Vector2 windowDimensions,
							  Renderable brickImage
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
				CollisionStrategy collisionStrategy = randomizeCollisionStrategy();
				Brick brick = new Brick(new Vector2(x, y), new Vector2(brickWidth, BRICK_HEIGHT), brickImage,
						collisionStrategy);
				brickCounter.increment();
				gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
			}
		}
	}

	@Override
	public void update(float deltaTime) {
//		LivesManager livesManager = new LivesManager(LIVES, gameObjects());

//		System.out.println(g);
		super.update(deltaTime);
//		System.out.println(brickCounter.value());
//		System.out.println("changed in update game mangaer");
		if( brickCounter.value()<= 0){
			makeWinGame();
		}
		checkForPressedW();
		float ballHeight = ball.getCenter().y();
		checkIfPuckBallGoneAndRemove();

		this.livesManager.addLivesHeartPaddleCollision();

		this.cameraManager.updateCamera();


		checkIfNeedToRemoveExtraPaddle();
//		this.livesManager.addLivesHeartPaddleCollision();
		if(ballHeight > 500){
			this.livesManager.checkForGameEnd(windowController);
			//reposition ball
			ball.setCenter(windowDimensions.mult(0.5F));
		}

	}

	private void checkIfNeedToRemoveExtraPaddle() {
		if(ballExtraPaddleCollision.value() == 4){
			gameObjects().removeGameObject(extraPaddle);
			extraPaddle.isShowing = false;
		}

	}

	private void checkIfPuckBallGoneAndRemove() {
		for(Ball puckBall : listOfPuckBalls){
			float puckBallHeight = puckBall.getCenter().y();
			if(puckBallHeight > 500){
				gameObjects().removeGameObject(puckBall);
			}
		}

	}


	private void checkForPressedW() {
		if(inputListener.isKeyPressed(KeyEvent.VK_W)){
			makeWinGame();
		}

	}

	private void makeWinGame() {
//		System.out.println("got here");
//		System.out.println(brickCounter.value());

			String prompt = "You win! Play again?";
			if(windowController.openYesNoDialog(prompt)){
				windowController.resetGame();
			}
			else{
				windowController.closeWindow();
			}



	}



	public static void main(String[] args) {
		GameManager gameManager = new BrickerGameManager("Bouncing Ball", new Vector2(700, 500));
		gameManager.run();

		}

}

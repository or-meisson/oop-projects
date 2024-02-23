

package bricker.main;

import bricker.gameobjects.Ball;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The BallsManager class manages the creation, behavior, and removal of balls in the game.
 * It handles the creation of main balls and puck balls, as well as their movement, collision detection,
 * and removal when they reach a certain height.
 *
 * The class also randomizes the initial direction of balls and handles repositioning of the main ball.
 *
 * @author Or Meissonnier
 */
public class BallsManager {

	private static final float BALL_SPEED = 200;
	private static final Vector2 BALL_VELOCITY = new Vector2(0, BALL_SPEED);
	private static final int MAX_BALL_HEIGHT = 500;
	private static final Vector2 MAIN_BALL_DIMENSIONS = new Vector2(50, 50);
	private static final Vector2 PUCK_BALL_DIMENSIONS = new Vector2(37.5F, 37.5F);
	private static final String BALL_IMG_PATH = "assets/ball.png";
	private static final String PUCKBALL_IMG_PATH = "assets/mockBall.png";
	private static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
	private static final float MULT_FACTOR = 0.5F;
	private static final String MAIN_BALL_TAG = "mainBall";
	private static final String EXTRA_PADDLE_TAG = "extraPaddle";
	private static final String PUCK_BALL_TAG = "puckBall";
	private static final int MULT_FACTOR_FOR_ANGLE = 2;
	private final Renderable puckImage;
	private final Renderable ballImage;
	private final Sound collisionSound;
	private final Vector2 windowDimensions;
	private final GameObjectCollection gameObjects;
	private final Counter ballExtraPaddleCollision;
	private final List<Ball> listOfPuckBalls = new ArrayList<>();;

	/**
	 * Constructs a BallsManager with the specified parameters.
	 *
	 * @param imageReader            the image reader for loading images
	 * @param soundReader            the sound reader for loading sounds
	 * @param windowDimensions       the dimensions of the game window
	 * @param gameObjects            the collection of game objects
	 * @param ballExtraPaddleCollision the counter for ball extra paddle collisions
	 */
	public BallsManager(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions,
						GameObjectCollection gameObjects, Counter ballExtraPaddleCollision) {
		ballImage = imageReader.readImage(BALL_IMG_PATH, true);
		puckImage = imageReader.readImage(PUCKBALL_IMG_PATH, true);
		collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);
		this.windowDimensions = windowDimensions;
		this.gameObjects = gameObjects;
		this.ballExtraPaddleCollision = ballExtraPaddleCollision;
	}

	/**
	 * Creates a main ball and adds it to the game.
	 *
	 * @return the created main ball
	 */
	public Ball createBall(){
		Ball ball = new Ball(Vector2.ZERO, MAIN_BALL_DIMENSIONS, ballImage, collisionSound,
				ballExtraPaddleCollision, EXTRA_PADDLE_TAG);
		ball.setTag(MAIN_BALL_TAG);
		ball.setVelocity(BALL_VELOCITY);
		ball.setCenter(windowDimensions.mult(MULT_FACTOR));
		randomizeDirectionForBall(ball);
		gameObjects.addGameObject(ball);
		return ball;
	}

	/**
	 * Creates a puck ball and adds it to the game.
	 *
	 * @return the created puck ball
	 */
	public Ball createPuckBall(){
		Ball puckBall1 = new Ball(Vector2.ZERO, PUCK_BALL_DIMENSIONS, puckImage, collisionSound,
				ballExtraPaddleCollision, EXTRA_PADDLE_TAG);
		puckBall1.setTag(PUCK_BALL_TAG);
		listOfPuckBalls.add(puckBall1);
		puckBall1.setVelocity(BALL_VELOCITY);
		randomizeDirectionForPuckBall(puckBall1);
		return puckBall1;
	}


	/**
	 * Randomizes the direction of movement for the main ball.
	 *
	 * @param ball the main ball
	 */
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

	/**
	 * Randomizes the direction of movement for the puck ball.
	 *
	 * @param ball the puck ball
	 */
	private void randomizeDirectionForPuckBall(Ball ball) {
		// Choose a random angle for the direction
		double angle = Math.random() * MULT_FACTOR_FOR_ANGLE * Math.PI;

		// Calculate velocity components using trigonometry
		float ballVelX = BALL_SPEED * (float)Math.cos(angle);
		float ballVelY = BALL_SPEED * (float)Math.sin(angle);

		// Set the velocity
		ball.setVelocity(new Vector2(ballVelX, ballVelY));
	}

	/**
	 * Checks if any puck ball has gone beyond the maximum height and removes it from the game.
	 */
	public void checkIfPuckBallGoneAndRemove() {
		for(Ball puckBall : listOfPuckBalls){
			float puckBallHeight = puckBall.getCenter().y();
			if(puckBallHeight > MAX_BALL_HEIGHT){
				gameObjects.removeGameObject(puckBall);
			}
		}

	}

	/**
	 * Checks if the main ball has reached the maximum height.
	 *
	 * @param ball the main ball
	 * @return true if the ball has reached the maximum height, otherwise false
	 */
	public boolean checkIfBallTooHigh(Ball ball){
		if(ball.getCenter().y() > MAX_BALL_HEIGHT){
			return true;
		}
		return false;
	}
	/**
	 * Repositions the main ball to the center of the window and randomizes its direction.
	 *
	 * @param ball the main ball
	 */
	public void repositionBall(Ball ball) {
		ball.setCenter(windowDimensions.mult(MULT_FACTOR));
		randomizeDirectionForBall(ball);

	}
}

package Bricker.managers;

import Bricker.gameobjects.Ball;
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

public class BallsManager {

	private static final float BALL_SPEED = 200;
	private static final int MAX_BALL_HEIGHT = 500;
	private static final Vector2 MAIN_BALL_DIMENSIONS = new Vector2(50, 50);
	private static final Vector2 PUCK_BALL_DIMENSIONS = new Vector2(37.5F, 37.5F);
	private static final String BALL_IMG_PATH = "assets/ball.png";
	private static final String PUCKBALL_IMG_PATH = "assets/mockBall.png";
	private static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
	private final Renderable puckImage;
	private final Renderable ballImage;
	private final Sound collisionSound;
	private final Vector2 windowDimensions;
	private final GameObjectCollection gameObjects;
	private final Counter ballExtraPaddleCollision;
	private final List<Ball> listOfPuckBalls = new ArrayList<>();;

	public BallsManager(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions,
						GameObjectCollection gameObjects, Counter ballExtraPaddleCollision) {
		ballImage = imageReader.readImage(BALL_IMG_PATH, true);
		puckImage = imageReader.readImage(PUCKBALL_IMG_PATH, true);
		collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);
		this.windowDimensions = windowDimensions;
		this.gameObjects = gameObjects;
		this.ballExtraPaddleCollision = ballExtraPaddleCollision;
	}
	
	
	public Ball createBall(){
		Ball ball = new Ball(Vector2.ZERO, MAIN_BALL_DIMENSIONS, ballImage, collisionSound,
				ballExtraPaddleCollision, "extraPaddle");
		ball.setTag("mainBall");
		ball.setVelocity(new Vector2(0, BALL_SPEED));
		ball.setCenter(windowDimensions.mult(0.5F));
		randomizeDirectionForBall(ball);
		gameObjects.addGameObject(ball);
		return ball;
	}


	public Ball createPuckBall(){
		Ball puckBall1 = new Ball(Vector2.ZERO, PUCK_BALL_DIMENSIONS, puckImage, collisionSound,
				ballExtraPaddleCollision, "extraPaddle");
		puckBall1.setTag("puckBall");
		listOfPuckBalls.add(puckBall1);
		puckBall1.setVelocity(new Vector2(0, BALL_SPEED));
		randomizeDirectionForPuckBall(puckBall1);
		return puckBall1;
	}



	private void randomizeDirectionForBall(Ball ball) {
		float ballVelX = BALL_SPEED;
		float ballVelY = BALL_SPEED;
		Random rand = new Random();
		if(rand.nextBoolean()){
			ballVelX *= -1; //todo maybe this dofferent
			ballVelY *= -1;
		}
		ball.setVelocity(new Vector2(ballVelX, ballVelY));
	}


	private void randomizeDirectionForPuckBall(Ball ball) {
		// Choose a random angle for the direction
		double angle = Math.random() * 2 * Math.PI;

		// Calculate velocity components using trigonometry
		float ballVelX = BALL_SPEED * (float)Math.cos(angle);
		float ballVelY = BALL_SPEED * (float)Math.sin(angle);

		// Set the velocity
		ball.setVelocity(new Vector2(ballVelX, ballVelY));
	}


	public void checkIfPuckBallGoneAndRemove() {
		for(Ball puckBall : listOfPuckBalls){
			float puckBallHeight = puckBall.getCenter().y();
			if(puckBallHeight > MAX_BALL_HEIGHT){
				gameObjects.removeGameObject(puckBall);
			}
		}

	}

	public boolean checkIfBallToHigh(Ball ball){
		if(ball.getCenter().y() > MAX_BALL_HEIGHT){
			return true;
		}
		return false;
	}

	public void repositionBall(Ball ball) {
		ball.setCenter(windowDimensions.mult(0.5F));
		randomizeDirectionForBall(ball);

	}
}

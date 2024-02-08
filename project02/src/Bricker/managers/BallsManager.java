package Bricker.managers;

import Bricker.gameobjects.Ball;
import Bricker.gameobjects.Paddle;
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
	public static final int MAX_BALL_HEIGHT = 500;
	private Renderable puckImage;
	private Renderable ballImage;
	private Sound collisionSound;
	private float BALL_SIZE = 50; //todo maybe to change to vector instead of float
	private float PUCK_BALL_SIZE = 37.5F;

	private final Vector2 windowDimensions;
	private final GameObjectCollection gameObjects;
	private final Counter ballExtraPaddleCollision;


	private List<Ball> listOfPuckBalls = new ArrayList<>();;

	public BallsManager(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions,
						GameObjectCollection gameObjects, Counter ballExtraPaddleCollision) {
		ballImage = imageReader.readImage("assets/ball.png", true);
		puckImage = imageReader.readImage("assets/mockBall.png", true);
		collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
		this.windowDimensions = windowDimensions;
		this.gameObjects = gameObjects;
		this.ballExtraPaddleCollision = ballExtraPaddleCollision;
	}
	public Ball createBall(){
		Ball ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ballImage, collisionSound, ballExtraPaddleCollision);
		ball.setTag("mainBall"); //all these maybe change to another different class
//		System.out.println(ball.getTag());
		ball.setVelocity(new Vector2(0, BALL_SPEED));
//		windowDimensions = windowController.getWindowDimensions();
		ball.setCenter(windowDimensions.mult(0.5F));
		randomizeDirectionForBall(ball);
		gameObjects.addGameObject(ball);
		return ball;
	}


	Ball createPuckBall(){
		Ball puckBall1 = new Ball(Vector2.ZERO, new Vector2(PUCK_BALL_SIZE, PUCK_BALL_SIZE), puckImage, collisionSound,
				ballExtraPaddleCollision);
		listOfPuckBalls.add(puckBall1);
		puckBall1.setVelocity(new Vector2(0, BALL_SPEED));
		randomizeDirectionForBall(puckBall1);
		return puckBall1;
	}



	private void randomizeDirectionForBall(Ball ball) {
		//todo check if the puck ball goes to all ways
		float ballVelX = BALL_SPEED;
		float ballVelY = BALL_SPEED;
		Random rand = new Random();
		if(rand.nextBoolean()){
			ballVelX *= -1;
			ballVelY *= -1;
		}
		ball.setVelocity(new Vector2(ballVelX, ballVelY));
	}


	public void checkIfPuckBallGoneAndRemove() { //todo maybe puck ball manager?
		for(Ball puckBall : listOfPuckBalls){
			float puckBallHeight = puckBall.getCenter().y();
			if(puckBallHeight > MAX_BALL_HEIGHT){//todo constant
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

}

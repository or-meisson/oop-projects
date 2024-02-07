package Bricker.brick_straegies;

import Bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class ExtraBallsCollisionStrategy implements CollisionStrategy{
	GameObjectCollection gameObjects;
	private Counter brickCounter;
	private final Ball puck1;
	private final Ball puck2;
//	public String strategyType = "extraBalls";
private boolean isExtraStrategy = false;

	public void setExtraStrategy(boolean extraStrategy) {
		isExtraStrategy = extraStrategy;
	}

	public boolean isExtraStrategy() {
		return isExtraStrategy;
	}


	public ExtraBallsCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter, Ball puck1,
									   Ball puck2) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
		this.puck1 = puck1;
		this.puck2 = puck2;
	}

	public String getStrategyType() {
		return "extraBalls";
	}

	@Override
	public void onCollision(GameObject object1, GameObject object2) {
//		System.out.println("collision that caused extra balls");
		if(!isExtraStrategy) { //the main one
			gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);


			//object 1 is the brick
			brickCounter.decrement();
		}

		Vector2 brickPosition = object1.getCenter();
		puck1.setCenter(brickPosition);
		puck2.setCenter(brickPosition);
		gameObjects.addGameObject(puck1);

		gameObjects.addGameObject(puck2);



//		Renderable ballImage = imageReader.readImage("assets/ball.png", true);
//		Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
//
//		Ball ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ballImage, collisionSound);
//		ball.setVelocity(new Vector2(0, BALL_SPEED));
//		windowDimensions = windowController.getWindowDimensions();
//		ball.setCenter(windowDimensions.mult(0.5F));
//		float ballVelX = BALL_SPEED;
//		float ballVely = BALL_SPEED;
//		Random rand = new Random();
//		if(rand.nextBoolean()){
//			ballVelX *= -1;
//			ballVely *= -1;
//		}
//
//		ball.setVelocity(new Vector2(ballVelX, ballVely));
//		this.ball = ball;
//		gameObjects().addGameObject(ball);
//
//		gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
//		brickCounter.decrement();

	}
}

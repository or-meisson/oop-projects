package Bricker.brick_straegies;

import Bricker.gameobjects.Ball;
import Bricker.gameobjects.ExtraPaddle;
import Bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class ExtraPaddleCollisionStrategy implements CollisionStrategy {


	GameObjectCollection gameObjects;
	private Counter brickCounter;
	private final ExtraPaddle extraPaddle;
	private boolean isExtraStrategy = false;

	public void setExtraStrategy(boolean extraStrategy) {
		isExtraStrategy = extraStrategy;
	}

	public boolean isExtraStrategy() {
		return isExtraStrategy;
	}


	public ExtraPaddleCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
										ExtraPaddle extraPaddle) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
		this.extraPaddle = extraPaddle;
	}

	public String getStrategyType() {
		return "extraPaddle";
	}

	@Override
	public void onCollision(GameObject object1, GameObject object2) {
		//object 1 is the brick
//		System.out.println("collision eith brick that makes extra paddle");

		if(!isExtraStrategy) { //the main one

			gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
			brickCounter.decrement();
		}

		if(!extraPaddle.isShowing()){
		extraPaddle.setShowing(true);
		gameObjects.addGameObject(extraPaddle);}

	}
}

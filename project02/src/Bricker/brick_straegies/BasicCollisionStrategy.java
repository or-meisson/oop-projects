package Bricker.brick_straegies;

import Bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy{
	private GameObjectCollection gameObjects;
	private Counter brickCounter;
	private boolean isExtraStrategy = false;

	public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
	}

	public boolean isExtraStrategy() {
		return isExtraStrategy;
	}

	public void setExtraStrategy(boolean extraStrategy) {
		isExtraStrategy = extraStrategy;
	}
//	public String strategyType = ;


	public String getStrategyType() {
		return "basic";
	}

	@Override
	public void onCollision(GameObject object1, GameObject object2) {
		System.out.println("collision with brick detected");
		gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
		brickCounter.decrement();
//		System.out.println(brickCounter.value());


	}
}

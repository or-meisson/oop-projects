package Bricker.brick_straegies;

import Bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy{
	private static final String BASIC_COLLISION_PROMPT = "collision with brick detected";
	private final String strategyType  =  "basic";
	private final GameObjectCollection gameObjects;
	private Counter brickCounter;
	private boolean isExtraStrategy = false;

	public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
//		this.strategyType =  "basic";
	}


	public void setExtraStrategy(boolean extraStrategy) {
		isExtraStrategy = extraStrategy;
	}


	@Override
	public String getStrategyType() {
		return strategyType;
	}

	@Override
	public void onCollision(GameObject object1, GameObject object2) {
		System.out.println(BASIC_COLLISION_PROMPT);
		gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
		brickCounter.decrement();


	}
}

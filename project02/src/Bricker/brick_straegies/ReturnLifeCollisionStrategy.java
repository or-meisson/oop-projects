package Bricker.brick_straegies;

import Bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ReturnLifeCollisionStrategy implements CollisionStrategy{

	private final GameObjectCollection gameObjects;
	private Counter brickCounter;
	private Heart fallingHeart;
	private boolean isExtraStrategy = false;
	private final String strategyType = "returnLife";






	public ReturnLifeCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
									   Heart fallingHeart) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
		this.fallingHeart = fallingHeart;
//		this.strategyType = "returnLife";
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
		if(!isExtraStrategy) { //is the main one

			gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
			brickCounter.decrement();
		}
		Vector2 brickPosition = object1.getCenter();
		fallingHeart.setCenter(brickPosition);
		gameObjects.addGameObject(fallingHeart);


	}
}

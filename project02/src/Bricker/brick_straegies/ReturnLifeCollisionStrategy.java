package Bricker.brick_straegies;

import Bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ReturnLifeCollisionStrategy implements CollisionStrategy{

	GameObjectCollection gameObjects;
	private Counter brickCounter;
	private Heart fallingHeart;
//	public String strategyType = "returnLife";
	private boolean isExtraStrategy = false;

	public void setExtraStrategy(boolean extraStrategy) {
		isExtraStrategy = extraStrategy;
	}

	public boolean isExtraStrategy() {
		return isExtraStrategy;
	}



	public ReturnLifeCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
									   Heart fallingHeart) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
		this.fallingHeart = fallingHeart;
	}

	public String getStrategyType() {
		return "returnLife";
	}


	@Override
	public void onCollision(GameObject object1, GameObject object2) {
		if(!isExtraStrategy) { //is the main one

			gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
			brickCounter.decrement();
		}
		Vector2 brickPosition = object1.getCenter(); //TODO AHOW FUNCTION IN FALLING HEART
		fallingHeart.setCenter(brickPosition);
		gameObjects.addGameObject(fallingHeart);


	}
}

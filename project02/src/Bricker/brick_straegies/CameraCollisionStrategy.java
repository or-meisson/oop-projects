package Bricker.brick_straegies;

import Bricker.managers.CameraManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class CameraCollisionStrategy implements CollisionStrategy{
	private GameObjectCollection gameObjects;
	private Counter brickCounter;
	private final String strategyType = "camera";

	private CameraManager cameraManager;
	private final String tagToNotice;
	private boolean isExtraStrategy = false;



	public CameraCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
								   CameraManager cameraManager, String tagToNotice) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
		this.cameraManager = cameraManager;
//		this.strategyType = "camera";

		this.tagToNotice = tagToNotice;
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
		if(!isExtraStrategy) { //the main one
		gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
		brickCounter.decrement();
		}
		if(object2.getTag().equals(tagToNotice)) {
			cameraManager.setCamera();
		}


	}


}

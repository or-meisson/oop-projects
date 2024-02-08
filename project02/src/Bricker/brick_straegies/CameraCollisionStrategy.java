package Bricker.brick_straegies;

import Bricker.managers.CameraManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class CameraCollisionStrategy implements CollisionStrategy{
	GameObjectCollection gameObjects;
	private Counter brickCounter;
	private CameraManager cameraManager;
	private boolean isExtraStrategy = false;

	public void setExtraStrategy(boolean extraStrategy) {
		isExtraStrategy = extraStrategy;
	}

	public boolean isExtraStrategy() {
		return isExtraStrategy;
	}
//	public String strategyType =

	public String getStrategyType() {
		return "camera";
	}
	public CameraCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
								   CameraManager cameraManager) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
		this.cameraManager = cameraManager;

	}



	@Override
	public void onCollision(GameObject object1, GameObject object2) { //todo get the tag from constructor
		if(!isExtraStrategy) { //the main one


		gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
		brickCounter.decrement();}
//		System.out.println(object2.getTag());
		//object 1 is the brick

		if(object2.getTag().equals("mainBall")) {
//			System.out.println("collided with camera brick, state of camera:");
//			System.out.println(cameraManager.getCamera());
//			if (cameraManager.getCamera() == null) {
				cameraManager.setCamera();
//			}
		}

	}


}

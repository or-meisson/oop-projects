package Bricker.brick_straegies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

public class CameraCollisionStrategy implements CollisionStrategy{
	GameObjectCollection gameObjects;
	private Counter brickCounter;

	public CameraCollisionStrategy(GameObjectCollection gameObjectS, Counter brickCounter) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;

	}
	@Override
	public void onCollision(GameObject object1, GameObject object2) {

		gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
		brickCounter.decrement();

		//object 1 is the brick




	}


}

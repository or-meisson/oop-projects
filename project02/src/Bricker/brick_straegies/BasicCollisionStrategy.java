package Bricker.brick_straegies;

import Bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class BasicCollisionStrategy implements CollisionStrategy{
	GameObjectCollection gameObjects;
	public BasicCollisionStrategy(GameObjectCollection gameObjects) {
		this.gameObjects = gameObjects;
	}

	@Override
	public void onCollision(GameObject object1, GameObject object2) {
		System.out.println("collision with brick detected");
		gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);

	}
}

package Bricker.brick_straegies;

import Bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class returnLifeCollisionStrategy implements CollisionStrategy{

	GameObjectCollection gameObjects;
	private Counter brickCounter;
	private Heart fallingHeart;

	public returnLifeCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
									   Heart fallingHeart) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
		this.fallingHeart = fallingHeart;
	}


	@Override
	public void onCollision(GameObject object1, GameObject object2) {


	}
}

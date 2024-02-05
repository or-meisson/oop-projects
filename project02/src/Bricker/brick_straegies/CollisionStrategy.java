package Bricker.brick_straegies;

import danogl.GameObject;

public interface CollisionStrategy {

	void onCollision(GameObject object1, GameObject object2);
}

package Bricker.brick_straegies;

import danogl.GameObject;
//public boolean isExtraStrategy = false;

public interface CollisionStrategy {
	void setExtraStrategy(boolean extraStrategy);


	void onCollision(GameObject object1, GameObject object2);

	String getStrategyType();
}

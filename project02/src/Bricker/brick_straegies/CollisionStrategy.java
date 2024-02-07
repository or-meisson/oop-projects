package Bricker.brick_straegies;

import danogl.GameObject;
//public boolean isExtraStrategy = false;

public interface CollisionStrategy {
	public void setExtraStrategy(boolean extraStrategy);

	public boolean isExtraStrategy();

	void onCollision(GameObject object1, GameObject object2);

	String getStrategyType();
}

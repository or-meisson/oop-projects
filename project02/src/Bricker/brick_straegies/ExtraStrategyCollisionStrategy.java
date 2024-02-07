package Bricker.brick_straegies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

import java.util.List;

public class ExtraStrategyCollisionStrategy implements CollisionStrategy{


	private final GameObjectCollection gameObjects;
//	public boolean isExtraStrategy = false;

	private final Counter brickCounter;
	private final List<CollisionStrategy> strategies;
//	private String strategyType = "extraStrategy";

	private boolean isExtraStrategy = false;

	public void setExtraStrategy(boolean extraStrategy) {
		isExtraStrategy = extraStrategy;
	}

	public boolean isExtraStrategy() {
		return isExtraStrategy;
	}

//	CollisionStrategy firstStrategy CollisionStrategy secondStrategy
	public ExtraStrategyCollisionStrategy(GameObjectCollection gameObjects,
										  Counter brickCounter, List<CollisionStrategy> strategies) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
		this.strategies = strategies;
	}

	public String getStrategyType() {

		return "extraStrategy";
	}

	@Override
	public void onCollision(GameObject object1, GameObject object2) {

		System.out.println("double collision");
		System.out.println(strategies.size());
		for( CollisionStrategy strategy : strategies){

			System.out.println(strategy.getStrategyType());
			strategy.onCollision(object1, object2);
		}





	}
}

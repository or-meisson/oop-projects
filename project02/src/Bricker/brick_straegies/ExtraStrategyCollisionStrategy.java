package Bricker.brick_straegies;

import danogl.GameObject;


import java.util.List;

public class ExtraStrategyCollisionStrategy implements CollisionStrategy{



	private final List<CollisionStrategy> strategies;
	private final String strategyType = "extraStrategy";

	private boolean isExtraStrategy = false;



//	CollisionStrategy firstStrategy CollisionStrategy secondStrategy
	public ExtraStrategyCollisionStrategy(List<CollisionStrategy> strategies) {
		this.strategies = strategies;
//		this.strategyType = "extraStrategy";
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

//		System.out.println("double collision");
//		System.out.println(strategies.size());
		for( CollisionStrategy strategy : strategies){
//			System.out.println(strategy.getStrategyType());
			strategy.onCollision(object1, object2);
		}

	}
}

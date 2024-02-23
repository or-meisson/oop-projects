
package bricker.brick_strategies;

import danogl.GameObject;


import java.util.List;

/**
 * Represents a collision strategy for handling several strategies upon collision.
 *
 * @author Or Meissonnier
 *
 */
public class ExtraStrategyCollisionStrategy implements CollisionStrategy{



	private final List<CollisionStrategy> strategies;
	private final String strategyType = "extraStrategy";



	/**
	 * Constructs an ExtraStrategyCollisionStrategy with the specified parameters.
	 *
	 * @param strategies List of strategies to handle upon collision.
	 */
	public ExtraStrategyCollisionStrategy(List<CollisionStrategy> strategies) {
		this.strategies = strategies;
	}


	/**
	 * Retrieves the type of this collision strategy.
	 *
	 * @return The strategy type.
	 */
	@Override
	public String getStrategyType() {
		return strategyType;
	}

	/**
	 * Handles the collision event between two objects.
	 *
	 * @param object1 The first object involved in the collision.
	 * @param object2 The second object involved in the collision.
	 */
	@Override
	public void onCollision(GameObject object1, GameObject object2) {

		for( CollisionStrategy strategy : strategies){
			strategy.onCollision(object1, object2);
		}

	}
}

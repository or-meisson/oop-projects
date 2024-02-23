
package bricker.brick_strategies;

import danogl.GameObject;

/**
 * The CollisionStrategy interface represents a strategy for handling collisions between game objects.
 * Implementations of this interface define specific collision behaviors that can be applied to different
 * types of game objects.
 */
public interface CollisionStrategy {



	/**
	 * Handles the collision between two game objects according to the defined strategy.
	 *
	 * @param object1 The first game object involved in the collision.
	 * @param object2 The second game object involved in the collision.
	 */
	void onCollision(GameObject object1, GameObject object2);

	/**
	 * Retrieves the type of collision strategy.
	 *
	 * @return A string representing the type of collision strategy.
	 */
	String getStrategyType();

}

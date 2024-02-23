
package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * The BasicCollisionStrategy class implements the CollisionStrategy interface and defines
 * basic collision behavior between game objects, particularly between a brick and another object.
 * It removes the brick upon collision and decrements the brick counter.
 *
 * @author Or Meissonnier
 */
public class BasicCollisionStrategy implements CollisionStrategy {
	private final String strategyType = "basic";
	private final GameObjectCollection gameObjects;
	private final Counter brickCounter;


	/**
	 * Constructor for BasicCollisionStrategy.
	 *
	 * @param gameObjects  The collection of game objects.
	 * @param brickCounter Counter for tracking the number of bricks.
	 */
	public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
	}


	/**
	 * Retrieves the type of strategy.
	 *
	 * @return The type of strategy.
	 */
	@Override
	public String getStrategyType() {
		return strategyType;
	}




	/**
	 * Handles collision between two game objects.
	 *
	 * @param object1 The first game object involved in the collision.
	 * @param object2 The second game object involved in the collision.
	 */
	@Override
	public void onCollision(GameObject object1, GameObject object2) {
		if (gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS)) {
			brickCounter.decrement();


		}
	}
}

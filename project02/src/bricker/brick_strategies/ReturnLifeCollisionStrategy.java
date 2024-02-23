
package bricker.brick_strategies;

import bricker.gameobjects.FallingHeart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a collision strategy for handling returned lives upon collision.
 *
 * @author Or Meissonnier
 *
 */
public class ReturnLifeCollisionStrategy extends BasicCollisionStrategy{

	private final GameObjectCollection gameObjects;
	private final FallingHeart fallingHeart;
	private final String strategyType = "returnLife";


	/**
	 * Constructs a ReturnLifeCollisionStrategy with the specified parameters.
	 *
	 * @param gameObjects  The collection of game objects.
	 * @param brickCounter Counter for tracking bricks.
	 * @param fallingHeart The falling heart GameObject.
	 */
	public ReturnLifeCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
									   FallingHeart fallingHeart) {
		super(gameObjects, brickCounter);
		this.gameObjects = gameObjects;
		this.fallingHeart = fallingHeart;
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

		super.onCollision(object1, object2);
		Vector2 brickPosition = object1.getCenter();
		fallingHeart.setCenter(brickPosition);
		gameObjects.addGameObject(fallingHeart);


	}
}

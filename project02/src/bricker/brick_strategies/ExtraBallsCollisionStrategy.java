
package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a collision strategy for handling extra balls upon collision.
 *
 * @author Or Meissonnier
 *
 */
public class ExtraBallsCollisionStrategy extends BasicCollisionStrategy {
	private final String strategyType = "extraBalls";
	private final GameObjectCollection gameObjects;
	private final Ball puck1;
	private final Ball puck2;



	/**
	 * Constructs an ExtraBallsCollisionStrategy.
	 *
	 * @param gameObjects  The collection of game objects.
	 * @param brickCounter Counter for tracking bricks.
	 * @param puck1        The first extra ball.
	 * @param puck2        The second extra ball.
	 */
	public ExtraBallsCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter, Ball puck1,
									   Ball puck2) {
		super(gameObjects, brickCounter);
		this.gameObjects = gameObjects;
		this.puck1 = puck1;
		this.puck2 = puck2;
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
		// collision AND THEM IT EILL BE WITH SUPER
		super.onCollision(object1, object2);
		Vector2 brickPosition = object1.getCenter();
		puck1.setCenter(brickPosition);
		puck2.setCenter(brickPosition);
		gameObjects.addGameObject(puck1);
		gameObjects.addGameObject(puck2);


	}
}

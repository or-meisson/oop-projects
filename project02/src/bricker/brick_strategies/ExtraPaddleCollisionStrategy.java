
package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * Represents a collision strategy for handling extra paddles upon collision.
 *
 * @author Or Meissonnier
 *
 */
public class ExtraPaddleCollisionStrategy extends BasicCollisionStrategy {


	private final GameObjectCollection gameObjects;
	private final ExtraPaddle extraPaddle;
	private final String strategyType = "extraPaddle";



	/**
	 * Constructs an ExtraPaddleCollisionStrategy with the specified parameters.
	 *
	 * @param gameObjects Collection of all game objects
	 * @param brickCounter Counter for bricks in the game
	 * @param extraPaddle ExtraPaddle GameObject
	 */
	public ExtraPaddleCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
										ExtraPaddle extraPaddle) {
		super(gameObjects, brickCounter);
		this.gameObjects = gameObjects;
		this.extraPaddle = extraPaddle;
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
		//object 1 is the brick
		super.onCollision(object1, object2);

		if(!extraPaddle.isShowing()){
		extraPaddle.setShowing(true);
		gameObjects.addGameObject(extraPaddle);}

	}
}

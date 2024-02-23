
package bricker.brick_strategies;

import bricker.main.CameraManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * Represents a collision strategy for triggering camera actions upon collision.
 *
 * @author Or Meissonnier
 *
 */
public class CameraCollisionStrategy extends BasicCollisionStrategy{
	private final String strategyType = "camera";
	private final CameraManager cameraManager;
	private final String tagToNotice;


	/**
	 * Constructs a CameraCollisionStrategy.
	 *
	 * @param gameObjects  The collection of game objects.
	 * @param brickCounter Counter for tracking bricks.
	 * @param cameraManager The manager for controlling the camera.
	 * @param tagToNotice  The tag of the object to notice for camera activation.
	 */
	public CameraCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter,
								   CameraManager cameraManager, String tagToNotice) {
		super(gameObjects, brickCounter);
		this.cameraManager = cameraManager;
		this.tagToNotice = tagToNotice;
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
		if(object2.getTag().equals(tagToNotice)) {
			cameraManager.setCamera();
		}


	}


}

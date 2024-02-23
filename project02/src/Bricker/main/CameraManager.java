
package bricker.main;

import bricker.gameobjects.Ball;
import danogl.GameManager;
import danogl.gui.rendering.Camera;

/**
 * The CameraManager class manages the behavior of the camera in the game.
 * It handles setting and updating the camera based on the ball's collisions.
 * If the ball collides a certain number of times, the camera is set to null, allowing
 * the default camera behavior to resume.
 *
 * The class is associated with a GameManager instance, a Camera, and a Ball.
 * Upon initialization, the camera is set to null until the ball reaches a specified
 * number of collisions.
 *
 * @author Or Meissonnier
 */
public class CameraManager {
	private static final int HITS_NUM_TILL_CAMERAS_GETS_NULL = 4;
	private final GameManager gameManager;
	private final Camera camera;
	private final Ball ball;
	private int ballCounterBeforeCameraSet;

	/**
	 * Constructs a CameraManager with the specified GameManager, Camera, and Ball.
	 *
	 * @param gameManager the game manager instance
	 * @param camera      the camera to manage
	 * @param ball        the ball to track collisions
	 */
	public CameraManager(GameManager gameManager, Camera camera, Ball ball) {
		this.gameManager = gameManager;
		this.camera = camera;
		this.ball = ball;

	}


	/**
	 * Sets the camera to the specified camera instance.
	 * This method is called when the ball reaches the required number of collisions.
	 */
	public void setCamera(){
		if (this.gameManager.camera()==null){
		this.gameManager.setCamera(this.camera);
		ballCounterBeforeCameraSet = ball.getCollisionCounter();}
	}

	/**
	 * Updates the camera based on the ball's collision count.
	 * If the ball has collided a certain number of times since the camera was set, the camera is set to null.
	 */
	public void updateCamera(){
		int currentBallCounter = ball.getCollisionCounter();
		if((currentBallCounter - ballCounterBeforeCameraSet) > HITS_NUM_TILL_CAMERAS_GETS_NULL){
			gameManager.setCamera(null);
		}
	}
}

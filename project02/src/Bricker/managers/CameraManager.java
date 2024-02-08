package Bricker.managers;

import Bricker.gameobjects.Ball;
import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;

public class CameraManager {
	private static final int HITS_NUM_TILL_CAMERAS_GETS_NULL = 4;
	private final GameManager gameManager;
	private final Camera camera;
	private final Ball ball;
	private int ballCounterBeforeCameraSet;

	public CameraManager(GameManager gameManager, Camera camera, Ball ball) {
		this.gameManager = gameManager;
		this.camera = camera;
		this.ball = ball;

	}



	public void setCamera(){
		if (this.gameManager.camera()==null){
		this.gameManager.setCamera(this.camera);
		ballCounterBeforeCameraSet = ball.getCollisionCounter();}
	}


	public void updateCamera(){
		int currentBallCounter = ball.getCollisionCounter();
		if((currentBallCounter - ballCounterBeforeCameraSet) > HITS_NUM_TILL_CAMERAS_GETS_NULL){
			gameManager.setCamera(null);
		}
	}
}

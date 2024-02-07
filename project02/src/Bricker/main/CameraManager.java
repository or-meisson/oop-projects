package Bricker.main;

import Bricker.gameobjects.Ball;
import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;

public class CameraManager {
	GameManager gameManager;
	GameObjectCollection gameObjects;
	Counter brickCounter;
	Camera camera;
	private Ball ball;
	boolean isCameraSet = false;
	int ballCounterBeforeCameraSet;
	Camera cameraFunc;

	public CameraManager(GameManager gameManager, GameObjectCollection gameObjects, Counter brickCounter,
						 Camera cameraFunc, Camera camera, Ball ball) {
		this.gameManager = gameManager;
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
		this.cameraFunc = cameraFunc;
		this.camera = camera;

		this.ball = ball;
		System.out.println(ball.getTag());

	}

	public Camera getCamera() {
		return cameraFunc;
	}

	public void setCamera(){
		if (this.gameManager.camera()==null){
		this.gameManager.setCamera(this.camera);
		ballCounterBeforeCameraSet = ball.getCollisionCounter();}
//		System.out.println("ball counter when camera is set:");

//		System.out.println(ballCounterBeforeCameraSet);
		
	}
	public void updateCamera(){
		int currentBallCounter = ball.getCollisionCounter();
//		System.out.println("current ball counter:");
//		System.out.println(currentBallCounter);
//		System.out.println("ballCounterBeforeCameraSet");
//		System.out.println(ballCounterBeforeCameraSet);
		if((currentBallCounter - ballCounterBeforeCameraSet) > 4){
			gameManager.setCamera(null);
		}
	}
}

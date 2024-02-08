package Bricker.managers;

import Bricker.gameobjects.Heart;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class LivesManager {
	private static final String HEART_IMG_PATH = "assets/heart.png";
	private final Renderable heartImage;
	private final Counter lives;
	private int LIVES_NUM =3;
	private final Vector2 windowDimensions;
	private final GraphicalLivesManager graphicalLivesManager;
	private final NumericLivesManager numericLivesManager;


	public LivesManager(GameObjectCollection gameObjects, ImageReader imageReader,
						Vector2 windowDimensions) {
		this.lives = new Counter(LIVES_NUM);
		this.windowDimensions = windowDimensions;
		this.graphicalLivesManager = new GraphicalLivesManager(lives, gameObjects);
		this.numericLivesManager = new NumericLivesManager(lives, gameObjects);
		this.heartImage = imageReader.readImage(HEART_IMG_PATH, true);
	}
	public int getLives() {
		return this.lives.value();
	}

	public void decrementLives() {
		this.lives.decrement();
	}

	public void showLives() {
		graphicalLivesManager.initializeLivesHearts(windowDimensions, heartImage);
		numericLivesManager.initializeLivesText();
	}

	public Heart createFallingHeart(){
		return graphicalLivesManager.createFallingHeart();
	}
	
	
	public void addLivesHeartPaddleCollision(){
		this.graphicalLivesManager.checkIfFallingHeartsCollided();
		this.numericLivesManager.handleLivesText();

	}
	public void removeHighFallingHearts(){
		this.graphicalLivesManager.removeFallingHeartsTooHigh();
	}


	public void handleLife() {
		graphicalLivesManager.reduceHearts();
		numericLivesManager.handleLivesText();
	}



}


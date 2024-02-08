package Bricker.managers;

import Bricker.GraphicalLivesManager;
import Bricker.NumericLivesManager;
import Bricker.gameobjects.Heart;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class LivesManager {
	private final Renderable heartImage;
	Counter lives;
	private int LIVES_NUM =3;
	GameObjectCollection gameObjects;
	private final ImageReader imageReader;
	private final Vector2 windowDimensions;
	GraphicalLivesManager graphicalLivesManager;
	private NumericLivesManager numericLivesManager;


	public LivesManager(GameObjectCollection gameObjects, ImageReader imageReader,
						Vector2 windowDimensions) {
		this.lives = new Counter(LIVES_NUM);
		this.gameObjects = gameObjects; 
		this.imageReader = imageReader;
		this.windowDimensions = windowDimensions;
		this.graphicalLivesManager = new GraphicalLivesManager(lives, gameObjects);
		this.numericLivesManager = new NumericLivesManager(lives, gameObjects);
		this.heartImage = imageReader.readImage("assets/heart.png", true);

		
		

	}
	public int getLives() {
		return this.lives.value();
	}

	public void decrementLives() {
		this.lives.decrement();
	}

	public void showLives() {
		graphicalLivesManager.initializeLivesHearts(windowDimensions, heartImage);
		numericLivesManager.initializeLivesText(windowDimensions);
	}

	public Heart createFallingHeart(){
		Heart fallingHeart = graphicalLivesManager.createFallingHeart();
		//todo somthing with the lives in numerix also add it it there ??
		return fallingHeart;
	}
	
	
	public void addLivesHeartPaddleCollision(){
		this.graphicalLivesManager.checkIfFallingHeartsCollided();
		this.numericLivesManager.handleLivesText();

	}


	public void handleLife() {
		graphicalLivesManager.reduceHearts();
		numericLivesManager.handleLivesText();
	}
}


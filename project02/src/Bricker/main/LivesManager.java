

package bricker.main;

import bricker.gameobjects.FallingHeart;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The LivesManager class manages the lives system in the game. It handles the graphical representation
 * of lives using heart icons as well as numeric representation of lives. The class coordinates between
 * graphical and numeric lives managers to update and display the current lives count.
 *
 * @author Or Meissonnier
 */
public class LivesManager {
	private static final String HEART_IMG_PATH = "assets/heart.png";
	private final Renderable heartImage;
	private final Counter lives;
	private int LIVES_NUM =3;
	private final Vector2 windowDimensions;
	private final GraphicalLivesManager graphicalLivesManager;
	private final NumericLivesManager numericLivesManager;

	/**
	 * Constructs a LivesManager with the specified game object collection,
	 * image reader, and window dimensions.
	 *
	 * @param gameObjects     the collection of game objects
	 * @param imageReader     the image reader for loading heart image
	 * @param windowDimensions  the dimensions of the game window
	 */
	public LivesManager(GameObjectCollection gameObjects, ImageReader imageReader,
						Vector2 windowDimensions) {
		this.lives = new Counter(LIVES_NUM);
		this.windowDimensions = windowDimensions;
		this.graphicalLivesManager = new GraphicalLivesManager(lives, gameObjects);
		this.numericLivesManager = new NumericLivesManager(lives, gameObjects);
		this.heartImage = imageReader.readImage(HEART_IMG_PATH, true);
	}

	/**
	 * Gets the current number of lives.
	 *
	 * @return the current number of lives
	 */
	public int getLives() {
		return this.lives.value();
	}

	/**
	 * Decrements the number of lives.
	 */
	public void decrementLives() {
		this.lives.decrement();
	}

	/**
	 * Displays the lives count using graphical and numeric representations.
	 */
	public void showLives() {
		graphicalLivesManager.initializeLivesHearts(windowDimensions, heartImage);
		numericLivesManager.initializeLivesText();
	}

	/**
	 * Creates a falling heart object.
	 *
	 * @return the falling heart object created
	 */
	public FallingHeart createFallingHeart(){
		return graphicalLivesManager.createFallingHeart();
	}

	/**
	 * Checks for collision between falling hearts and paddle, and updates the lives accordingly.
	 */
	public void addLivesHeartPaddleCollision(){
		this.graphicalLivesManager.checkIfFallingHeartsCollided();
		this.numericLivesManager.handleLivesText();

	}

	/**
	 * Removes falling hearts that exceed the maximum falling height.
	 */
	public void removeHighFallingHearts(){
		this.graphicalLivesManager.removeFallingHeartsTooHigh();
	}

	/**
	 * Handles life by reducing hearts and updating the numeric representation of lives.
	 */
	public void handleLife() {
		graphicalLivesManager.reduceHearts();
		numericLivesManager.handleLivesText();
	}



}


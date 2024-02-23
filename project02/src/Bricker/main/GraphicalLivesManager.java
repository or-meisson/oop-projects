
package bricker.main;

import bricker.gameobjects.FallingHeart;
import bricker.gameobjects.Heart;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * The GraphicalLivesManager class manages the graphical representation of lives in the game.
 * It handles the initialization of heart icons representing player lives, as well as the creation
 * and management of falling heart objects. Falling hearts can be collected by the player to gain
 * additional lives.
 *
 * The class is associated with a Counter for tracking lives, a GameObjectCollection for managing
 * game objects, and various parameters for heart icons and falling hearts.
 *
 * @author Or Meissonnier
 */
public class GraphicalLivesManager{
	private static final Vector2 FALLING_HEART_VELOCITY = new Vector2(0, 100);
	private static final int MAX_LIVES_NUM = 4;
	private static final String MAIN_PADDLE_TAG = "mainPaddle";
	private static final Vector2 TOP_LEFT_CORNER = new Vector2(0, 0);
	private final Counter lives;
	private final GameObjectCollection gameObjects;
	private final int heartWidth = 30;
	private final int heartHeight = 30;
	private final int gap = 10;
	private static final int MAX_FALLING_HEART_HEIGHT = 500;
	private float startY;
	private Renderable heartImage;
	private final List<FallingHeart> listOfFallingHearts = new ArrayList<>();
	private final List<Heart> listOfHearts = new ArrayList<>();


	/**
	 * Constructs a GraphicalLivesManager with the specified lives counter and game object collection.
	 *
	 * @param lives        the counter for tracking lives
	 * @param gameObjects  the collection of game objects
	 */
	public GraphicalLivesManager(Counter lives, GameObjectCollection gameObjects) {
		this.gameObjects = gameObjects;
		this.lives = lives;
	}

	/**
	 * Reduces the number of hearts displayed on the UI to reflect the decreased number of lives.
	 */
	public void reduceHearts(){
		int lastIndex = listOfHearts.size() - 1;
		Heart lastHeart = listOfHearts.remove(lastIndex);
		gameObjects.removeGameObject(lastHeart, Layer.UI);

	}

	/**
	 * Initializes the graphical representation of lives hearts on the game UI.
	 *
	 * @param windowDimensions  the dimensions of the game window
	 * @param heartImage        the image used for representing hearts
	 */
	public void initializeLivesHearts(Vector2 windowDimensions, Renderable heartImage) {
		this.heartImage = heartImage;
		float windowHeight = windowDimensions.y();
		startY = windowHeight - heartHeight - gap;

		for (int i = 0; i < lives.value(); i++) {

			Heart heart = new Heart(getNewHeartSpot(i), new Vector2(heartWidth, heartHeight), heartImage);
			listOfHearts.add(heart);
			gameObjects.addGameObject(heart, Layer.UI);
		}
	}


	/**
	 * Generates a new spot for placing a heart on the UI based on its position index.
	 *
	 * @param heartNum  the index of the heart in the list of hearts
	 * @return          the position vector for the new heart spot
	 */
	private Vector2 getNewHeartSpot(int heartNum){
		float x = gap + heartNum * (heartWidth + gap);
		float y = startY;
		return new Vector2(x,y);
	}

	/**
	 * Generates a falling heart object and adds it to the list of falling hearts.
	 *
	 * @return the falling heart object created
	 */
	public FallingHeart createFallingHeart(){
		FallingHeart fallingHeart = new FallingHeart(TOP_LEFT_CORNER,
				new Vector2(heartWidth, heartHeight),
				heartImage, MAIN_PADDLE_TAG);
		listOfFallingHearts.add(fallingHeart);
		fallingHeart.setVelocity(FALLING_HEART_VELOCITY);
		return fallingHeart;
	}

	/**
	 * Checks if any falling hearts have collided with the paddle.
	 * If so, adds a new life heart to the UI and removes the falling heart.
	 */
	public void checkIfFallingHeartsCollided(){
		List<FallingHeart> listOfHeartsToRemove = new ArrayList<>();
		for (FallingHeart fallingHeart : listOfFallingHearts){
			if (fallingHeart.isHasCollidedWithPaddle()){ //make the falling heart a lives heart
				if(lives.value() < MAX_LIVES_NUM){
					lives.increment();
					Heart heart = new Heart(getNewHeartSpot(lives.value()-1), new Vector2(heartWidth,
							heartHeight),
							heartImage); //forth heart
					listOfHearts.add(heart);
					gameObjects.addGameObject(heart,Layer.UI);
				}
				listOfHeartsToRemove.add(fallingHeart);
				gameObjects.removeGameObject(fallingHeart);
			}
		}
		for(FallingHeart fallingHeart: listOfHeartsToRemove){
			listOfFallingHearts.remove(fallingHeart);
		}

	}

	/**
	 * Removes falling hearts that have reached a height beyond the maximum falling height.
	 */
	public void removeFallingHeartsTooHigh(){
		for(FallingHeart fallingHeart : listOfFallingHearts)
			if(fallingHeart.getCenter().y() > MAX_FALLING_HEART_HEIGHT){
				gameObjects.removeGameObject(fallingHeart);
			}

	}






}

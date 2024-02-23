
package bricker.main;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The NumericLivesManager class handles the numeric representation of lives in the game.
 * It creates and updates a text renderable object to display the current number of lives remaining.
 * The color of the text changes based
 * on the number of lives.
 *
 * @author Or Meissonnier
 *
 */
public class NumericLivesManager {
	private static final Vector2 LIVES_TEXT_TOP_LEFT_CORNER = new Vector2(670, 470);
	private static final Vector2 LIVES_TEXT_DIMENSIONS = new Vector2(30, 30);
	private final Counter lives;
	private final GameObjectCollection gameObjects;
	private final TextRenderable livesTextRenderable;

	/**
	 * Constructs a NumericLivesManager with the specified lives counter and game object collection.
	 *
	 * @param lives        the counter for tracking lives
	 * @param gameObjects  the collection of game objects
	 * @author Or Meissonnier
	 */
	public NumericLivesManager(Counter lives, GameObjectCollection gameObjects) {
		this.lives = lives;
		this.gameObjects = gameObjects;
		this.livesTextRenderable = new TextRenderable(Integer.toString(lives.value()));
	}

	/**
	 * Initializes the text renderable for displaying lives count.
	 */
	public void initializeLivesText(){
		livesTextRenderable.setColor(Color.GREEN);
		GameObject livesText = new GameObject(LIVES_TEXT_TOP_LEFT_CORNER, LIVES_TEXT_DIMENSIONS,
				livesTextRenderable);
		gameObjects.addGameObject(livesText,Layer.UI);
	}


	/**
	 * Handles the update of the lives text based on the current number of lives.
	 * Adjusts the text string and color accordingly.
	 */
	public void handleLivesText(){
		if(lives.value()==4){
			livesTextRenderable.setString("4");
			livesTextRenderable.setColor(Color.GREEN); //todo constant?
		}
		if(lives.value()==3){
			livesTextRenderable.setString("3");
			livesTextRenderable.setColor(Color.GREEN);

		}
		if(lives.value()==2){
			livesTextRenderable.setString("2");
			livesTextRenderable.setColor(Color.YELLOW);
		}
		if (lives.value() == 1){
			livesTextRenderable.setString("1");
			livesTextRenderable.setColor(Color.RED);
		}


	}


}


package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.Brick;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * The BricksManager class handles the creation, initialization, and management of bricks in the game.
 * It generates brick objects based on specified parameters such as the number of rows and bricks
 * in each row, and positions them within the game window. It also tracks the number of remaining bricks
 * and checks
 * if all bricks have been destroyed.
 * The class uses a collision strategy manager to assign random collision strategies to each brick
 * upon initialization.
 *
 * @author Or Meissonnier
 */
public class BricksManager {
	private static final String BRICK_IMG_PATH = "assets/brick.png";
	private static final float CENTER_MULT_FACTOR = 0.5F;
	private final int GAP_BETWEEN_BRICKS = 5;
	private final int BRICK_HEIGHT = 15;
	private final GameObjectCollection gameObjects;
	private final int rowsOfBricks;
	private final int brickInRow;
	private final Vector2 windowDimensions;
	private final Counter brickCounter;
	private final Renderable brickImage;


	/**
	 * Constructs a BricksManager with the specified parameters.
	 *
	 * @param gameObjects      the collection of game objects
	 * @param imageReader      the image reader for loading images
	 * @param rowsOfBricks     the number of rows of bricks
	 * @param brickInRow       the number of bricks in each row
	 * @param windowDimensions the dimensions of the game window
	 */
	public BricksManager(GameObjectCollection gameObjects, ImageReader imageReader,
						 int rowsOfBricks, int brickInRow, Vector2 windowDimensions
						 ) {
		this.gameObjects = gameObjects;
		this.rowsOfBricks = rowsOfBricks;
		this.brickInRow = brickInRow;
		this.windowDimensions = windowDimensions;
		this.brickCounter = new Counter(0);
		this.brickImage = imageReader.readImage(BRICK_IMG_PATH, false);

	}

	/**
	 * Retrieves the counter for tracking the number of bricks.
	 *
	 * @return the counter for bricks
	 */
	public Counter getBrickCounter() {
		return brickCounter;
	}

	/**
	 * Initializes the bricks by creating and positioning them within the game window.
	 * Also assigns random collision strategies to each brick using the provided
	 * collision strategy manager.
	 *
	 * @param collisionStrategyManager the collision strategy manager for assigning
	 *                                   collision strategies
	 */
	public void initializeBricks(CollisionStrategyManager collisionStrategyManager){
		float windowWidth = windowDimensions.x();
		int totalGapWidth = (brickInRow - 1) * GAP_BETWEEN_BRICKS;
		float brickWidth = (windowWidth - totalGapWidth) / brickInRow; // Calculate width dynamically
		float totalBricksWidth = brickInRow * brickWidth + totalGapWidth;
		float startX = (windowWidth - totalBricksWidth) * CENTER_MULT_FACTOR;
		float startY = 0;


		for (int row = 0; row < rowsOfBricks; row++) {
			for (int col = 0; col < brickInRow; col++) {
				float x = startX + col * (brickWidth + GAP_BETWEEN_BRICKS);
				float y = startY + row * (BRICK_HEIGHT + GAP_BETWEEN_BRICKS);
				CollisionStrategy collisionStrategy = collisionStrategyManager.
						randomizeCollisionStrategy();
				Brick brick = new Brick(new Vector2(x, y), new Vector2(brickWidth, BRICK_HEIGHT),
						brickImage, collisionStrategy);
				brickCounter.increment();
				gameObjects.addGameObject(brick, Layer.STATIC_OBJECTS);
			}
		}
	}

	/**
	 * Checks if all bricks have been destroyed.
	 *
	 * @return true if all bricks are gone, otherwise false
	 */
	public boolean areAllBricksGone(){
		if(brickCounter.value() <= 0){
			return true;
		}
		return false;
	}













}

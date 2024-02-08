package Bricker.managers;

import Bricker.brick_straegies.*;
import Bricker.gameobjects.Brick;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;



public class BricksManager {
	private static final String BRICK_IMG_PATH = "assets/brick.png";
	private final int GAP_BETWEEN_BRICKS = 5;
	private final int BRICK_HEIGHT = 15;
	private final GameObjectCollection gameObjects;
	private final int rowsOfBricks;
	private final int brickInRow;
	private final Vector2 windowDimensions;
	private final Counter brickCounter;
	private final Renderable brickImage;



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

	public Counter getBrickCounter() {
		return brickCounter;
	}

	public void initializeBricks(CollisionStrategyManager collisionStrategyManager){
		float windowWidth = windowDimensions.x();
		int totalGapWidth = (brickInRow - 1) * GAP_BETWEEN_BRICKS;
		float brickWidth = (windowWidth - totalGapWidth) / brickInRow; // Calculate width dynamically
		float totalBricksWidth = brickInRow * brickWidth + totalGapWidth;
		float startX = (windowWidth - totalBricksWidth) / 2;
		float startY = 0;


		for (int row = 0; row < rowsOfBricks; row++) {
			for (int col = 0; col < brickInRow; col++) {
				float x = startX + col * (brickWidth + GAP_BETWEEN_BRICKS);
				float y = startY + row * (BRICK_HEIGHT + GAP_BETWEEN_BRICKS);
				CollisionStrategy collisionStrategy = collisionStrategyManager.randomizeCollisionStrategy();
				Brick brick = new Brick(new Vector2(x, y), new Vector2(brickWidth, BRICK_HEIGHT), brickImage,
						collisionStrategy);
				brickCounter.increment();
				gameObjects.addGameObject(brick, Layer.STATIC_OBJECTS);
			}
		}
	}

	public boolean areAllBricksGone(){
		if(brickCounter.value() <= 0){
			return true;
		}
		return false;
	}













}

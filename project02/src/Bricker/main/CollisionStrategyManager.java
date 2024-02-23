
package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.Ball;
import bricker.gameobjects.FallingHeart;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The CollisionStrategyManager class manages the randomization and selection of collision strategies
 * for game objects.
 * It generates collision strategies based on certain conditions and returns them for
 * use in collision resolution.
 * Strategies can include basic collision handling, as well as special strategies involving
 * extra game elements.
 *
 * The class is associated with various game managers and managers for different game objects such as balls,
 * paddles, cameras, and lives.
 * It provides methods for randomizing collision strategies and handling special collision cases.
 *
 * @author Or Meissonnier
 */
public class CollisionStrategyManager {


	private static final String PUCK_BALL_TAG = "puckBall";
	private static final String MAIN_BALL_TAG = "mainBall";
	private static final String EXTRA_STRATEGY_TYPE = "extraStrategy";
	private final Counter brickCounter;
	private final GameObjectCollection gameObjects;
	private final BallsManager ballsManager;
	private final PaddlesManager paddlesManager;
	private final CameraManager cameraManager;
	private final LivesManager livesManager;
	private final int MAX_NUM_TO_RANDOMIZE_STRATEGY = 10;
	private final int MAX_NUM_TO_RANDOMIZE_BASIC_STRATEGY = 5;
	private final int MAX_NUM_TO_RANDOMIZE_SPECIAL_STRATEGY = 4;



	/**
	 * Constructs a CollisionStrategyManager with the specified parameters.
	 *
	 * @param gameObjects      the collection of game objects
	 * @param brickCounter     the counter for tracking bricks
	 * @param ballsManager     the balls manager for managing balls in the game
	 * @param paddlesManager   the paddles manager for managing paddles in the game
	 * @param cameraManager    the camera manager for managing the game camera
	 * @param livesManager     the lives manager for managing lives in the game
	 */
	public CollisionStrategyManager(GameObjectCollection gameObjects, Counter brickCounter,
									BallsManager ballsManager, PaddlesManager paddlesManager,
									CameraManager cameraManager, LivesManager livesManager) {
		this.brickCounter = brickCounter;
		this.gameObjects = gameObjects;
		this.ballsManager = ballsManager;
		this.paddlesManager = paddlesManager;
		this.cameraManager = cameraManager;
		this.livesManager = livesManager;
	}




	/**
	 * Randomizes and returns a collision strategy based on certain conditions.
	 * If the random number generated is less than the threshold for basic strategies,
	 * a basic collision strategy is returned. Otherwise, a special collision strategy is returned.
	 *
	 * @return a collision strategy instance
	 */
	public CollisionStrategy randomizeCollisionStrategy() {
		int randomNumber = new Random().nextInt(MAX_NUM_TO_RANDOMIZE_STRATEGY);
		if (randomNumber < MAX_NUM_TO_RANDOMIZE_BASIC_STRATEGY) {
			return new BasicCollisionStrategy(gameObjects, brickCounter);
		}
		return randomizeSpecialCollisionStrategy();
	}




	/**
	 * Randomizes and returns a special collision strategy, which can include multiple sub-strategies
	 * or extra game elements such as extra balls, paddles, camera effects, or life bonuses.
	 *
	 * @return a special collision strategy instance
	 */
	private CollisionStrategy randomizeSpecialCollisionStrategyWithoutExtraStrategy() {
		int randomIndex = new Random().nextInt(MAX_NUM_TO_RANDOMIZE_SPECIAL_STRATEGY);
		// Generate a random number between 0 and 3
		switch (randomIndex) {
			case 0:
				//create puck balls
				Ball puck1 = ballsManager.createPuckBall();
				Ball puck2 = ballsManager.createPuckBall();
				puck1.setTag(PUCK_BALL_TAG);
				puck2.setTag(PUCK_BALL_TAG);
				return new ExtraBallsCollisionStrategy(gameObjects, brickCounter, puck1, puck2);
			case 1:

				return new ExtraPaddleCollisionStrategy(gameObjects, brickCounter,
						paddlesManager.getExtraPaddle());
			case 2:

				return new CameraCollisionStrategy(gameObjects, brickCounter,
						cameraManager, MAIN_BALL_TAG);

			case 3:
				FallingHeart fallingHeart = this.livesManager.createFallingHeart();
				return new ReturnLifeCollisionStrategy(gameObjects, brickCounter, fallingHeart);

		}
		return null;
	}


	/**
	 * Randomizes and returns a special collision strategy excluding additional extra strategies.
	 * This method is used to avoid infinite recursion in the randomization process.
	 *
	 * @return a special collision strategy instance
	 */
	private CollisionStrategy randomizeSpecialCollisionStrategy() {
		int randomIndex = new Random().nextInt(MAX_NUM_TO_RANDOMIZE_BASIC_STRATEGY);
		switch (randomIndex) {
			case 0:
			case 1:
			case 2:
			case 3:
				return randomizeSpecialCollisionStrategyWithoutExtraStrategy();
			case 4:
				List<CollisionStrategy> strategies = new ArrayList<>();
				CollisionStrategy firstStrategy = randomizeSpecialCollisionStrategy();
				if(firstStrategy.getStrategyType().equals(EXTRA_STRATEGY_TYPE)){
					CollisionStrategy strategy1 = randomizeSpecialCollisionStrategyWithoutExtraStrategy();
					CollisionStrategy strategy2 = randomizeSpecialCollisionStrategyWithoutExtraStrategy();
					CollisionStrategy strategy3 = randomizeSpecialCollisionStrategyWithoutExtraStrategy();
					strategies.add(strategy1);
					strategies.add(strategy2);
					strategies.add(strategy3);
				}
				else{
					strategies.add(firstStrategy);
					CollisionStrategy strategy1 = randomizeSpecialCollisionStrategy();
					if(strategy1.getStrategyType().equals(EXTRA_STRATEGY_TYPE)){
						CollisionStrategy strategy2 =
								randomizeSpecialCollisionStrategyWithoutExtraStrategy();
						CollisionStrategy strategy3 =
								randomizeSpecialCollisionStrategyWithoutExtraStrategy();
						strategies.add(strategy2);
						strategies.add(strategy3);
					}
					else{
						strategies.add(strategy1);
					}
				}
				return new ExtraStrategyCollisionStrategy(strategies);
		}
		return null;
	}
}
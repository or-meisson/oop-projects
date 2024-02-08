package Bricker.managers;

import Bricker.brick_straegies.*;
import Bricker.gameobjects.Ball;
import Bricker.gameobjects.Heart;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollisionStrategyManager {


	private final Counter brickCounter;
	private final GameObjectCollection gameObjects;
	private final BallsManager ballsManager;
	private final PaddlesManager paddlesManager;
	private final CameraManager cameraManager;
	private final LivesManager livesManager;
	private int MAX_NUM_TO_RANDOMIZE_STRATEGY = 10;
	private int MAX_NUM_TO_RANDOMIZE_BASIC_STRATEGY = 5;
	private int MAX_NUM_TO_RANDOMIZE_SPECIAL_STRATEGY = 4;




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





	public CollisionStrategy randomizeCollisionStrategy() {
		int randomNumber = new Random().nextInt(MAX_NUM_TO_RANDOMIZE_STRATEGY);
		if (randomNumber < MAX_NUM_TO_RANDOMIZE_BASIC_STRATEGY) {
			return new BasicCollisionStrategy(gameObjects, brickCounter);
		}
		return randomizeSpecialCollisionStrategy();
	}





	private CollisionStrategy randomizeSpecialCollisionStrategyWithoutExtraStrategy() {
		int randomIndex = new Random().nextInt(MAX_NUM_TO_RANDOMIZE_SPECIAL_STRATEGY); // Generate a random number between 0 and 3
		switch (randomIndex) {
			case 0:
				//create puck balls
				Ball puck1 = ballsManager.createPuckBall();
				Ball puck2 = ballsManager.createPuckBall();
				puck1.setTag("puckBall");
				puck2.setTag("puckBall");
				return new ExtraBallsCollisionStrategy(gameObjects, brickCounter, puck1, puck2);
			case 1:

				return new ExtraPaddleCollisionStrategy(gameObjects, brickCounter, paddlesManager.getExtraPaddle());
			case 2:

				return new CameraCollisionStrategy(gameObjects, brickCounter, cameraManager, "mainBall");

			case 3:
				Heart fallingHeart = this.livesManager.createFallingHeart();
				return new ReturnLifeCollisionStrategy(gameObjects, brickCounter, fallingHeart);

		}
		return null;
	}



	private CollisionStrategy randomizeSpecialCollisionStrategy() {
		int randomIndex = new Random().nextInt(MAX_NUM_TO_RANDOMIZE_BASIC_STRATEGY); // Generate a random number between 0 and 4
		switch (randomIndex) {
			case 0:
			case 1:
			case 2:
			case 3:
				return randomizeSpecialCollisionStrategyWithoutExtraStrategy();

			case 4:
				//strategy 5
				List<CollisionStrategy> strategies = new ArrayList<>();

				CollisionStrategy firstStrategy = randomizeSpecialCollisionStrategy();
				if(firstStrategy.getStrategyType().equals("extraStrategy")){
					// types
					CollisionStrategy strategy1 = randomizeSpecialCollisionStrategyWithoutExtraStrategy();
					CollisionStrategy strategy2 = randomizeSpecialCollisionStrategyWithoutExtraStrategy();
					CollisionStrategy strategy3 = randomizeSpecialCollisionStrategyWithoutExtraStrategy();
					strategy2.setExtraStrategy(true);
					strategy3.setExtraStrategy(true);
					strategies.add(strategy1);
					strategies.add(strategy2);
					strategies.add(strategy3);
				}
				else{
					strategies.add(firstStrategy);
					CollisionStrategy strategy1 = randomizeSpecialCollisionStrategy();
					if(strategy1.getStrategyType().equals("extraStrategy")){
						CollisionStrategy strategy2 = randomizeSpecialCollisionStrategyWithoutExtraStrategy();
						CollisionStrategy strategy3 = randomizeSpecialCollisionStrategyWithoutExtraStrategy();
						strategy2.setExtraStrategy(true);
						strategy3.setExtraStrategy(true);
						strategies.add(strategy2);
						strategies.add(strategy3);

					}
					else{
						strategies.add(strategy1);
						strategy1.setExtraStrategy(true);

					}

				}
				return new ExtraStrategyCollisionStrategy(strategies);



		}

		return null;

	}
}
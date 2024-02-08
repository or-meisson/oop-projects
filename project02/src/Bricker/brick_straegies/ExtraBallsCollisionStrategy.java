package Bricker.brick_straegies;

import Bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class ExtraBallsCollisionStrategy implements CollisionStrategy{
	private final String strategyType = "extraBalls";
	private final GameObjectCollection gameObjects;
	private Counter brickCounter;
	private final Ball puck1;
	private final Ball puck2;
	private boolean isExtraStrategy = false;




	public ExtraBallsCollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter, Ball puck1,
									   Ball puck2) {
		this.gameObjects = gameObjects;
		this.brickCounter = brickCounter;
		this.puck1 = puck1;
		this.puck2 = puck2;
	}


	public void setExtraStrategy(boolean extraStrategy) {
		isExtraStrategy = extraStrategy;
	}


	@Override
	public String getStrategyType() {
		return strategyType;
	}

	@Override
	public void onCollision(GameObject object1, GameObject object2) { // todo maybe do extends basic
		// collision AND THEM IT EILL BE WITH SUPER
		if(!isExtraStrategy) { //the main one
			gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
			brickCounter.decrement();
		}

		Vector2 brickPosition = object1.getCenter();
		puck1.setCenter(brickPosition);
		puck2.setCenter(brickPosition);
		gameObjects.addGameObject(puck1);
		gameObjects.addGameObject(puck2);


	}
}

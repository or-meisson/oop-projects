package Bricker.managers;
//import Bricker.gameobjects.*;

import Bricker.gameobjects.FallingHeart;
import Bricker.gameobjects.Heart;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GraphicalLivesManager{
	private static final Vector2 FALLING_HEART_VELOCITY = new Vector2(0, 100);
	private static final int MAX_LIVES_NUM = 4;
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

	public GraphicalLivesManager(Counter lives, GameObjectCollection gameObjects) {
		this.gameObjects = gameObjects;
		this.lives = lives;
	}

	public void reduceHearts(){
		Heart lastHeart = listOfHearts.removeLast();
		gameObjects.removeGameObject(lastHeart, Layer.UI);

	}

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


	private Vector2 getNewHeartSpot(int heartNum){
		float x = gap + heartNum * (heartWidth + gap);
		float y = startY;
		return new Vector2(x,y);
	}

	public FallingHeart createFallingHeart(){
		FallingHeart fallingHeart = new FallingHeart(new Vector2(0, 0), new Vector2(heartWidth, heartHeight),
				heartImage, "mainPaddle");
		listOfFallingHearts.add(fallingHeart);
		fallingHeart.setVelocity(FALLING_HEART_VELOCITY);
		return fallingHeart;
	}


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

	public void removeFallingHeartsTooHigh(){
		for(FallingHeart fallingHeart : listOfFallingHearts)
			if(fallingHeart.getCenter().y() > MAX_FALLING_HEART_HEIGHT){
				gameObjects.removeGameObject(fallingHeart);
			}

	}






}

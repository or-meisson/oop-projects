package Bricker;
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
	Counter lives;
	private GameObjectCollection gameObjects;
	private int heartWidth = 30;
	private int heartHeight = 30;
	private int gap = 10;

	float startY;
	private Renderable heartImage;
	private List<FallingHeart> listOfFallingHearts = new ArrayList<>();
	private Vector2 emptySpot;

	private List<Heart> listOfHearts = new ArrayList<>(); //todo change to private

	public GraphicalLivesManager(Counter lives, GameObjectCollection gameObjects) {
		this.gameObjects = gameObjects;
		this.lives = lives;
//		this.heartImage = heartImage;
	}

//	public GraphicalLivesManager(int lives, GameObjectCollection gameObjects) {
//		super(lives, gameObjects);
//	}


	public void reduceHearts(){
//		System.out.println(listOfHearts);
		Heart lastHeart = listOfHearts.removeLast();
		gameObjects.removeGameObject(lastHeart, Layer.UI);
//		float startX = gap + (lives.value()-1) * (heartWidth + gap);
//		float startY = windowHeight - heartHeight - gap;
		emptySpot =lastHeart.getTopLeftCorner();

	}

	public void initializeLivesHearts(Vector2 windowDimensions, Renderable heartImage) {
//		System.out.println(44);
		this.heartImage = heartImage;
//		int startX = gap;
		float windowHeight = windowDimensions.y();
		startY = windowHeight - heartHeight - gap;

		for (int i = 0; i < lives.value(); i++) {
//			float x = startX + i * (heartWidth + gap);
//			float y = startY;
//			System.out.println(33333);
			Heart heart = new Heart(getNewHeartSpot(i), new Vector2(heartWidth, heartHeight), heartImage);
			listOfHearts.add(heart);
//			System.out.println(listOfHearts);
			gameObjects.addGameObject(heart, Layer.UI); //is this ok?


		}
//		float x = startX + 3 * (heartWidth + gap); // Assuming 3 hearts are already present
	}
	Vector2 getNewHeartSpot(int heartNum){
		float x = gap + heartNum * (heartWidth + gap);
		float y = startY;
		return new Vector2(x,y);
	}
	public Heart createFallingHeart(){
		FallingHeart fallingHeart = new FallingHeart(new Vector2(0, 0), new Vector2(heartWidth, heartHeight),
				heartImage);
		listOfFallingHearts.add(fallingHeart);
		fallingHeart.setVelocity(new Vector2(0, 100));
		return fallingHeart;
	}


	public void checkIfFallingHeartsCollided(){
		List<FallingHeart> listOfHeartsToRemove = new ArrayList<>();
//		System.out.println(listOfFallingHearts.size());
		for (FallingHeart fallingHeart : listOfFallingHearts){
			if (fallingHeart.hasCollidedWithPaddle){ //make the falling heart a lives heart
//				fallingHeartsToAdd.add(fallingHeart);
//				System.out.println(lives.value());
				if(lives.value() < 4){

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
//		return lives;
		for(FallingHeart fallingHeart: listOfHeartsToRemove){
			listOfFallingHearts.remove(fallingHeart);

		}

	}






}

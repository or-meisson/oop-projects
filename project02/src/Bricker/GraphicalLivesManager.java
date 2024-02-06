package Bricker;
//import Bricker.gameobjects.*;

import Bricker.gameobjects.Heart;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GraphicalLivesManager{
	private int lives;
	private GameObjectCollection gameObjects;
	private int heartWidth = 30;
	private int heartHeight = 30;
	private Renderable heartImage;

	private List<Heart> listOfHearts = new ArrayList<>(); //todo change to private

	public GraphicalLivesManager(int lives, GameObjectCollection gameObjects) {
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

	}

	public void initializeLivesHearts(Vector2 windowDimensions, Renderable heartImage) {
//		System.out.println(44);
		this.heartImage = heartImage;
		int gap = 10;
		int startX = gap;
		float windowHeight = windowDimensions.y();
		float startY = windowHeight - heartHeight - gap;

		for (int i = 0; i < lives; i++) {
			float x = startX + i * (heartWidth + gap);
			float y = startY;
//			System.out.println(33333);
			Heart heart = new Heart(new Vector2(x, y), new Vector2(heartWidth, heartHeight), heartImage);
			listOfHearts.add(heart);
//			System.out.println(listOfHearts);
			gameObjects.addGameObject(heart, Layer.UI); //is this ok?


		}
	}

	public Heart createFallingHeart(){
		Heart fallingHeart = new Heart(new Vector2(0, 0), new Vector2(heartWidth, heartHeight), heartImage);
		fallingHeart.setVelocity(new Vector2(0, 100));
		return fallingHeart;

	}


}

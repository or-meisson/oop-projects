package Bricker;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLivesManager {
	private int lives;
	private GameObjectCollection gameObjects;
	private TextRenderable livesTextRenderable;
	private GameObject livesText;

	public NumericLivesManager(int lives, GameObjectCollection gameObjects) {
		this.lives = lives;
		this.gameObjects = gameObjects;
		this.livesTextRenderable = new TextRenderable(Integer.toString(lives));
	}


	public void initializeLivesText(Vector2 windowDimensions){

//		TextRenderable livesTextRenderable = new TextRenderable(Integer.toString(lives));
		livesTextRenderable.setColor(Color.GREEN);
		livesText = new GameObject(new Vector2(670, 470), new Vector2(30, 30),
				livesTextRenderable);
		gameObjects.addGameObject(livesText,Layer.UI);
	}

	public void reduceLivesText(){
		lives--;
		if(lives==2){
			livesTextRenderable.setString("2");
			livesTextRenderable.setColor(Color.YELLOW);
		}
		if (lives == 1){
			livesTextRenderable.setString("1");
			livesTextRenderable.setColor(Color.RED);
		}


	}
}

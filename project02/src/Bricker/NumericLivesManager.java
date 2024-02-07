package Bricker;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLivesManager {
	Counter lives;
	private GameObjectCollection gameObjects;
	private TextRenderable livesTextRenderable;
	private GameObject livesText;

	public NumericLivesManager(Counter lives, GameObjectCollection gameObjects) {
		this.lives = lives;
		this.gameObjects = gameObjects;
		this.livesTextRenderable = new TextRenderable(Integer.toString(lives.value()));
	}


	public void initializeLivesText(Vector2 windowDimensions){

//		TextRenderable livesTextRenderable = new TextRenderable(Integer.toString(lives));
		livesTextRenderable.setColor(Color.GREEN);
		livesText = new GameObject(new Vector2(670, 470), new Vector2(30, 30),
				livesTextRenderable);
		gameObjects.addGameObject(livesText,Layer.UI);
	}



	public void reduceLivesText(){
		if(lives.value()==4){
			livesTextRenderable.setString("4");
			livesTextRenderable.setColor(Color.GREEN);
		}
//		lives.decrement();
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

//	public void updateLives() {
//		livesTextRenderable.
//
//	}
}

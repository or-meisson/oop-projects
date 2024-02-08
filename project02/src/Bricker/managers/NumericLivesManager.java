package Bricker.managers;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLivesManager {
	private static final Vector2 LIVES_TEXT_TOP_LEFT_CORNER = new Vector2(670, 470);
	private static final Vector2 LIVES_TEXT_DIMENSIONS = new Vector2(30, 30);
	private final Counter lives;
	private final GameObjectCollection gameObjects;
	private final TextRenderable livesTextRenderable;

	public NumericLivesManager(Counter lives, GameObjectCollection gameObjects) {
		this.lives = lives;
		this.gameObjects = gameObjects;
		this.livesTextRenderable = new TextRenderable(Integer.toString(lives.value()));
	}


	public void initializeLivesText(){
		livesTextRenderable.setColor(Color.GREEN);//todo constant?
		GameObject livesText = new GameObject(LIVES_TEXT_TOP_LEFT_CORNER, LIVES_TEXT_DIMENSIONS,
				livesTextRenderable);
		gameObjects.addGameObject(livesText,Layer.UI);
	}



	public void handleLivesText(){
		if(lives.value()==4){
			livesTextRenderable.setString("4");
			livesTextRenderable.setColor(Color.GREEN); //todo constant?
		}
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


}

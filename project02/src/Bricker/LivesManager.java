package Bricker;

import Bricker.gameobjects.FallingHeart;
import Bricker.gameobjects.Heart;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class LivesManager {
	Counter lives;
	GameObjectCollection gameObjects;
	GraphicalLivesManager graphicalLivesManager;
	private NumericLivesManager numericLivesManager;



	public LivesManager(Counter lives, GameObjectCollection gameObjects) {
		this.lives = lives;
		this.gameObjects = gameObjects;  //todo is this ok?
		this.graphicalLivesManager = new GraphicalLivesManager(lives, gameObjects);
		this.numericLivesManager = new NumericLivesManager(lives, gameObjects);

	}

	public void checkForGameEnd(WindowController windowController) {
		//todo where to put??  maybe in game manager
//		System.out.println("got here");
//		System.out.println(graphicalLivesManager.listOfHearts);
			String prompt = "";
			lives.decrement();
//			System.out.println(lives);
			if (lives.value() == 0){ //we lose
				prompt = "You Lose! Play again?";
				if(windowController.openYesNoDialog(prompt)){
					windowController.resetGame();
				}
				else{
					windowController.closeWindow();
				}
			}
			else {//we still have lives
				//todo remove heart
//				System.out.println("reduced life");
				graphicalLivesManager.reduceHearts();
//				System.out.println("hozez");
				numericLivesManager.reduceLivesText();

			}

		}


	public void setLives(Vector2 windowDimensions, Renderable heartImage) {
		graphicalLivesManager.initializeLivesHearts(windowDimensions, heartImage);
		numericLivesManager.initializeLivesText(windowDimensions);
	}

	public Heart createFallingHeart(){
		Heart fallingHeart = graphicalLivesManager.createFallingHeart();
		//todo somthing with the lives in numerix also add it it there ??
		return fallingHeart;
	}
	public void addLivesHeartPaddleCollision(){
		this.graphicalLivesManager.checkIfFallingHeartsCollided();
		this.numericLivesManager.reduceLivesText();

	}



}


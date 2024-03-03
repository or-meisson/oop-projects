package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.daynight.Sun;
import pepse.world.daynight.Night;
import pepse.world.Terrain;

import java.util.List;

public class PepseGameManager extends GameManager {
	private static final int CYCLE_LENGTH = 30;
	private WindowController windowController;
	private Vector2 windowDimensions;


	@Override
	public void initializeGame(
			ImageReader imageReader,
			SoundReader soundReader, UserInputListener inputListener,
			WindowController windowController) {

		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		this.windowController = windowController;
		this.windowDimensions = windowController.getWindowDimensions();

		//create sky
		GameObject sky = Sky.create(this.windowDimensions);
		gameObjects().addGameObject(sky, Layer.BACKGROUND); //todo change to sky layer


		//create ground
		Terrain terrain = new Terrain(windowDimensions, 0);
		List<Block> allBlocks = terrain.createInRange(0, (int) windowDimensions.x());
		for (Block block : allBlocks) {
			gameObjects().addGameObject(block, Layer.STATIC_OBJECTS); //todo change layer
		}

		//create night
		GameObject night = Night.create(windowDimensions, CYCLE_LENGTH);
		gameObjects().addGameObject(night, Layer.UI); //todo change layer?

		//create sun
		GameObject sun = Sun.create(windowDimensions, CYCLE_LENGTH);
		gameObjects().addGameObject(sun,Layer.BACKGROUND); //todo change layer?
	}


	public static void main(String[] args) {

		new PepseGameManager().run();
	}

}

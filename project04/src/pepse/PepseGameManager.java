package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.ScheduledTask;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Sun;
import pepse.world.daynight.Night;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Tree;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PepseGameManager extends GameManager {
	private static final int CYCLE_LENGTH = 30;
	private WindowController windowController;
	private Vector2 windowDimensions;

	private TextRenderable energyTextRenderable; //make class





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
		gameObjects().addGameObject(sky, Layer.BACKGROUND);


		//create ground
		Terrain terrain = new Terrain(windowDimensions, 0);
		List<Block> allBlocks = terrain.createInRange(0, (int) windowDimensions.x());
		for (Block block : allBlocks) {
			gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
		}

		//create night
		GameObject night = Night.create(windowDimensions, CYCLE_LENGTH);
		gameObjects().addGameObject(night, Layer.FOREGROUND);

		//create sun
		GameObject sun = Sun.create(windowDimensions, CYCLE_LENGTH);
		gameObjects().addGameObject(sun,Layer.BACKGROUND);

		//create sun halo
		GameObject sunHalo = SunHalo.create(sun);
		gameObjects().addGameObject(sunHalo, Layer.BACKGROUND); //todo change layer?

		//create avatar
		Avatar avatar = new Avatar(new Vector2(windowDimensions.x()/2,
				terrain.groundHeightAt(windowDimensions.x()/2)),
				inputListener,
				imageReader);
		gameObjects().addGameObject(avatar); //todo change layer?

		//create energy text
		this.energyTextRenderable = new TextRenderable("Energy: 100");
		avatar.addEnergyListener(this::updateEnergyConsumer);
		gameObjects().addGameObject(new GameObject(new Vector2(10, 10), new Vector2(100, 100),
				this.energyTextRenderable), Layer.FOREGROUND);

		//create flora
		Flora flora = new Flora(terrain::groundHeightAt, terrain.groundHeightAt(0),
				(GameObject fruit,GameObject other)->{
					if (other.getTag().equals( "avatar")){
						gameObjects().removeGameObject(fruit);
						avatar.addEnergy(10);

						ScheduledTask scheduledTask = new ScheduledTask(
								avatar,
								CYCLE_LENGTH,
								false,
								() -> {
									this.gameObjects().addGameObject(fruit);
								}
						);




					}
				}
				);
		List<Tree> trees = flora.createInRange(0, (int) windowDimensions.x());
		for (Tree tree : trees) {
			gameObjects().addGameObject(tree.getTrunk(), Layer.STATIC_OBJECTS);
			gameObjects().addGameObject(tree.getLeaves(), Layer.BACKGROUND);
			gameObjects().addGameObject(tree.getFruit());
		}




	}


//	@Override
//	public void update(float deltaTime) {
//		super.update(deltaTime);
//
//
//
//	}



	private void updateEnergyConsumer(int newEnergyLevel) {
		this.energyTextRenderable.setString("Energy: " + newEnergyLevel);

	}

	public static void main(String[] args) {

		new PepseGameManager().run();
	}

}

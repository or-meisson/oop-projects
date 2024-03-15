package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.ScheduledTask;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Sun;
import pepse.world.daynight.Night;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Tree;

import java.util.List;
import java.util.Random;

/**
 * The game manager for the Pepse game.
 */
public class PepseGameManager extends GameManager {
	private static final int CYCLE_LENGTH = 30;
	private static final String START_ENERGY_TEXT = "Energy: 100";
	private static final int ENERGY_TO_ADD_WHEN_EATING_FRUIT = 10;
	private static final Vector2 TOP_LEFT_CORNER_OF_ENERGY_TEXT =
			new Vector2(ENERGY_TO_ADD_WHEN_EATING_FRUIT, ENERGY_TO_ADD_WHEN_EATING_FRUIT);
	private static final int MAX_AVATAR_ENERGY = 100;
	private static final Vector2 DIMENSIONS_OF_ENERGY_TEXT =
			new Vector2(MAX_AVATAR_ENERGY, MAX_AVATAR_ENERGY);
	private static final float MID_SCREEN_MULT_FACTOR = 0.5F;
	private static final String ENERGY_TEXT = "Energy: ";
	private static final int SEED_FOR_TERRAIN = 0;
	private Vector2 windowDimensions;
	private TextRenderable energyTextRenderable;


	/**
	 * Initializes the game by creating the game objects and adding them to the game manager.
	 * @param imageReader the image reader
	 * @param soundReader the sound reader
	 * @param inputListener the input listener
	 * @param windowController the window controller
	 */
	@Override
	public void initializeGame(
			ImageReader imageReader,
			SoundReader soundReader, UserInputListener inputListener,
			WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		this.windowDimensions = windowController.getWindowDimensions();

		//create sky
		GameObject sky = Sky.create(this.windowDimensions);
		gameObjects().addGameObject(sky, Layer.BACKGROUND);

		//create ground
		Terrain terrain = createTerrain();

		//create night
		GameObject night = Night.create(windowDimensions, CYCLE_LENGTH);
		gameObjects().addGameObject(night, Layer.FOREGROUND);

		//create sun
		GameObject sun = Sun.create(windowDimensions, CYCLE_LENGTH);
		gameObjects().addGameObject(sun,Layer.BACKGROUND);

		//create sun halo
		GameObject sunHalo = SunHalo.create(sun);
		gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);

		//create avatar
		Avatar avatar = createAvatar(imageReader, inputListener, terrain);

		//create energy text
		createEnergyText(avatar);

		//create flora
		crateFlora(terrain, avatar);
	}

	/**
	 * Creates the terrain and adds the blocks to the game.
	 * @return the terrain
	 */
	private Terrain createTerrain() {
		Terrain terrain = new Terrain(windowDimensions, new Random().nextInt());
		List<Block> allBlocks = terrain.createInRange(0, (int) windowDimensions.x());
		for (Block block : allBlocks) {
			gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
		}
		return terrain;
	}

	/**
	 * Creates the avatar and adds it to the game.
	 * @param imageReader the image reader
	 * @param inputListener the input listener
	 * @param terrain the terrain
	 * @return the avatar
	 */
	private Avatar createAvatar(ImageReader imageReader, UserInputListener inputListener,
								Terrain terrain) {
		Avatar avatar = new Avatar(new Vector2(windowDimensions.x()* MID_SCREEN_MULT_FACTOR,
				terrain.groundHeightAt(windowDimensions.x()* MID_SCREEN_MULT_FACTOR)),
				inputListener,
				imageReader, MAX_AVATAR_ENERGY);
		gameObjects().addGameObject(avatar);
		return avatar;
	}

	/**
	 * Creates the energy text and adds it to the game.
	 * @param avatar the avatar
	 */
	private void createEnergyText(Avatar avatar) {
		this.energyTextRenderable = new TextRenderable(START_ENERGY_TEXT);
		avatar.addEnergyListener(this::updateEnergyConsumer);
		gameObjects().addGameObject(new GameObject(TOP_LEFT_CORNER_OF_ENERGY_TEXT,
				DIMENSIONS_OF_ENERGY_TEXT,
				this.energyTextRenderable), Layer.FOREGROUND);
	}

	/**
	 * Creates the flora and adds the trees to the game manager.
	 * @param terrain the terrain
	 * @param avatar the avatar
	 */
	private void crateFlora(Terrain terrain, Avatar avatar) {
		Flora flora = new Flora(terrain::groundHeightAt,
				(GameObject fruit,GameObject other)->{
					if (other.getTag().equals("avatar")){
						gameObjects().removeGameObject(fruit);
						if (avatar.getEnergy() < MAX_AVATAR_ENERGY){
						avatar.addEnergy(ENERGY_TO_ADD_WHEN_EATING_FRUIT);
						}
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
		List<Tree> trees = flora.createInRange(SEED_FOR_TERRAIN, (int) windowDimensions.x());
		for (Tree tree : trees) {
			avatar.addJumpListener(tree::onJump);
			gameObjects().addGameObject(tree.getTrunk(), Layer.STATIC_OBJECTS);
			gameObjects().addGameObject(tree.getLeaves(), Layer.BACKGROUND);
			gameObjects().addGameObject(tree.getFruit());
		}
	}


	/**
	 * Updates the energy text renderable with the new energy level.
	 * @param newEnergyLevel the new energy level
	 */

	private void updateEnergyConsumer(int newEnergyLevel) {
		this.energyTextRenderable.setString(ENERGY_TEXT + newEnergyLevel);

	}


	/**
	 * the main program that runs the game
	 * @param args possible args
	 */
	public static void main(String[] args) {
		new PepseGameManager().run();
	}

}

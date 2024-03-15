package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The avatar is a game object that is centered at the bottom of the window
 * and has a width of 35 and a height of 50. The avatar can move left, right,
 * and jump. The avatar has energy that is used when it moves and jumps.
 */
public class Avatar extends GameObject {
	private static final float VELOCITY_X = 150;
	private static final float VELOCITY_Y = -220;
	private static final float GRAVITY = 240;
	private static final int AVATAR_HEIGHT = 50;
	private static final float AVATAR_WIDTH = 35;
	private static final String PATH_TO_IDLE_IMAGE_1 = "assets/idle_0.png";
	private static final String PATH_TO_IDLE_IMAGE_2 = "assets/idle_1.png";
	private static final String PATH_TO_IDLE_IMAGE_3 = "assets/idle_2.png";
	private static final String PATH_TO_IDLE_IMAGE_4 = "assets/idle_3.png";
	private static final String PATH_TO_JUMP_IMAGE_1 = "assets/jump_0.png";
	private static final String PATH_TO_JUMP_IMAGE_2 = "assets/jump_1.png";
	private static final String PATH_TO_JUMP_IMAGE_3 = "assets/jump_2.png";
	private static final String PATH_TO_JUMP_IMAGE_4 = "assets/jump_3.png";
	private static final String PATH_TO_RUN_IMAGE_1 = "assets/run_0.png";
	private static final String PATH_TO_RUN_IMAGE_2 = "assets/run_1.png";
	private static final String PATH_TO_RUN_IMAGE_3 = "assets/run_2.png";
	private static final String PATH_TO_RUN_IMAGE_4 = "assets/run_3.png";
	private static final String PATH_TO_RUN_IMAGE_5 = "assets/run_4.png";
	private static final String PATH_TO_RUN_IMAGE_6 = "assets/run_5.png";
	private static final float TIME_BETWEEN_CLIPS_IDLE = 0.2f;
	private static final float TIME_BETWEEN_CLIPS_JUMP = 0.2f;
	private static final float TIME_BETWEEN_CLIPS_RUN = 0.1f;
	private static final float RUN_ENERGY = 0.5F;
	private static final int ENERGY_TO_REMOVE_FROM_JUMP = 10;
	private static final String AVATAR_TAG = "avatar";
	private final float maxEnergy;
	private float energy;
	private final UserInputListener inputListener;
	private final List<Consumer<Integer>> energyListeners = new ArrayList<>();
	private final List<Runnable> jumpListeners = new ArrayList<>();
	private final AnimationRenderable idleAnimation;
	private final AnimationRenderable jumpAnimation;
	private final AnimationRenderable runAnimation;

	private Renderable[] idleRenderables;
	private Renderable[] jumpRenderables;
	private Renderable[] runRenderables;



	/**
	 * Creates an avatar game object that is centered at the bottom of
	 * the window and has a width of 35 and a height of 50.
	 * @param pos the position of the avatar
	 * @param inputListener the input listener of the avatar
	 * @param imageReader the image reader of the avatar
	 */
	public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader,
				  float maxEnergy) {

		super(Vector2.of(pos.x(), pos.y()-Avatar.AVATAR_HEIGHT), Vector2.of(Avatar.AVATAR_WIDTH,
				Avatar.AVATAR_HEIGHT), imageReader.readImage
				(PATH_TO_IDLE_IMAGE_1, false));
		this.maxEnergy = maxEnergy;
		this.energy = this.maxEnergy;


		physics().preventIntersectionsFromDirection(Vector2.ZERO);
		transform().setAccelerationY(GRAVITY);
		this.inputListener = inputListener;

		this.idleRenderables = new Renderable[]{
				imageReader.readImage(PATH_TO_IDLE_IMAGE_1, false),
				imageReader.readImage(PATH_TO_IDLE_IMAGE_2, false),
				imageReader.readImage(PATH_TO_IDLE_IMAGE_3, false),
				imageReader.readImage(PATH_TO_IDLE_IMAGE_4, false)};

		this.jumpRenderables = new Renderable[]{
				imageReader.readImage(PATH_TO_JUMP_IMAGE_1, false),
				imageReader.readImage(PATH_TO_JUMP_IMAGE_2, false),
				imageReader.readImage(PATH_TO_JUMP_IMAGE_3, false),
				imageReader.readImage(PATH_TO_JUMP_IMAGE_4, false)};

		this.runRenderables = new Renderable[]{
				imageReader.readImage(PATH_TO_RUN_IMAGE_1, false),
				imageReader.readImage(PATH_TO_RUN_IMAGE_2, false),
				imageReader.readImage(PATH_TO_RUN_IMAGE_3, false),
				imageReader.readImage(PATH_TO_RUN_IMAGE_4, false),
				imageReader.readImage(PATH_TO_RUN_IMAGE_5, false),
				imageReader.readImage(PATH_TO_RUN_IMAGE_6, false)};

		this.idleAnimation = new AnimationRenderable(this.idleRenderables, TIME_BETWEEN_CLIPS_IDLE);
		this.jumpAnimation = new AnimationRenderable(this.jumpRenderables, TIME_BETWEEN_CLIPS_JUMP);
		this.runAnimation = new AnimationRenderable(this.runRenderables, TIME_BETWEEN_CLIPS_RUN);

		this.setTag(AVATAR_TAG);
		}


	/**
	 * Adds energy to the avatar
	 * @param energy the amount of energy to add
	 */
	public void addEnergy(float energy){
		this.energy += energy;
	}

	/**
	 * Adds a listener that listens to the energy of the avatar
	 * @param listener the listener to add
	 */
	public void addEnergyListener(Consumer<Integer> listener){
		energyListeners.add(listener);
	}

	/**
	 * Adds a listener that listens to the jump of the avatar
	 * @param listener the listener to add
	 */
	public void addJumpListener(Runnable listener){
		jumpListeners.add(listener);
	}




	/**
	 * Updates the avatar's position and energy
	 * @param deltaTime the time that has passed since the last update
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		boolean isJumping = getVelocity().y() != 0; // Check if the Avatar is jumping

		float xVel = 0;
		if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && this.energy >= RUN_ENERGY) {
				xVel -= VELOCITY_X;
				this.energy -= RUN_ENERGY;
				renderer().setIsFlippedHorizontally(true);
				renderer().setRenderable(runAnimation);
		}
		else if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && this.energy >= RUN_ENERGY) {
				xVel += VELOCITY_X;
				this.energy -= RUN_ENERGY;
				renderer().setIsFlippedHorizontally(false);
				renderer().setRenderable(runAnimation);
		}
		else if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && !isJumping) {
			if (this.energy >= ENERGY_TO_REMOVE_FROM_JUMP){
				transform().setVelocityY(VELOCITY_Y);
				this.energy -= ENERGY_TO_REMOVE_FROM_JUMP;
				jumpListeners.forEach(Runnable::run);
				renderer().setRenderable(jumpAnimation);
			}
		}
		else{
			renderer().setRenderable(idleAnimation);
			if(!isJumping && this.energy < this.maxEnergy) {
				this.energy++;
			}
		}
		transform().setVelocityX(xVel);

		energyListeners.forEach(listener -> listener.accept((int) this.energy));
	}

	/**
	 * Returns the energy of the avatar
	 * @return the energy of the avatar
	 */
	public float getEnergy() {
		return this.energy;
	}
}

package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Avatar extends GameObject {
	private static final float VELOCITY_X = 150;
	private static final float VELOCITY_Y = -220;
	private static final float GRAVITY = 240;
	private static final Color AVATAR_COLOR = Color.DARK_GRAY;
	private static final int AVATAR_HEIGHT = 50;
	private static final float AVATAR_WIDTH = 35;

	private float energy = 100;


	private UserInputListener inputListener;


	private List<Consumer<Integer>> energyListeners = new ArrayList<>();

	private List<Runnable> jumpListeners = new ArrayList<>();
	private AnimationRenderable idleAnimation;
	private AnimationRenderable jumpAnimation;
	private AnimationRenderable runAnimation;

	private Renderable[] idleRenderables;
	private Renderable[] jumpRenderables;
	private Renderable[] runRenderables;




	public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {

		super(Vector2.of(pos.x(), pos.y()-Avatar.AVATAR_HEIGHT), Vector2.of(Avatar.AVATAR_WIDTH,
				Avatar.AVATAR_HEIGHT), imageReader.readImage
				("assets/idle_0.png", false));

		physics().preventIntersectionsFromDirection(Vector2.ZERO);
		transform().setAccelerationY(GRAVITY);
		this.inputListener = inputListener;

		this.idleRenderables = new Renderable[]{imageReader.readImage
				("assets/idle_0.png", false),
				imageReader.readImage("assets/idle_1.png", false),
				imageReader.readImage("assets/idle_2.png", false),
				imageReader.readImage("assets/idle_3.png", false)};

		this.jumpRenderables = new Renderable[]{imageReader.readImage
				("assets/jump_0.png", false),
				imageReader.readImage("assets/jump_1.png", false),
				imageReader.readImage("assets/jump_2.png", false),
				imageReader.readImage("assets/jump_3.png", false)};

		this.runRenderables = new Renderable[]{imageReader.readImage
				("assets/run_0.png", false),
				imageReader.readImage("assets/run_1.png", false),
				imageReader.readImage("assets/run_2.png", false),
				imageReader.readImage("assets/run_3.png", false),
				imageReader.readImage("assets/run_4.png", false),
				imageReader.readImage("assets/run_5.png", false)};

		this.idleAnimation = new AnimationRenderable(this.idleRenderables, 0.2f);
		this.jumpAnimation = new AnimationRenderable(this.jumpRenderables, 0.2f);
		this.runAnimation = new AnimationRenderable(this.runRenderables, 0.1f);

		this.setTag("avatar");
		}

	public void addEnergy(float energy){
		this.energy += energy;
	}

	public void addEnergyListener(Consumer<Integer> listener){
		energyListeners.add(listener);
	}

	public void addJumpListener(Runnable listener){
		jumpListeners.add(listener);
	}





	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float xVel = 0;
		if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && this.energy >= 0.5F) {
				xVel -= VELOCITY_X;
				this.energy -= 0.5F;
				renderer().setIsFlippedHorizontally(true);
				renderer().setRenderable(runAnimation);
		}
		else if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && this.energy >= 0.5F) {
				xVel += VELOCITY_X;
				this.energy -= 0.5F;
				renderer().setIsFlippedHorizontally(false);
				renderer().setRenderable(runAnimation);
		}
		else if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0) {
			if (this.energy >= 10){
				transform().setVelocityY(VELOCITY_Y);
				this.energy -= 10;
				jumpListeners.forEach(Runnable::run);
				renderer().setRenderable(jumpAnimation);
			}
		}
		else{
			renderer().setRenderable(idleAnimation); //todo is it ok ? does the energy go up
			// during jump

			this.energy++;
		}
		transform().setVelocityX(xVel);

		energyListeners.forEach(listener -> listener.accept((int) this.energy));
	}
}

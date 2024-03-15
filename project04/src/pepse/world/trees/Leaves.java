	package pepse.world.trees;

	import danogl.GameObject;
	import danogl.components.ScheduledTask;
	import danogl.components.Transition;
	import danogl.gui.rendering.Renderable;
	import danogl.util.Vector2;

	import java.util.Random;

	/**
	 * The leaves are a green rectangle that is centered on the trunk and has a width and height of 100.
	 */
	public class Leaves extends GameObject {

		private static final int TRANSITION_TIME_FOR_MOVING_LEAVES = 5;
		private static final Vector2 LEAVES_SIZE_AFTER_ROTATION = new Vector2(110, 110);
		private static final float DEGREE_OF_LEAVES_AFTER_JUMP = 90f;
		private static final int TRANSITION_TIME_FOR_MOVING_LEAVES_90_DEGREES = 1;
		private  final float LEAVES_INITIAL_DEGREE = 0f;
		private  final float LEAVES_FINAL_DEGREE = 20f;
		private  final Vector2 LEAVES_SIZE = new Vector2(100, 100);

		private boolean isRotating90Degrees = false;

		/**
		 * Creates a leaves game object that is centered on the trunk and has a width and
		 * height of 100.
		 * @param topLeftCorner the top left corner of the leaves
		 * @param renderable the renderable of the leaves
		 */
		public Leaves(Vector2 topLeftCorner, Renderable renderable) {
			super(topLeftCorner, Vector2.ONES, renderable);
			this.setDimensions(LEAVES_SIZE);

			Random random = new Random();
			float randomNumber = random.nextFloat();
			ScheduledTask scheduledTask = new ScheduledTask(
					this,
					randomNumber,
					true,
					() -> {if(!isRotating90Degrees){
						new Transition<Float>(this,
								this.renderer()::setRenderableAngle,
								LEAVES_INITIAL_DEGREE,
								LEAVES_FINAL_DEGREE,
								Transition.LINEAR_INTERPOLATOR_FLOAT,
								TRANSITION_TIME_FOR_MOVING_LEAVES,
								Transition.TransitionType.TRANSITION_ONCE,
								null);


						new Transition<Vector2>(this,
								this::setDimensions,
								LEAVES_SIZE,
								LEAVES_SIZE_AFTER_ROTATION,
								Transition.LINEAR_INTERPOLATOR_VECTOR,
								TRANSITION_TIME_FOR_MOVING_LEAVES,
								Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
								null);}
					}	);

		}

		/**
		 * Rotates the leaves when the player jumps.
		 */
		public void onJump() {
			isRotating90Degrees = true;
			new Transition<Float>(this,
					this.renderer()::setRenderableAngle,
					this.renderer().getRenderableAngle(),
					this.renderer().getRenderableAngle() + DEGREE_OF_LEAVES_AFTER_JUMP,
					Transition.LINEAR_INTERPOLATOR_FLOAT,
					TRANSITION_TIME_FOR_MOVING_LEAVES_90_DEGREES,
					Transition.TransitionType.TRANSITION_ONCE,
					null);

		}







	}

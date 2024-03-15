package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The night object is a black rectangle that covers the entire screen and
 * changes its opacity from 0 to 0.5 and back to 0
 * every cycleLength * 0.5 seconds.
 */
public class Night {


	private static final Float MIDNIGHT_OPACITY = 0.5F;
	private static final Float CYCLE_LENGTH_MULT_FACTOR = 0.5f;
	private static final float MID_DAY_OPACITY = 0f;
	private static final String NIGHT_TAG = "night";

	/**
	 * Creates a night game object that changes its opacity from 0 to 0.5 and back to 0
	 * every cycleLength * 0.5 seconds.
	 * @param windowDimensions the dimensions of the window
	 * @param cycleLength the time it takes for the night to complete a cycle
	 * @return the night game object
	 */
	public static GameObject create(Vector2 windowDimensions, float cycleLength) {

		Renderable nightRenderable = new RectangleRenderable(Color.BLACK);
		GameObject night = new GameObject(Vector2.ZERO, windowDimensions, nightRenderable);
		night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		night.setTag(NIGHT_TAG);


		new Transition<Float>(night,
				night.renderer()::setOpaqueness,
				MID_DAY_OPACITY,
				MIDNIGHT_OPACITY,
				Transition.CUBIC_INTERPOLATOR_FLOAT,
				cycleLength * CYCLE_LENGTH_MULT_FACTOR,
		Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
		null);


		return night;
	}
}

package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.awt.geom.Area;


/**
 * A class that creates a sun game object that moves in a circular path
 */
public class Sun {

	private static final float MID_SCREEN_MULT_FACTOR = 0.5F;
	private static final Vector2 SUN_SIZE = new Vector2(50, 50);
	private static final Color SUN_COLOR = Color.YELLOW;
	private static final float INITIAL_SUN_DEGREE = 0f;
	private static final float FINAL_SUN_DEGREE = 360f;
	private static final String SUN_TAG = "sun";
	private static float SUN_CYCLE_CENTER_Y_MULT_FACTOR = (float) 2 / 3;
	private static float SUN_CENTER_Y_MULT_FACTOR = (float) 1 / 3;


	/**
	 * Creates a sun game object that moves in a circular path
	 * @param windowDimensions the dimensions of the window
	 * @param cycleLength the time it takes for the sun to complete a cycle
	 * @return the sun game object
	 */
	public static GameObject create(Vector2 windowDimensions, float cycleLength) {
		Vector2 initialSunCenter = new Vector2(windowDimensions.x() * MID_SCREEN_MULT_FACTOR,
				windowDimensions.y() * SUN_CENTER_Y_MULT_FACTOR);
		GameObject sun = new GameObject(initialSunCenter, SUN_SIZE, new OvalRenderable(SUN_COLOR));
		sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		sun.setCenter(initialSunCenter);
		Vector2 cycleCenter = new Vector2(windowDimensions.x() * MID_SCREEN_MULT_FACTOR,
				windowDimensions.y() * (SUN_CYCLE_CENTER_Y_MULT_FACTOR));
		sun.setTag(SUN_TAG);

		new Transition<Float>(sun,
				(Float angle) -> sun.setCenter
						(initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
				INITIAL_SUN_DEGREE,
				FINAL_SUN_DEGREE,
				Transition.LINEAR_INTERPOLATOR_FLOAT,
				cycleLength,
				Transition.TransitionType.TRANSITION_LOOP,
				null);



		return sun;



	}
}

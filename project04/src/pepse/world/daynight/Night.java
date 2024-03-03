package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {


	private static final Float MIDNIGHT_OPACITY = 0.5F;
	public static final float MID_DAY_OPACITY = 0f;

	public static GameObject create(Vector2 windowDimensions, float cycleLength) {

		Renderable nightRenderable = new RectangleRenderable(Color.BLACK);
		GameObject night = new GameObject(Vector2.ZERO, windowDimensions, nightRenderable);
		night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		night.setTag("night");


		new Transition<Float>(night,
				night.renderer()::setOpaqueness,
				MID_DAY_OPACITY,
				MIDNIGHT_OPACITY,
				Transition.CUBIC_INTERPOLATOR_FLOAT,
				cycleLength/2,
		Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
		null);


		return night;
	}
}

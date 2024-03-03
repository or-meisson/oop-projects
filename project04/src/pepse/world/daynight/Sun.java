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

public class Sun {

	public static GameObject create(Vector2 windowDimensions, float cycleLength) {
		Vector2 initialSunCenter = new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 3);
		GameObject sun = new GameObject(initialSunCenter, new Vector2(50, 50), new OvalRenderable(Color.YELLOW));
		sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		sun.setCenter(initialSunCenter);
		Vector2 cycleCenter = new Vector2(windowDimensions.x() / 2,
				windowDimensions.y() * ((float) 2 / 3));
		sun.setTag("sun");

//		Area initialSunCenter;
		new Transition<Float>(sun,
				(Float angle) -> sun.setCenter
						(initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
				0f,
				360f,
				Transition.LINEAR_INTERPOLATOR_FLOAT,
				cycleLength,
				Transition.TransitionType.TRANSITION_LOOP,
				null);



		return sun;



	}
}

package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The sun halo is a yellow oval that is centered on the sun and has a radius of 100.
 */
public class SunHalo {
	private static final Vector2 SUN_DIMENSIONS = new Vector2(100, 100);
	private static final String SUN_HALO_TAG = "sunHalo";
	private static final Color HALO_COLOR = new Color(255, 255, 0, 20);

	/**
	 * Creates a sun halo game object that is centered on the sun and has a radius of 100.
	 * @param sun the sun game object
	 * @return the sun halo game object
	 */
	public static GameObject create(GameObject sun) {
		GameObject sunHalo = new GameObject( sun.getCenter(), SUN_DIMENSIONS,
				new OvalRenderable(HALO_COLOR));
		sunHalo.setCenter( sun.getCenter());
		sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		sunHalo.setTag(SUN_HALO_TAG);
		sunHalo.addComponent((float deltaTime)->
				sunHalo.setCenter(sun.getCenter()));
		return sunHalo;
	}

}

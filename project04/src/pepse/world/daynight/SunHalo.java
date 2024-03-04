package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {
	private static Color HALO_COLOR = new Color(255, 255, 0, 20);
	public static GameObject create(GameObject sun) {
//		Vector2 sunHaloCenter = sun.getCenter();
		GameObject sunHalo = new GameObject( sun.getCenter(), new Vector2(100, 100),
				new OvalRenderable(HALO_COLOR));
		sunHalo.setCenter( sun.getCenter());
		sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		sunHalo.setTag("sunHalo");
		sunHalo.addComponent((float deltaTime)->
				sunHalo.setCenter(sun.getCenter()));
		return sunHalo;
	}

}

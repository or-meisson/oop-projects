package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Leaves extends GameObject {

	public  final Vector2 LEAVES_SIZE = new Vector2(100, 100);

	public Leaves(Vector2 topLeftCorner, Renderable renderable) {
		super(topLeftCorner, new Vector2(100, 100), renderable);
		this.setDimensions(LEAVES_SIZE);

		new Transition<Float>(this,
				this.renderer()::setRenderableAngle,
				0f,
				360f,
				Transition.CUBIC_INTERPOLATOR_FLOAT, //todo change somthing here?
				5,
				Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
				null);


		new Transition<Vector2>(this,
				this::setDimensions,
				LEAVES_SIZE,
				new Vector2(110, 110),
				Transition.CUBIC_INTERPOLATOR_VECTOR, //todo change somthing here?
				5,
				Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
				null);

	}

//	ScheduledTask scheduledTask = new ScheduledTask( //todo scehduled task
//			this,
//			2.0f,
//			 true,
//			??;





}

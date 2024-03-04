package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.Component;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.function.BiConsumer;

public class Fruit extends GameObject {

	public  final Vector2 FRUIT_SIZE = new Vector2(30, 30);
	private final BiConsumer<GameObject, GameObject> onCollisionOfFruit;


	public Fruit(Vector2 topLeftCorner, Renderable renderable, BiConsumer<GameObject, GameObject> onCollisionOfFruit) {
		super(topLeftCorner, new Vector2(30, 30), renderable);
		this.onCollisionOfFruit = onCollisionOfFruit;
		this.setDimensions(FRUIT_SIZE);





	}

	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
//		TODO remove the fruit from the tree and add energy to the avatar
		this.onCollisionOfFruit.accept(this, other);





//		new Transition<Component>(this,
//				this::removeComponent,
//				this,
//				this,
//				Transition.CUBIC_INTERPOLATOR_COMPONENT,
//				30,
//				Transition.TransitionType.TRANSITION_ONCE,
//				null);


	}

}

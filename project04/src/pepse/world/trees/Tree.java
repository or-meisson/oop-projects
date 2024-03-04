package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.BiConsumer;

public class Tree extends GameObject {

	public  final Color LEAVES_COLOR = new Color(50, 200, 30);

	public  final Color TRUNK_COLOR = new Color(100, 50, 20);

	private Trunk trunk;
	private Leaves leaves;
	private Fruit fruit;
	private final BiConsumer<GameObject, GameObject> onCollisionOfFruit;


	public Tree(Vector2 topLeftCorner, Renderable renderable, BiConsumer<GameObject, GameObject> onCollisionOfFruit) {
		super(topLeftCorner, new Vector2(100, 160), renderable);
		this.trunk = new Trunk(topLeftCorner, new RectangleRenderable(TRUNK_COLOR));

		this.leaves = new Leaves(topLeftCorner.add(new Vector2(0, 0)),
				new RectangleRenderable(LEAVES_COLOR));
		this.fruit = new Fruit(topLeftCorner.add(new Vector2(0, 0)),
				new OvalRenderable(Color.RED),onCollisionOfFruit);
		this.onCollisionOfFruit = onCollisionOfFruit;
		this.fruit.setCenter(this.leaves.getCenter());
	}

	public Trunk getTrunk() {
		return this.trunk;
	}
	public void setTrunkTopLeftCorner(Vector2 topLeftCorner) {
		this.trunk.setTopLeftCorner(topLeftCorner);
	}

	public void setLeavesCenter() {
		this.leaves.setCenter(new Vector2(getTrunk().getTopLeftCorner().add(getTrunk().getDimensions()
				.mult(0.5F)).x(), getTrunk().getTopLeftCorner().y() - 50));
	}

	public Leaves getLeaves() {
		return this.leaves;
	}


	public GameObject getFruit() {
		return this.fruit;
	}

	public void setFruitCenter() {
		this.fruit.setCenter(this.leaves.getCenter());
	}
}

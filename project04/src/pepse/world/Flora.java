package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.trees.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.Random;



public class Flora {


	private final UnaryOperator<Float> groundHeightAt;
	private final float groundHeightAtX0;
	private final BiConsumer<GameObject, GameObject> onCollisionOfFruit;
	public static final double TREE_PLANT_PROBABILITY = 0.2;


	public Flora(UnaryOperator<Float> groundHeightAt, float groundHeightAtX0,
				 BiConsumer<GameObject,GameObject> onCollisionOfFruit) {


		this.groundHeightAt = groundHeightAt;
		this.groundHeightAtX0 = groundHeightAtX0;

		this.onCollisionOfFruit = onCollisionOfFruit;
	}


	public List<Tree> createInRange(int minX, int maxX) {
		List<Tree> trees = new ArrayList<>();
		int blockSize = Block.SIZE;
		int startBlockX = (int) Math.ceil((double) minX / blockSize) * blockSize;
		int endBlockX = (int) Math.floor((double) maxX / blockSize) * blockSize;

		for (int x = startBlockX; x <= endBlockX; x += blockSize) {
			float groundHeight = groundHeightAt.apply((float) x);

			Random random = new Random();

			double probability = random.nextDouble();
			if (probability < TREE_PLANT_PROBABILITY) {

				Tree tree = new Tree(Vector2.of(x, 0), null,onCollisionOfFruit);
				float trunkHeight = tree.getTrunk().getTrunkSize().y();
				tree.setTrunkTopLeftCorner(Vector2.of(x,groundHeight - trunkHeight));
				tree.setLeavesCenter();
				tree.setFruitCenter();
				trees.add(tree);
			}


		}
		return trees;

	}

//	public void checkIfAvatarAteFruit()
}

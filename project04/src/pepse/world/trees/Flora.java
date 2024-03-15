package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import pepse.world.trees.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;
import java.util.Random;


/**
 * The flora is a collection of trees.
 */
public class Flora {


	private final UnaryOperator<Float> groundHeightAt;
	private final BiConsumer<GameObject, GameObject> onCollisionOfFruit;
	private static final double TREE_PLANT_PROBABILITY = 0.2;

	/**
	 * Creates a flora with the given ground height function and the given action
	 * to perform when the fruit collides with another game object.
	 * @param groundHeightAt the ground height function
	 * @param onCollisionOfFruit the action to perform when the fruit collides
	 *                             with another game object
	 */
	public Flora(UnaryOperator<Float> groundHeightAt,
				 BiConsumer<GameObject,GameObject> onCollisionOfFruit) {

		this.groundHeightAt = groundHeightAt;
		this.onCollisionOfFruit = onCollisionOfFruit;
	}

	/**
	 * Creates a list of trees that are in the range of the given x coordinates.
	 * @param minX the minimum x coordinate
	 * @param maxX the maximum x coordinate
	 * @return a list of trees that are in the range of the given x coordinates
	 */
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

}

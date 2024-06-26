package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The terrain is a game object that is the ground of the game.
 */
public class Terrain {
	private static final int NOISE_MULT_FACTOR = 7;
	private static final String GROUND_TAG = "ground";
	private final float GROUND_HEIGHT_MULT_FACTOR = (float) 2 / 3;
	private final float groundHeightAtX0;
	private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
	private static final int TERRAIN_DEPTH = 20;
	private final NoiseGenerator noiseGenerator;


	/**
	 * Creates a terrain game object that is the ground of the game.
	 * @param windowDimensions the dimensions of the window
	 * @param seed the seed of the noise generator
	 */
	public Terrain(Vector2 windowDimensions, int seed){
		groundHeightAtX0 = windowDimensions.y() * (GROUND_HEIGHT_MULT_FACTOR);
		this.noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);

	}

	/**
	 * Returns the ground height at the given x coordinate.
	 * @param x the x coordinate
	 * @return the ground height at the given x coordinate
	 */
	public float groundHeightAt(float x){
		float noise = (float) this.noiseGenerator.noise(x, Block.SIZE *
				Terrain.NOISE_MULT_FACTOR);
		return groundHeightAtX0 + noise;
	}


	/**
	 * Creates a list of blocks that are in the range of the given x coordinates.
	 * @param minX the minimum x coordinate
	 * @param maxX the maximum x coordinate
	 * @return a list of blocks that are in the range of the given x coordinates
	 */
	public List<Block> createInRange(int minX, int maxX) {
		List<Block> blocks = new ArrayList<>();
		int blockSize = Block.SIZE;

		int startBlockX = (int) Math.ceil((double) minX / blockSize) * blockSize;
		int endBlockX = (int) Math.floor((double) maxX / blockSize) * blockSize;

		for (int x = startBlockX; x <= endBlockX; x += blockSize) {
			int topY = (int) (Math.floor(groundHeightAt(x) / blockSize) * blockSize);

			for (int i = 0; i < TERRAIN_DEPTH; i++) {

				int y = topY + i * blockSize;
				Block block = new Block(Vector2.of(x, y),
						new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
				block.setTag(GROUND_TAG);
				blocks.add(block);
			}
		}

		return blocks;

	}



}

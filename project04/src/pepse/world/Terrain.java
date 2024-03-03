package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Terrain {
	private static final int NOISE_MULT_FACTOR = 7;
	private float groundHeightAtX0;
	private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
	public static final int TERRAIN_DEPTH = 20;

	Renderable renderable;
	private NoiseGenerator noiseGenerator;


	public Terrain(Vector2 windowDimensions, int seed){
		groundHeightAtX0 = windowDimensions.y() * ((float) 2 /3);
		this.noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);

	}


	public float groundHeightAt(float x){
		float noise = (float) this.noiseGenerator.noise(x, Block.SIZE * Terrain.NOISE_MULT_FACTOR);
		return groundHeightAtX0 + noise;
	}

	public List<Block> createInRange(int minX, int maxX) {
		List<Block> blocks = new ArrayList<>();
		int blockSize = Block.SIZE;

		int startBlockX = (int) Math.ceil((double) minX / blockSize) * blockSize;
		int endBlockX = (int) Math.floor((double) maxX / blockSize) * blockSize;

		for (int x = startBlockX; x <= endBlockX; x += blockSize) {
			int topY = (int) (Math.floor(groundHeightAt(x) / blockSize) * blockSize);

			for (int i = 0; i < TERRAIN_DEPTH; i++) {

				int y = topY + i * blockSize;
				Block block = new Block(Vector2.of(x, y), new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
				block.setTag("ground");
				blocks.add(block);
			}
		}

		return blocks;

	}



}

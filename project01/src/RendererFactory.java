/**
 * The {@code RendererFactory} class provides a factory method to create instances of various renderers
 * for the tic-tac-toe game. It allows dynamic creation of renderer objects based on the specified type.
 *
 * @author Or Meissonnier
 */
public class RendererFactory {
	/**
	 * Constructs a new RendererFactory instance.
	 */
	public RendererFactory() {
	}

	/**
	 * Builds and returns a renderer object based on the specified type and board size.
	 *
	 * @param type The type of renderer to be created ("none", "console").
	 * @param size The size of the game board.
	 * @return A renderer object corresponding to the specified type, or null if the type is invalid.
	 */
	public Renderer buildRenderer(String type, int size) {
		Renderer renderer;
		switch (type) {
			case "none":
				renderer = new VoidRenderer();
				break;
			case "console":
				renderer = new ConsoleRenderer(size);
				break;
			default:
				renderer = null;
		}
		return renderer;
	}
}

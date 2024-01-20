/**
 * The {@code VoidRenderer} class implements the {@code Renderer} interface but provides
 * no visual rendering.
 * It is used when a game requires no visual representation of the board during its execution.
 *
 * @author Or Meissonnier
 */
public class VoidRenderer implements Renderer {
	/**
	 * Renders the game board in a void manner, providing no visual representation.
	 *
	 * @param board The game board to be rendered.
	 */
	public void renderBoard(Board board) {
		// No rendering is performed in this implementation.
	}
}

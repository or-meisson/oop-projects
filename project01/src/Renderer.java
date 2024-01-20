/**
 * The {@code Renderer} interface defines the contract for rendering the tic-tac-toe game board.
 * Any class implementing this interface is expected to provide an implementation
 * for the renderBoard method,
 * which displays the current state of the game board.
 *
 * @author Or Meissonnier
 */
public interface Renderer {
	/**
	 * Renders and displays the current state of the game board.
	 *
	 * @param board The game board to be rendered.
	 */
	void renderBoard(Board board);
}

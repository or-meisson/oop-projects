/**
 * The {@code Player} interface defines the contract for a player in the tic-tac-toe game.
 * Any class implementing this interface is expected to provide an implementation for
 * the playTurn method, which represents a player's move during the game.
 */
public interface Player {
	void playTurn(Board board, Mark mark);
}

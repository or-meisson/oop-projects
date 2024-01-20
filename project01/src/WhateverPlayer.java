import java.util.Random;

/**
 * The {@code WhateverPlayer} class implements the {@code Player} interface and represents a player
 * who makes random moves on the game board.
 *
 * @author Or Meissonnier
 */
public class WhateverPlayer implements Player {
	/**
	 * Constructs a new instance of the {@code WhateverPlayer} class.
	 */
	public WhateverPlayer() {
	}

	/**
	 * Plays a turn on the specified game board by making random moves until a valid move is found.
	 *
	 * @param board The game board on which the turn is played.
	 * @param mark  The mark (X or O) representing the player.
	 */
	public void playTurn(Board board, Mark mark) {
		Random random = new Random();
		int max = board.getSize();
		int randomRow = random.nextInt(max);
		int randomCol = random.nextInt(max);

		while (!board.putMark(mark, randomRow, randomCol)) {
			randomRow = random.nextInt(max);
			randomCol = random.nextInt(max);
		}
	}
}

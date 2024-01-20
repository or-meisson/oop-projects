import java.util.ArrayList;
import java.util.Random;

/**
 * The {@code CleverPlayer} class represents a player with a strategy to make
 * clever moves on the game board.It utilizes a {@code MarksChecker} to evaluate possible
 * moves and makes decisions based on certain criteria.
 *
 * @author Or Meissonnier
 */
public class CleverPlayer implements Player {
	/**
	 * The instance of MarksChecker used to evaluate possible moves on the game board.
	 */
	private final MarksChecker marksChecker = new MarksChecker();
	/**
	 * A multiplier for evaluating the importance of two marks in a row vertically.
	 */
	private static final int VERTICAL_MULTIPLAYER = 1;
	/**
	 * A multiplier for evaluating the importance of two marks in a row horizontally.
	 */
	private static final int HORIZONTAL_MULTIPLAYER = 100;
	/**
	 * Creates an instance of the {@code WhateverPlayer} class to be used for making random moves.
	 */
	private final WhateverPlayer randomPlayer = new WhateverPlayer();

	/**
	 * Constructs a new CleverPlayer instance.
	 */
	public CleverPlayer() {
	}

	/**
	 * Makes a strategic move on the provided game board by evaluating possible positions.
	 * If there are no positions with two marks in a row, it resorts to a random move.
	 *
	 * @param board The game board.
	 * @param mark  The mark to be placed on the board.
	 */
	public void playTurn(Board board, Mark mark) {
		ArrayList<int[]> possiblePlacesToPutMark = marksChecker.checkPossiblePlacesToPutMark(board);
		ArrayList<int[]> possiblePlacesWith2Marks =
				marksChecker.checkPossiblePlacesWith2Marks(possiblePlacesToPutMark, board, mark
						, HORIZONTAL_MULTIPLAYER, VERTICAL_MULTIPLAYER);
		if (possiblePlacesWith2Marks.isEmpty()) {
			randomPlayer.playTurn(board, mark);
		} else {
			int[] randomCoor = possiblePlacesWith2Marks.
					get(new Random().nextInt(possiblePlacesWith2Marks.size()));
			board.putMark(mark, randomCoor[0], randomCoor[1]);
		}
	}
}
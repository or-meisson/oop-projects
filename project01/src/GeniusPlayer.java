import java.util.ArrayList;
import java.util.Random;

/**
 * The {@code GeniusPlayer} class represents an advanced player with a strategic
 * approach to the tic-tac-toe game.
 * It evaluates possible moves and makes decisions based on creating opportunities
 * to win or block the opponent.
 */
public class GeniusPlayer implements Player {
	//todo readme


	/**
	 * The instance of MarksChecker used to evaluate possible moves on the game board.
	 */
	private final MarksChecker marksChecker = new MarksChecker();

	/**
	 * A multiplier for evaluating the importance of three marks in a row vertically.
	 */
	private static final int VERTICAL_MULTIPLIER = 100;

	/**
	 * A multiplier for evaluating the importance of three marks in a row horizontally.
	 */
	private static final int HORIZONTAL_MULTIPLIER = 1;
	/**
	 * Creates an instance of the {@code WhateverPlayer} class to be used for making random moves.
	 */
	private final WhateverPlayer randomPlayer = new WhateverPlayer();

	/**
	 * Constructs a new GeniusPlayer instance.
	 */
	public GeniusPlayer() {
	}

	/**
	 * Makes a strategic move on the provided game board by evaluating possible positions.
	 * Prioritizes creating opportunities for three marks in a row, blocking the opponent,
	 * and acting like a clever player if no other option.
	 *
	 * @param board The game board.
	 * @param mark  The mark to be placed on the board.
	 */
	public void playTurn(Board board, Mark mark) {
		ArrayList<int[]> possiblePlacesToPutMark = marksChecker.checkPossiblePlacesToPutMark(board);
		ArrayList<int[]> possiblePlacesWith3Marks = marksChecker.checkPossiblePlacesWith3Marks
				(possiblePlacesToPutMark, board, mark,
						HORIZONTAL_MULTIPLIER, VERTICAL_MULTIPLIER);
		if (!possiblePlacesWith3Marks.isEmpty()) {
			int[] randomCoor = possiblePlacesWith3Marks.get
					(new Random().nextInt(possiblePlacesWith3Marks.size()));
			board.putMark(mark, randomCoor[0], randomCoor[1]);
			return;
		}
		if (canDefeatOpponent(mark, board, possiblePlacesToPutMark)) {
			return;
		}
		//act like a clever player
		ArrayList<int[]> possiblePlacesWith2Marks =
				this.marksChecker.checkPossiblePlacesWith2Marks(possiblePlacesToPutMark, board, mark,
						HORIZONTAL_MULTIPLIER, VERTICAL_MULTIPLIER);
		if (!possiblePlacesWith2Marks.isEmpty()) {
			int[] randomCoor = possiblePlacesWith2Marks.get
					(new Random().nextInt(possiblePlacesWith2Marks.size()));
			board.putMark(mark, randomCoor[0], randomCoor[1]);
			return;
		}
		randomPlayer.playTurn(board, mark);
	}

	/**
	 * Checks if the player can defeat the opponent by placing a mark strategically.
	 *
	 * @param mark                    The mark of the current player.
	 * @param board                   The game board.
	 * @param possiblePlacesToPutMark List of possible places to put the mark.
	 * @return {@code true} if the opponent can be defeated, {@code false} otherwise.
	 */
	private boolean canDefeatOpponent(Mark mark, Board board, ArrayList<int[]> possiblePlacesToPutMark) {
		Mark oppositeMark = mark == Mark.X ? Mark.O : Mark.X;
		ArrayList<int[]> possiblePlacesWith3MarksForOpponent =
				marksChecker.checkPossiblePlacesWith3Marks(possiblePlacesToPutMark,
						board, oppositeMark,
						VERTICAL_MULTIPLIER, HORIZONTAL_MULTIPLIER);
		if (!possiblePlacesWith3MarksForOpponent.isEmpty()) {
			int[] randomCoor = possiblePlacesWith3MarksForOpponent.get
					(new Random().nextInt(possiblePlacesWith3MarksForOpponent.size()));
			board.putMark(mark, randomCoor[0], randomCoor[1]);
			return true; //enemy defeated
		}
		return false;
	}

}



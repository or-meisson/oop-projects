/**
 * The {@code HumanPlayer} class represents a player controlled by
 * human input in the tic-tac-toe game. It prompts the user to input coordinates
 * for their move, validates the input, and updates the game board accordingly.
 *
 * @author Or Meissonnier
 */
public class HumanPlayer implements Player {
	/**
	 * Constructs a new HumanPlayer instance.
	 */
	public HumanPlayer() {
	}

	/**
	 * Allows the human player to make a move on the provided game board.
	 * Prompts the user for input, validates the input, and updates the game board
	 * with the specified mark.
	 *
	 * @param board The game board.
	 * @param mark  The mark to be placed on the board (X or O).
	 */
	public void playTurn(Board board, Mark mark) {
		String markInString = mark.toString();
		System.out.println(Constants.playerRequestInputString(markInString));
		int coorInput = KeyboardInput.readInt();
		int[] boardCoordinates = getCoorNum(coorInput);
		boolean isValidCoor = checkCoorValidity(boardCoordinates, board, mark);
		while (!isValidCoor) {
			coorInput = KeyboardInput.readInt();
			boardCoordinates = getCoorNum(coorInput);
			isValidCoor = checkCoorValidity(boardCoordinates, board, mark);
		}
	}

	/**
	 * Converts the given coordinate input into an array of two integers.
	 *
	 * @param coor The coordinate input.
	 * @return An array of two integers representing row and column coordinates.
	 */
	private int[] getCoorNum(int coor) {
		int[] boardCoordinates = new int[2];
		int firstDigit = coor / 10;
		int secondDigit = coor % 10;
		boardCoordinates[0] = firstDigit;
		boardCoordinates[1] = secondDigit;
		return boardCoordinates;
	}

	/**
	 * Checks the validity of the provided coordinates and updates the game board accordingly.
	 *
	 * @param boardCoordinates An array of two integers representing row and column coordinates.
	 * @param board            The game board.
	 * @param mark             The mark to be placed on the board (X or O).
	 * @return {@code true} if the coordinates are valid and the mark is placed on the board,
	 * {@code false} otherwise.
	 */
	private boolean checkCoorValidity(int[] boardCoordinates, Board board, Mark mark) {
		int boardSize = board.getSize();
		int firstCoor = boardCoordinates[0];
		int secondCoor = boardCoordinates[1];
		if (firstCoor < 0 || firstCoor >= boardSize || secondCoor < 0 || secondCoor >= boardSize) {
			System.out.println(Constants.INVALID_COORDINATE);
			return false;
		} else if (!board.putMark(mark, firstCoor, secondCoor)) {
			System.out.println(Constants.OCCUPIED_COORDINATE);
			return false;
		}
		return true;
	}


}

import java.util.ArrayList;

/**
 * The {@code MarksChecker} class provides methods to analyze and check marks on the tic-tac-toe game board.
 * It includes functionality to determine possible places to put marks, check directions for marks,
 * and identify positions with the potential for two or three consecutive marks.
 *
 * @author Or Meissonnier
 */
public class MarksChecker {

	/**
	 * Constructs a new MarksChecker instance.
	 */
	public MarksChecker() {
	}

	/**
	 * Checks all directions (horizontal, vertical, and diagonal) for consecutive marks
	 * from the specified coordinate.
	 * Multipliers control the weight of horizontal and vertical directions for strategic analysis.
	 *
	 * @param coordinate           The starting coordinate for checking directions.
	 * @param board                The game board.
	 * @param mark                 The mark to check for.
	 * @param horizontalMultiplier The multiplier for horizontal directions.
	 * @param verticalMultiplier   The multiplier for vertical directions.
	 * @return The total count of consecutive marks in all directions.
	 */
	public int checkAllDirections(int[] coordinate, Board board, Mark mark,
								  int horizontalMultiplier, int verticalMultiplier) {
		int count = 0;
		count += checkDirection(coordinate, board, mark, 0, -1)
				* horizontalMultiplier; // Left
		count += checkDirection(coordinate, board, mark, 0, 1)
				* horizontalMultiplier;  // Right
		count += checkDirection(coordinate, board, mark, -1, 0)
				* verticalMultiplier;   // Up
		count += checkDirection(coordinate, board, mark, 1, 0)
				* verticalMultiplier;    // Down
		count += checkDirection(coordinate, board, mark, -1, 1);  // Up-Right
		count += checkDirection(coordinate, board, mark, -1, -1); // Up-Left
		count += checkDirection(coordinate, board, mark, 1, 1);   // Down-Right
		count += checkDirection(coordinate, board, mark, 1, -1);  // Down-Left
		return count;
	}

	/**
	 * Checks a specific direction for consecutive marks from the specified coordinate.
	 *
	 * @param coordinate The starting coordinate for checking the direction.
	 * @param board      The game board.
	 * @param mark       The mark to check for.
	 * @param rowOffset  The row offset for the direction.
	 * @param colOffset  The column offset for the direction.
	 * @return 1 if consecutive marks are found, 0 otherwise.
	 */
	public int checkDirection(int[] coordinate, Board board, Mark mark, int rowOffset, int colOffset) {
		int newRow = coordinate[0] + rowOffset;
		int newCol = coordinate[1] + colOffset;
		if (newRow < 0 || newRow >= board.getSize() || newCol < 0 || newCol >= board.getSize()) {
			return 0;
		}
		return board.getMark(newRow, newCol).equals(mark) ? 1 : 0;
	}

	/**
	 * Identifies and returns a list of possible places to put marks on the game board.
	 *
	 * @param board The game board.
	 * @return A list of possible places to put marks represented as coordinate arrays.
	 */
	public ArrayList<int[]> checkPossiblePlacesToPutMark(Board board) {
		int boardSize = board.getSize();
		ArrayList<int[]> possiblePlacesToPutMark = new ArrayList<int[]>();
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				Mark curr_mark = board.getMark(row, col);
				if (curr_mark.equals(Mark.BLANK)) {
					int[] coordinate = {row, col};
					possiblePlacesToPutMark.add(coordinate);
				}
			}
		}
		return possiblePlacesToPutMark;
	}

	/**
	 * Identifies and returns a list of possible places to put marks
	 * where two consecutive marks are possible.
	 *
	 * @param possiblePlacesToPutMark A list of possible places to put marks.
	 * @param board                   The game board.
	 * @param mark                    The mark to check for.
	 * @param horizontalMultiplier    The multiplier for horizontal directions.
	 * @param verticalMultiplier      The multiplier for vertical directions.
	 * @return A list of possible places with the potential for two consecutive marks.
	 */
	public ArrayList<int[]> checkPossiblePlacesWith2Marks
	(ArrayList<int[]> possiblePlacesToPutMark, Board board, Mark mark,
	 int horizontalMultiplier, int verticalMultiplier) {
		ArrayList<int[]> possiblePlacesWith2Marks = new ArrayList<>();
		for (int[] coordinate : possiblePlacesToPutMark) {
			int timesToAddCoor = this.checkAllDirections(coordinate, board, mark,
					horizontalMultiplier, verticalMultiplier);
			for (int i = 0; i < timesToAddCoor; i++) {
				possiblePlacesWith2Marks.add(coordinate);
			}
		}
		return possiblePlacesWith2Marks;
	}


	/**
	 * Identifies and returns a list of possible places to put marks where
	 * three consecutive marks are possible.
	 *
	 * @param possiblePlaces       A list of possible places to put marks.
	 * @param board                The game board.
	 * @param mark                 The mark to check for.
	 * @param horizontalMultiplier The multiplier for horizontal directions.
	 * @param verticalMultiplier   The multiplier for vertical directions.
	 * @return A list of possible places with the potential for three consecutive marks.
	 */
	public ArrayList<int[]> checkPossiblePlacesWith3Marks
	(ArrayList<int[]> possiblePlaces, Board board, Mark mark,
	 int horizontalMultiplier, int verticalMultiplier) {
		ArrayList<int[]> possiblePlacesWith3Marks = new ArrayList<>();
		for (int[] coordinate : possiblePlaces) {
			if (checkDirection(coordinate, board, mark, 0, -1) == 1 &&
					checkDirection(coordinate, board, mark, 0, -2) == 1) {
				for (int i = 0; i < horizontalMultiplier; i++) {
					possiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (checkDirection(coordinate, board, mark, 0, 1) == 1 &&
					checkDirection(coordinate, board, mark, 0, 2) == 1) {
				for (int i = 0; i < horizontalMultiplier; i++) {
					possiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (checkDirection(coordinate, board, mark, -1, 0) == 1 &&
					checkDirection(coordinate, board, mark, -2, 0) == 1) {
				for (int i = 0; i < verticalMultiplier; i++) {
					possiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (checkDirection(coordinate, board, mark, 1, 0) == 1 &&
					checkDirection(coordinate, board, mark, 2, 0) == 1) {
				for (int i = 0; i < verticalMultiplier; i++) {
					possiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (checkDirection(coordinate, board, mark, -1, 1) == 1 &&
					checkDirection(coordinate, board, mark,
							-2, 2) == 1) {
				possiblePlacesWith3Marks.add(coordinate);
			}
			if (checkDirection(coordinate, board, mark, -1, -1) == 1 &&
					checkDirection(coordinate, board, mark, -2, -2) == 1) {
				possiblePlacesWith3Marks.add(coordinate);
			}
			if (checkDirection(coordinate, board, mark, 1, 1) == 1 &&
					checkDirection(coordinate, board, mark, 2, 2) == 1) {
				possiblePlacesWith3Marks.add(coordinate);
			}
			if (checkDirection(coordinate, board, mark, 1, -1) == 1 &&
					checkDirection(coordinate, board, mark, 2, -2) == 1) {
				possiblePlacesWith3Marks.add(coordinate);
			}
		}
		return possiblePlacesWith3Marks;
	}
}


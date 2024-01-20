/**
 * The {@code Game} class represents a tic-tac-toe game with a configurable
 * game board size and win streak. It manages the game loop, player turns,
 * and checks for a winning condition or a full board.
 *
 * @author Or Meissonnier
 */

public class Game {

	/**
	 * The game board for the tic-tac-toe game.
	 */
	private final Board board;

	/**
	 * The size of the game board.
	 */
	private int size;

	/**
	 * The win streak required to win the game.
	 */
	private int winStreak;

	/**
	 * The player representing 'X'.
	 */
	private final Player playerX;

	/**
	 * The player representing 'O'.
	 */
	private final Player playerO;

	/**
	 * The renderer used to display the game board.
	 */
	private final Renderer renderer;

	/**
	 * The MarksChecker used to evaluate winning conditions.
	 */
	private final MarksChecker marksChecker = new MarksChecker();

	/**
	 * Constructs a new Game instance with default size and win streak.
	 *
	 * @param playerX  The player representing 'X'.
	 * @param playerO  The player representing 'O'.
	 * @param renderer The renderer used to display the game board.
	 */
	public Game(Player playerX, Player playerO, Renderer renderer) {
		this.renderer = renderer;
		this.board = new Board();
		this.playerX = playerX;
		this.playerO = playerO;
	}

	/**
	 * Constructs a new Game instance with specified size and win streak.
	 *
	 * @param playerX   The player representing 'X'.
	 * @param playerO   The player representing 'O'.
	 * @param size      The size of the game board.
	 * @param winStreak The win streak required to win the game.
	 * @param renderer  The renderer used to display the game board.
	 */
	public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
		this.renderer = renderer;
		this.size = size;
		this.playerX = playerX;
		this.playerO = playerO;
		if (winStreak < Constants.MIN_WIN_STREAK_SIZE || winStreak > size) {
			this.winStreak = size;
		} else {
			this.winStreak = winStreak;
		}
		this.board = new Board(size);
	}

	/**
	 * Gets the win streak required to win the game.
	 *
	 * @return The win streak.
	 */
	public int getWinStreak() {
		return winStreak;
	}

	/**
	 * Gets the size of the game board.
	 *
	 * @return The size of the game board.
	 */
	public int getBoardSize() {
		return size;
	}

	/**
	 * Checks if there is a win streak for the given mark in any row.
	 *
	 * @param mark         The mark to check for (X or O).
	 * @param row          The row index to start the check.
	 * @param col          The column index to start the check.
	 * @param marksChecker The MarksChecker instance used to evaluate winning conditions.
	 * @return {@code true} if there is a win streak in any row, {@code false} otherwise.
	 */
	private boolean isWinStreakInRows(Mark mark, int row, int col, MarksChecker marksChecker) {
		if (!board.getMark(row, col).equals(mark) || isValidCoordinate(row, col, board)) {
			return false;
		}
		int count = 0;
		while (count < winStreak - 1) {
			int[] coordinate = {row, col + count};
			int isRightCoorWithMark = marksChecker.checkDirection
					(coordinate, board, mark, 0, 1);
			if (isRightCoorWithMark == 0) {
				return false;
			}
			count++;
		}
		return true;
	}

	/**
	 * Checks if there is a win streak for the given mark in any column.
	 *
	 * @param mark         The mark to check for (X or O).
	 * @param row          The row index to start the check.
	 * @param col          The column index to start the check.
	 * @param marksChecker The MarksChecker instance used to evaluate winning conditions.
	 * @return {@code true} if there is a win streak in any column, {@code false} otherwise.
	 */
	private boolean isWinStreakInCols(Mark mark, int row, int col, MarksChecker marksChecker) {
		if (!board.getMark(row, col).equals(mark) || isValidCoordinate(row, col, board)) {
			return false;
		}
		int count = 0;
		while (count < winStreak - 1) {
			int[] coordinate = {row + count, col};
			int isDownCoorWithMark = marksChecker.checkDirection
					(coordinate, board, mark, 1, 0);
			if (isDownCoorWithMark == 0) {
				return false;
			}
			count++;
		}
		return true;

	}

	/**
	 * Checks if there is a win streak for the given mark in the diagonal from left to right.
	 *
	 * @param mark         The mark to check for (X or O).
	 * @param row          The row index to start the check.
	 * @param col          The column index to start the check.
	 * @param marksChecker The MarksChecker instance used to evaluate winning conditions.
	 * @return {@code true} if there is a win streak in the diagonal from left to right,
	 * {@code false} otherwise.
	 */
	private boolean isWinStreakInDiagonalLeftToRight
	(Mark mark, int row, int col, MarksChecker marksChecker) {
		if (!board.getMark(row, col).equals(mark) || isValidCoordinate(row, col, board)) {
			return false;
		}
		int count = 0;
		while (count < winStreak - 1) {
			int[] coordinate = {row + count, col + count};
			int isDownRightCoorWithMark = marksChecker.checkDirection
					(coordinate, board, mark, 1, 1);
			if (isDownRightCoorWithMark == 0) {
				return false;
			}
			count++;
		}
		return true;
	}

	/**
	 * Checks if there is a win streak for the given mark in the diagonal from right to left.
	 *
	 * @param mark         The mark to check for (X or O).
	 * @param row          The row index to start the check.
	 * @param col          The column index to start the check.
	 * @param marksChecker The MarksChecker instance used to evaluate winning conditions.
	 * @return {@code true} if there is a win streak in the diagonal from right to left,
	 * {@code false} otherwise.
	 */
	private boolean isWinStreakInDiagonalRightToLeft
	(Mark mark, int row, int col, MarksChecker marksChecker) {
		if (!board.getMark(row, col).equals(mark) || isValidCoordinate(row, col, board)) {
			return false;
		}
		int count = 0;
		while (count < winStreak - 1) {
			int[] coordinate = {row + count, col - count};
			int isDownLeftCoorWithMark = marksChecker.checkDirection
					(coordinate, board, mark, 1, -1);
			if (isDownLeftCoorWithMark == 0) {
				return false;
			}
			count++;
		}
		return true;
	}

	/**
	 * Checks if the specified coordinates are valid on the game board.
	 *
	 * @param row   The row index to check.
	 * @param col   The column index to check.
	 * @param board The game board to validate against.
	 * @return {@code true} if the coordinates are valid, {@code false} otherwise.
	 */
	private boolean isValidCoordinate(int row, int col, Board board) {
		// Check if the coordinate is within the bounds of the board
		return row < 0 || row >= board.getSize() || col < 0 || col >= board.getSize();
	}

	/**
	 * Checks if there is a win streak for the given mark in any row, column, or diagonal.
	 *
	 * @param mark The mark to check for (X or O).
	 * @return {@code true} if there is a win streak, {@code false} otherwise.
	 */
	private boolean isWin(Mark mark) {
		boolean isWinStreakInRows;
		boolean isWinStreakInCols;
		boolean isWinStreakInDiagonalLeftToRight;
		boolean isWinStreakInDiagonalRightToLeft;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				isWinStreakInRows = isWinStreakInRows(mark, row, col, marksChecker);
				isWinStreakInCols = isWinStreakInCols(mark, row, col, marksChecker);
				isWinStreakInDiagonalLeftToRight = isWinStreakInDiagonalLeftToRight
						(mark, row, col, marksChecker);
				isWinStreakInDiagonalRightToLeft = isWinStreakInDiagonalRightToLeft
						(mark, row, col, marksChecker);
				if (isWinStreakInRows || isWinStreakInCols ||
						isWinStreakInDiagonalLeftToRight || isWinStreakInDiagonalRightToLeft) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Runs the tic-tac-toe game until there is a winner or the board is full.
	 *
	 * @return The mark of the winner (X or O), or BLANK if the game is a draw.
	 */
	public Mark run() {
		while (true) {
			playerX.playTurn(board, Mark.X);
			renderer.renderBoard(board);
			if (isWin(Mark.X)) {
				return Mark.X;
			}
			if (isBoardFull()) {
				return Mark.BLANK;
			}
			playerO.playTurn(board, Mark.O);
			renderer.renderBoard(board);
			if (isWin(Mark.O)) {
				return Mark.O;
			}
			if (isBoardFull()) {
				return Mark.BLANK;
			}
		}
	}

	/**
	 * Checks if the game board is full.
	 *
	 * @return {@code true} if the board is full, {@code false} otherwise.
	 */
	private boolean isBoardFull() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (board.getMark(row, col).equals(Mark.BLANK)) {
					return false; // If any cell is BLANK, the board is not full
				}
			}
		}
		return true; // All cells are non-BLANK, so the board is full
	}
}

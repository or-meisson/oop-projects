/**
 * The {@code Board} class represents a game board for a two-player game.
 * It provides methods to initialize the board, get its size, put marks on it,
 * and retrieve marks at specific positions.
 *
 * @author Or Meissonnier
 */
public class Board {
	/**
	 * The default size of the board.
	 */
	private final int size;
	/**
	 * A 2D array representing the game board with marks.
	 */
	private final Mark[][] myBoard;

	/**
	 * Constructs a new board with the default size.
	 */
	public Board() {
		this(Constants.DEFAULT_BOARD_SIZE);
	}

	/**
	 * Constructs a new board with the specified size.
	 *
	 * @param size The size of the board.
	 */
	public Board(int size) {
		this.myBoard = new Mark[size][size];
		this.size = size;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				myBoard[i][j] = Mark.BLANK;
			}
		}
	}

	/**
	 * Gets the size of the board.
	 *
	 * @return The size of the board.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Puts a mark on the board at the specified row and column.
	 *
	 * @param mark The mark to be placed on the board.
	 * @param row  The row index.
	 * @param col  The column index.
	 * @return {@code true} if the mark was successfully placed,
	 * {@code false} if the position is already occupied.
	 */
	public boolean putMark(Mark mark, int row, int col) {
		if (myBoard[row][col].equals(Mark.BLANK)) {
			myBoard[row][col] = mark;
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the mark at the specified position on the board.
	 *
	 * @param row The row index.
	 * @param col The column index.
	 * @return The mark at the specified position, or BLANK if the position is out of bounds.
	 */
	public Mark getMark(int row, int col) {
		if (row < 0 || row > size || col < 0 || col > size) {
			return Mark.BLANK;
		}
		return myBoard[row][col];
	}
}

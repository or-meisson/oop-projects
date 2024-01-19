public class Game {
	//todo constant of 2
	//todo change imlementaion of checkRightMark etc so it will take row, col and not coordinate
	private Board board;
	private int size;
	private int winStreak;
	private Player playerX;
	private Player playerO;
	private Renderer renderer;

	public Game(Player playerX, Player playerO, Renderer renderer) {
		this.renderer = renderer;
		this.board = new Board();
		this.playerX = playerX;
		this.playerO = playerO;
	}

	public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
		this.renderer = renderer;
		this.size = size;
		this.playerX = playerX;
		this.playerO = playerO;
		if (winStreak < 2 || winStreak > size) {
			this.winStreak = size;
		} else {
			this.winStreak = winStreak;
		}
		this.board = new Board(size);
	}

	public int getWinStreak() {
		return winStreak;
	}

	public int getBoardSize() {
		return size;
	}

//	private boolean checkLeftMark(int[] coordinate, Board board, Mark mark) {
//		int row = coordinate[0];
//		int newCol = coordinate[1] - 1;
//		if (newCol < 0) {
//			return false;
//		}
//		if (board.getMark(row, newCol).equals(mark)) {
//			return true;
//		}
//		return false;
//	}

//	private boolean checkRightMark(int[] coordinate, Board board, Mark mark) {
//		int row = coordinate[0];
//		int newCol = coordinate[1] + 1;
//		if (newCol >= board.getSize()) {
//			return false;
//		}
//		if (board.getMark(row, newCol).equals(mark)) {
//			return true;
//		}
//		return false;
//	}

//	private boolean checkUpMark(int[] coordinate, Board board, Mark mark) {
//		int newRow = coordinate[0] - 1;
//		if (newRow < 0) {
//			return false;
//		}
//		int col = coordinate[1];
//		if (board.getMark(newRow, col).equals(mark)) {
//			return true;
//		}
//		return false;
//	}

//	private boolean checkDownMark(int[] coordinate, Board board, Mark mark) {
//		int newRow = coordinate[0] + 1;
//		if (newRow >= board.getSize()) {
//			return false;
//		}
//		int col = coordinate[1];
//		if (board.getMark(newRow, col).equals(mark)) {
//			return true;
//		}
//		return false;
//	}

//	private boolean checkUpLeftMark(int[] coordinate, Board board, Mark mark) {
//		int newRow = coordinate[0] - 1;
//		int newCol = coordinate[1] - 1;
//		if (newCol < 0 || newRow < 0) {
//			return false;
//		}
//		if (board.getMark(newRow, newCol).equals(mark)) {
//			return true;
//		}
//		return false;
//	}
//
//	private boolean checkUpRightMark(int[] coordinate, Board board, Mark mark) {
//		int newRow = coordinate[0] - 1;
//		if (newRow < 0) {
//			return false;
//		}
//		int newCol = coordinate[1] + 1;
//		if (newCol >= board.getSize()) {
//			return false;
//		}
//		if (board.getMark(newRow, newCol).equals(mark)) {
//			return true;
//		}
//		return false;
//	}
//
//	private boolean checkDownRightMark(int[] coordinate, Board board, Mark mark) {
//		int newRow = coordinate[0] + 1;
//		int newCol = coordinate[1] + 1;
//		if (newRow >= board.getSize() || newCol >= board.getSize()) {
//			return false;
//		}
//		if (board.getMark(newRow, newCol).equals(mark)) {
//			return true;
//		}
//		return false;
//	}
//
//	private boolean checkDownLeftMark(int[] coordinate, Board board, Mark mark) {
//		int newRow = coordinate[0] + 1;
//		if (newRow >= board.getSize()) {
//			return false;
//		}
//		int newCol = coordinate[1] - 1;
//		if (newCol < 0) {
//			return false;
//		}
//		if (board.getMark(newRow, newCol).equals(mark)) {
//			return true;
//		}
//		return false;
//	}

	private boolean isWinStreakInRows(Mark mark, int row, int col, MarksChecker marksChecker) {
		if (!board.getMark(row, col).equals(mark)) {
			return false;
		}
		if (!isValidCoordinate(row, col, board)) {
			return false;
		}
		int count = 0;
		while (count < winStreak - 1) { //maybe <=?
			int[] coordinate = {row, col + count};
			boolean isRightCoorWithMark = marksChecker.checkRightMark(coordinate, board, mark);
			if (!isRightCoorWithMark) {
				return false;
			}
			count++;
		}
		return true;
	}

	private boolean isWinStreakInCols(Mark mark, int row, int col, MarksChecker marksChecker) {
		if (!board.getMark(row, col).equals(mark)) {
			return false;
		}
		if (!isValidCoordinate(row, col, board)) {
			return false;
		}
		int count = 0;
		while (count < winStreak - 1) { //maybe <=?
			int[] coordinate = {row + count, col};
			boolean isDownCoorWithMark = marksChecker.checkDownMark(coordinate, board, mark);
			if (!isDownCoorWithMark) {
				return false;
			}
			count++;
		}
		return true;

	}

	private boolean isWinStreakInDiagL2R(Mark mark, int row, int col, MarksChecker marksChecker) {
		if (!board.getMark(row, col).equals(mark)) {
			return false;
		}
		if (!isValidCoordinate(row, col, board)) {
			return false;
		}
		int count = 0;
		while (count < winStreak - 1) { //maybe <=?
			int[] coordinate = {row + count, col + count};
			boolean isDownRightCoorWithMark = marksChecker.checkDownRightMark(coordinate, board, mark);
			if (!isDownRightCoorWithMark) {
				return false;
			}
			count++;
		}
		return true;
	}

	private boolean isWinStreakInDiagR2L(Mark mark, int row, int col, MarksChecker marksChecker) {
		if (!isValidCoordinate(row, col, board)) {
			return false;
		}
		if (!board.getMark(row, col).equals(mark)) {
			return false;
		}
		int count = 0;
		while (count < winStreak - 1) { //maybe <=?
			int[] coordinate = {row + count, col - count};
			boolean isDownLeftCoorWithMark = marksChecker.checkDownLeftMark(coordinate, board, mark);
			if (!isDownLeftCoorWithMark) {
				return false;
			}
			count++;
		}
		return true;
	}

	private boolean isValidCoordinate(int row, int col, Board board) {
		// Check if the coordinate is within the bounds of the board
		return row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize();
	}

	private boolean isWin(Mark mark) {
		boolean isWinStreakInRows = false;
		boolean isWinStreakInCols = false;
		boolean isWinStreakInDiagL2R = false; //todo shahar said to change
		boolean isWinStreakInDiagR2L = false;
		MarksChecker marksChecker = new MarksChecker();
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				isWinStreakInRows = isWinStreakInRows(mark, row, col, marksChecker);
//				System.out.println(isWinStreakInRows);
				isWinStreakInCols = isWinStreakInCols(mark, row, col, marksChecker);
//				System.out.println(isWinStreakInCols);
				isWinStreakInDiagL2R = isWinStreakInDiagL2R(mark, row, col, marksChecker);
//				System.out.println(isWinStreakInDiagL2R);
				isWinStreakInDiagR2L = isWinStreakInDiagR2L(mark, row, col, marksChecker);
//				System.out.println(isWinStreakInDiagR2L);
				if (isWinStreakInRows || isWinStreakInCols ||
						isWinStreakInDiagL2R || isWinStreakInDiagR2L) {
					return true;
				}
			}

		}
		return false;
	}

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

public class Game {
	//todo constant of 2
	//todo change imlementaion of checkRightMark etc so it will take row, col and not coordinate
	Board board;
	int size;
	int winStreak;
	Player playerX;
	Player playerO;
	Renderer renderer;

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
		}
		this.board = new Board(size);
	}

	public int getWinStreak() {
		return winStreak;
	}

	public int getBoardSize() {
		return size;
	}

	private boolean checkLeftMark(int[] coordinate, Board board, Mark mark){
		int row = coordinate[0];
		int new_col = coordinate[1] - 1;
		if(board.getMark(row, new_col).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkRightMark(int[] coordinate, Board board, Mark mark){
		int row = coordinate[0];
		int new_col = coordinate[1] + 1;
		if(board.getMark(row, new_col).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkUpMark(int[] coordinate, Board board, Mark mark){
		int new_row = coordinate[0] - 1;
		int col = coordinate[1];
		if(board.getMark(new_row, col).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkDownMark(int[] coordinate, Board board, Mark mark){
		int new_row = coordinate[0] + 1;
		int col = coordinate[1];
		if(board.getMark(new_row, col).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkUpLeftMark(int[] coordinate, Board board, Mark mark){
		int new_row = coordinate[0] - 1;
		int new_col = coordinate[1] - 1;
		if(board.getMark(new_row, new_col).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkUpRightMark(int[] coordinate, Board board, Mark mark){
		int new_row = coordinate[0] - 1;
		int new_col = coordinate[1] + 1;
		if(board.getMark(new_row, new_col).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkDownRightMark(int[] coordinate, Board board, Mark mark){
		int new_row = coordinate[0] + 1;
		int new_col = coordinate[1] + 1;
		if(board.getMark(new_row, new_col).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkDownLeftMark(int[] coordinate, Board board, Mark mark){
		int new_row = coordinate[0] + 1;
		int new_col = coordinate[1] - 1;
		if(board.getMark(new_row, new_col).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean isWinStreakInRows(Mark mark, int row, int col){
		int[] coordinate = {row, col};
		int count = 0;
		while(count<winStreak){ //maybe <=?
			boolean isRightCoorWithMark = checkRightMark(coordinate, board, mark);
			if(!isRightCoorWithMark){
				return false;
			}
			count++;
		}
		return true;
	}
	private boolean isWinStreakInCols(Mark mark, int row, int col){
		int[] coordinate = {row, col};
		int count = 0;
		while(count<winStreak){ //maybe <=?
			boolean isDownCoorWithMark = checkDownMark(coordinate, board, mark);
			if(!isDownCoorWithMark){
				return false;
			}
			count++;
		}
		return true;

	}
	private boolean isWinStreakInDiagL2R(Mark mark, int row, int col){
		int[] coordinate = {row, col};
		int count = 0;
		while(count<winStreak){ //maybe <=?
			boolean isDownRightCoorWithMark = checkDownRightMark(coordinate, board, mark);
			if(!isDownRightCoorWithMark){
				return false;
			}
			count++;
		}
		return true;
	}
	private boolean isWinStreakInDiagR2L(Mark mark, int row, int col){
		int[] coordinate = {row, col};
		int count = 0;
		while(count<winStreak){ //maybe <=?
			boolean isDownLeftCoorWithMark = checkDownLeftMark(coordinate, board, mark);
			if(!isDownLeftCoorWithMark){
				return false;
			}
			count++;
		}
		return true;
	}

	private boolean isWin(Mark mark) {
		boolean isWinStreakInRows = false;
		boolean isWinStreakInCols = false;
		boolean isWinStreakInDiagL2R = false;
		boolean isWinStreakInDiagR2L = false;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				isWinStreakInRows = isWinStreakInRows(mark, row, col);
				isWinStreakInCols = isWinStreakInCols(mark, row, col);
				isWinStreakInDiagL2R = isWinStreakInDiagL2R(mark, row, col);
				isWinStreakInDiagR2L = isWinStreakInDiagR2L(mark, row, col);
			}
		}
		if (isWinStreakInRows || isWinStreakInCols ||
				isWinStreakInDiagL2R || isWinStreakInDiagR2L) {
			return true;
		}
		return false;
	}
	public Mark run() {
		while (true) {
			playerX.playTurn(board, Mark.X);
			renderer.renderBoard(board);
			if(isWin(Mark.X)){
				return Mark.X;
			}
			if(isBoardFull()){
				return Mark.BLANK;
			}
			playerO.playTurn(board, Mark.O);
			renderer.renderBoard(board);
			if(isWin(Mark.O)){
				return Mark.O;
			}
			if(isBoardFull()){
				return Mark.BLANK;
			}

		}
	}
	private boolean isBoardFull(){
		for (int row=0; row<size; row++) {
			for (int col=0; col<size; col++) {
				if (board.getMark(row, col).equals(Mark.BLANK)) {
					return false; // If any cell is BLANK, the board is not full
				}
			}
		}
		return true; // All cells are non-BLANK, so the board is full
	}
}

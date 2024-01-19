import java.util.ArrayList;

public class MarksChecker {


	public MarksChecker() {

	}

	public ArrayList<int[]> checkPossiblePlacesToPutMark(Board board) { //todo this is duplicate with genius
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

	public boolean checkLeftMark(int[] coordinate, Board board, Mark mark) {
		int row = coordinate[0];
		int newCol = coordinate[1] - 1;
		if (newCol < 0) {
			return false;
		}
		if (board.getMark(row, newCol).equals(mark)) {
			return true;
		}
		return false;
	}

	public boolean checkRightMark(int[] coordinate, Board board, Mark mark) {
		int row = coordinate[0];
		int newCol = coordinate[1] + 1;
		if (newCol >= board.getSize()) {
			return false;
		}
		if (board.getMark(row, newCol).equals(mark)) {
			return true;
		}
		return false;
	}

	public boolean checkUpMark(int[] coordinate, Board board, Mark mark) {
		int newRow = coordinate[0] - 1;
		if (newRow < 0) {
			return false;
		}
		int col = coordinate[1];
		if (board.getMark(newRow, col).equals(mark)) {
			return true;
		}
		return false;
	}

	public boolean checkDownMark(int[] coordinate, Board board, Mark mark) {
		int newRow = coordinate[0] + 1;
		if (newRow >= board.getSize()) {
			return false;
		}
		int col = coordinate[1];
		if (board.getMark(newRow, col).equals(mark)) {
			return true;
		}
		return false;
	}

	public boolean checkUpLeftMark(int[] coordinate, Board board, Mark mark) {
		int newRow = coordinate[0] - 1;
		int newCol = coordinate[1] - 1;
		if (newCol < 0 || newRow < 0) {
			return false;
		}
		if (board.getMark(newRow, newCol).equals(mark)) {
			return true;
		}
		return false;
	}

	public boolean checkUpRightMark(int[] coordinate, Board board, Mark mark) {
		int newRow = coordinate[0] - 1;
		if (newRow < 0) {
			return false;
		}
		int newCol = coordinate[1] + 1;
		if (newCol >= board.getSize()) {
			return false;
		}
		if (board.getMark(newRow, newCol).equals(mark)) {
			return true;
		}
		return false;
	}

	public boolean checkDownRightMark(int[] coordinate, Board board, Mark mark) {
		int newRow = coordinate[0] + 1;
		int newCol = coordinate[1] + 1;
		if (newRow >= board.getSize() || newCol >= board.getSize()) {
			return false;
		}
		if (board.getMark(newRow, newCol).equals(mark)) {
			return true;
		}
		return false;
	}

	public boolean checkDownLeftMark(int[] coordinate, Board board, Mark mark) {
		int newRow = coordinate[0] + 1;
		if (newRow >= board.getSize()) {
			return false;
		}
		int newCol = coordinate[1] - 1;
		if (newCol < 0) {
			return false;
		}
		if (board.getMark(newRow, newCol).equals(mark)) {
			return true;
		}
		return false;
	}


}
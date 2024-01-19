import java.util.ArrayList;
import java.util.Random;

public class CleverPlayer implements Player {
	private MarksChecker marksChecker = new MarksChecker();

	public CleverPlayer() {
	}

	//todo change imlementaion of checkRightMark etc so it will take row, col and not coordinate

	public void playTurn(Board board, Mark mark) {
		ArrayList<int[]> possiblePlacesToPutMark = marksChecker.checkPossiblePlacesToPutMark(board);
		ArrayList<int[]> possiblePlacesWith2Marks = checkPossiblePlacesWith2Marks(possiblePlacesToPutMark, board, mark);
		if (possiblePlacesWith2Marks.isEmpty()) {
			WhateverPlayer randomPlayer = new WhateverPlayer();
			randomPlayer.playTurn(board, mark);
		} else {
			randomizeCoorAndPutMark(possiblePlacesWith2Marks, board, mark);
		}
	}


	private void randomizeCoorAndPutMark(ArrayList<int[]> CoorArray, Board board, Mark mark) {
		Random random = new Random();
		int randomIdx = random.nextInt(CoorArray.size());
		int[] randomCoor = CoorArray.get(randomIdx);
		int coorRow = randomCoor[0];
		int coorCol = randomCoor[1];
		board.putMark(mark, coorRow, coorCol);
	}


	private ArrayList<int[]> checkPossiblePlacesWith2Marks(ArrayList<int[]> PossiblePlacesToPutMark,
														   Board board, Mark mark) {
//		MarksChecker marksChecker = new MarksChecker();
		ArrayList<int[]> possiblePlacesWith2Marks = new ArrayList<>();
		for (int[] coordinate : PossiblePlacesToPutMark) {
			boolean isLeftCoorWithMark = marksChecker.checkLeftMark(coordinate, board, mark);
			boolean isRightCoorWithMark = marksChecker.checkRightMark(coordinate, board, mark);
			boolean isUpCoorWithMark = marksChecker.checkUpMark(coordinate, board, mark);
			boolean isDownCoorWithMark = marksChecker.checkDownMark(coordinate, board, mark);
			boolean isUpRightCoorWithMark = marksChecker.checkUpRightMark(coordinate, board, mark);
			boolean isUpLeftCoorWithMark = marksChecker.checkUpLeftMark(coordinate, board, mark);
			boolean isDownRightCoorWithMark = marksChecker.checkDownRightMark(coordinate, board, mark);
			boolean isDownLeftCoorWithMark = marksChecker.checkDownLeftMark(coordinate, board, mark);

			if (isLeftCoorWithMark || isRightCoorWithMark || isUpCoorWithMark || isDownCoorWithMark ||
					isUpRightCoorWithMark || isUpLeftCoorWithMark || isDownRightCoorWithMark || isDownLeftCoorWithMark) {
				// Add the coordinate to the ArrayList
				possiblePlacesWith2Marks.add(coordinate);
			}
		}
		return possiblePlacesWith2Marks;
	}

}
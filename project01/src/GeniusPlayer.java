import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeniusPlayer implements Player {
	//todo: constant of 2(coordinate), constant of 8
	//todo create a new class - figure out what to do with the duplicated implementation - forumהוספת מחלקה
	//todo change imlementaion of checkRightMark etc so it will take row, col and not coordinate
	private MarksChecker marksChecker = new MarksChecker();


	public GeniusPlayer() {
	}

	public void playTurn(Board board, Mark mark) {
		ArrayList<int[]> PossiblePlacesToPutMark = marksChecker.checkPossiblePlacesToPutMark(board);
		ArrayList<int[]> possiblePlacesWith3Marks = checkPossiblePlacesWith3Marks(PossiblePlacesToPutMark, board, mark);
//		for (int[] coordinate : PossiblePlacesToPutMark) {
//			System.out.println(Arrays.toString(coordinate));
//		}
//		for (int[] coordinate : possiblePlacesWith3Marks) {
//			System.out.println(Arrays.toString(coordinate));
//		}
		if (possiblePlacesWith3Marks.isEmpty()) {
			//randomize an empty cell from PossiblePlacesToPutMark
//			randomizeCoorAndPutMark(PossiblePlacesToPutMark, board, mark);
			CleverPlayer cleverPlayer = new CleverPlayer();
			cleverPlayer.playTurn(board, mark);
		} else {
			// //randomize an empty cell from possiblePlacesWith2Marks
			randomizeCoorAndPutMark(possiblePlacesWith3Marks, board, mark);
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
//
//	private ArrayList<int[]> checkPossiblePlacesToPutMark(Board board) { //todo this is duplicate with clever
//		int boardSize = board.getSize();
//		int maxSize = boardSize * boardSize;
//		ArrayList<int[]> possiblePlacesToPutMark = new ArrayList<int[]>();
////		int count = 0;
//		for (int row = 0; row < boardSize; row++) {
//			for (int col = 0; col < boardSize; col++) {
//				Mark curr_mark = board.getMark(row, col);
//				if (curr_mark.equals(Mark.BLANK)) {
//					int[] coordinate = {row, col};
//					possiblePlacesToPutMark.add(coordinate);
////					possiblePlacesToPutMark[count][0] = row;
////					possiblePlacesToPutMark[count][1] = col;
////					count++;
//
//				}
//			}
//		}
//		return possiblePlacesToPutMark;
//	}

	private ArrayList<int[]> checkPossiblePlacesWith3Marks(ArrayList<int[]> PossiblePlacesToPutMark,
														   Board board, Mark mark) {
//		int arraySize = PossiblePlacesToPutMark.length * 8;
		ArrayList<int[]> PossiblePlacesWith3Marks = new ArrayList<>();

//		int count = 0;
		for (int[] coordinate : PossiblePlacesToPutMark) {
			boolean isLeftCoorWithMark = marksChecker.checkLeftMark(coordinate, board, mark);
			boolean isRightCoorWithMark = marksChecker.checkRightMark(coordinate, board, mark);
			boolean isUpCoorWithMark = marksChecker.checkUpMark(coordinate, board, mark);
			boolean isDownCoorWithMark = marksChecker.checkDownMark(coordinate, board, mark);
			boolean isUpRightCoorWithMark = marksChecker.checkUpRightMark(coordinate, board, mark);
			boolean isUpLeftCoorWithMark = marksChecker.checkUpLeftMark(coordinate, board, mark);
			boolean isDownRightCoorWithMark = marksChecker.checkDownRightMark(coordinate, board, mark);
			boolean isDownLeftCoorWithMark = marksChecker.checkDownLeftMark(coordinate, board, mark);


			if (isLeftCoorWithMark) {
				int[] leftCoor = {coordinate[0], coordinate[1] - 1};
				boolean isLeftLeftCoorWithMark = marksChecker.checkLeftMark(leftCoor, board, mark);
				if (isLeftLeftCoorWithMark) {
					PossiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (isRightCoorWithMark) {
				int[] rightCoor = {coordinate[0], coordinate[1] + 1};
				boolean isRightRightCoorWithMark = marksChecker.checkRightMark(rightCoor, board, mark);
				if (isRightRightCoorWithMark) {
					PossiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (isUpCoorWithMark) {
				int[] upCoor = {coordinate[0] - 1, coordinate[1]};
				boolean isUpUpCoorWithMark = marksChecker.checkUpMark(upCoor, board, mark);
				if (isUpUpCoorWithMark) {
					PossiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (isDownCoorWithMark) {
				int[] downCoor = {coordinate[0] + 1, coordinate[1]};
				boolean isDownDownCoorWithMark = marksChecker.checkDownMark(downCoor, board, mark);
				if (isDownDownCoorWithMark) {
					PossiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (isUpRightCoorWithMark) {
				int[] upRightCoor = {coordinate[0] - 1, coordinate[1] + 1};
				boolean isUpRightX2CoorWithMark = marksChecker.checkUpRightMark(upRightCoor, board, mark);
				if (isUpRightX2CoorWithMark) {
					PossiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (isUpLeftCoorWithMark) {
				int[] upLeftCoor = {coordinate[0] - 1, coordinate[1] - 1};
				boolean isUpLeftX2CoorWithMark = marksChecker.checkUpLeftMark(upLeftCoor, board, mark);
				if (isUpLeftX2CoorWithMark) {
					PossiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (isDownRightCoorWithMark) {
				int[] downRightCoor = {coordinate[0] + 1, coordinate[1] + 1};
				boolean isDownRightX2CoorWithMark = marksChecker.checkDownRightMark(downRightCoor, board, mark);
				if (isDownRightX2CoorWithMark) {
					PossiblePlacesWith3Marks.add(coordinate);
				}
			}
			if (isDownLeftCoorWithMark) {
				int[] downLeftCoor = {coordinate[0] + 1, coordinate[1] - 1};
				boolean isDownLeftX2CoorWithMark = marksChecker.checkDownLeftMark(downLeftCoor, board, mark);
				if (isDownLeftX2CoorWithMark) {
					PossiblePlacesWith3Marks.add(coordinate);
				}
			}

		}
		return PossiblePlacesWith3Marks;

	}
}
import java.util.Random;

public class GeniusPlayer implements Player{
	//todo: constant of 2(coordinate), constant of 8
	//todo create a new class - figure out what to do with the duplicated implementation - forumהוספת מחלקה
	//todo change imlementaion of checkRightMark etc so it will take row, col and not coordinate


	public GeniusPlayer() {
	}
	public void playTurn(Board board, Mark mark){
		int[][] PossiblePlacesToPutMark = checkPossiblePlacesToPutMark(board);
//		int[][] possiblePlacesWith2Marks = checkPossiblePlacesWith2Marks(PossiblePlacesToPutMark, board, mark);
		int[][] possiblePlacesWith3Marks = checkPossiblePlacesWith3Marks(PossiblePlacesToPutMark, board, mark);
		if(isArrayEmpty(possiblePlacesWith3Marks)){
			//randomize an empty cell from PossiblePlacesToPutMark
//			randomizeCoorAndPutMark(PossiblePlacesToPutMark, board, mark);
			CleverPlayer cleverPlayer = new CleverPlayer();
			cleverPlayer.playTurn(board, mark);
		}
		// //randomize an empty cell from possiblePlacesWith2Marks
		randomizeCoorAndPutMark(possiblePlacesWith3Marks, board, mark);
	}
	private boolean isArrayEmpty(int[][] array) {
		return array.length == 0;
	}
	private void randomizeCoorAndPutMark(int[][] CoorArray, Board board, Mark mark){
		Random random = new Random();
		int randomIdx = random.nextInt(CoorArray.length);
		int[] randomCoor = CoorArray[randomIdx];
		int coorRow = randomCoor[0];
		int coorCol = randomCoor[1];
		board.putMark(mark, coorRow, coorCol);
	}
	private int[][] checkPossiblePlacesToPutMark(Board board){
		int boardSize = board.getSize();
		int maxSize = boardSize * boardSize;

		int[][] possiblePlacesToPutMark = new int[maxSize][2];
		int count = 0;

		for(int row=0; row<boardSize; row++){
			for (int col=0; col< boardSize; col++){
				Mark curr_mark = board.getMark(row, col);
				if (curr_mark.equals(Mark.BLANK)){
					possiblePlacesToPutMark[count][0] = row;
					possiblePlacesToPutMark[count][1] = col;
					count++;

				}
			}
		}
		return possiblePlacesToPutMark;
	}
	private int[][] checkPossiblePlacesWith3Marks(int[][] PossiblePlacesToPutMark,
												  Board board, Mark mark){
		int arraySize = PossiblePlacesToPutMark.length * 8;
		int[][] PossiblePlacesWith3Marks = new int[arraySize][2];
		int count = 0;
		for(int[] coordinate : PossiblePlacesToPutMark){
			boolean isLeftCoorWithMark = checkLeftMark(coordinate, board, mark);
			boolean isRightCoorWithMark = checkRightMark(coordinate, board, mark);
			boolean isUpCoorWithMark = checkUpMark(coordinate, board, mark);
			boolean isDownCoorWithMark = checkDownMark(coordinate, board, mark);
			boolean isUpRightCoorWithMark = checkUpRightMark(coordinate, board, mark);
			boolean isUpLeftCoorWithMark = checkUpLeftMark(coordinate, board, mark);
			boolean isDownRightCoorWithMark = checkDownRightMark(coordinate, board, mark);
			boolean isDownLeftCoorWithMark = checkDownLeftMark(coordinate, board, mark);

			if(isLeftCoorWithMark){
				int[] leftCoor = {coordinate[0], coordinate[1]-1};
				boolean isLeftLeftCoorWithMark = checkLeftMark(leftCoor, board, mark);
				if(isLeftLeftCoorWithMark){
					PossiblePlacesWith3Marks[count] = coordinate;
				count++;
				}
			}
			if(isRightCoorWithMark){
				int[] rightCoor = {coordinate[0], coordinate[1]+1};
				boolean isRightRightCoorWithMark = checkRightMark(rightCoor, board, mark);
				if(isRightRightCoorWithMark){
					PossiblePlacesWith3Marks[count] = coordinate;
					count++;
				}
			}
			if(isUpCoorWithMark){
				int[] upCoor = {coordinate[0]-1, coordinate[1]};
				boolean isUpUpCoorWithMark = checkUpMark(upCoor, board, mark);
				if(isUpUpCoorWithMark){
					PossiblePlacesWith3Marks[count] = coordinate;
					count++;
				}
			}
			if(isDownCoorWithMark){
				int[] downCoor = {coordinate[0]+1, coordinate[1]};
				boolean isDownDownCoorWithMark = checkDownMark(downCoor, board, mark);
				if(isDownDownCoorWithMark){
					PossiblePlacesWith3Marks[count] = coordinate;
					count++;
				}
			}
			if(isUpRightCoorWithMark){
				int[] upRightCoor = {coordinate[0]-1, coordinate[1]+1};
				boolean isUpRightX2CoorWithMark = checkUpRightMark(upRightCoor, board, mark);
				if(isUpRightX2CoorWithMark){
					PossiblePlacesWith3Marks[count] = coordinate;
					count++;
				}
			}
			if(isUpLeftCoorWithMark){
				int[] upLeftCoor = {coordinate[0]-1, coordinate[1]-1};
				boolean isUpLeftX2CoorWithMark = checkUpLeftMark(upLeftCoor, board, mark);
				if(isUpLeftX2CoorWithMark){
					PossiblePlacesWith3Marks[count] = coordinate;
					count++;
				}
			}
			if(isDownRightCoorWithMark){
				int[] downRightCoor = {coordinate[0]+1, coordinate[1]+1};
				boolean isDownRightX2CoorWithMark = checkDownRightMark(downRightCoor, board, mark);
				if(isDownRightX2CoorWithMark){
					PossiblePlacesWith3Marks[count] = coordinate;
					count++;
				}
			}
			if(isDownLeftCoorWithMark){
				int[] downLeftCoor = {coordinate[0]+1, coordinate[1]-1};
				boolean isDownLeftX2CoorWithMark = checkDownLeftMark(downLeftCoor, board, mark);
				if(isDownLeftX2CoorWithMark){
					PossiblePlacesWith3Marks[count] = coordinate;
					count++;
				}
			}

		}
		return PossiblePlacesWith3Marks;

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

}

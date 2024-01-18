import java.util.Random;

public class CleverPlayer implements Player{
	public CleverPlayer() {
	}
	//todo: constant of 2(coordinate), constant of 8
	//todo change imlementaion of checkRightMark etc so it will take row, col and not coordinate

	public void playTurn(Board board, Mark mark){
		int[][] PossiblePlacesToPutMark = checkPossiblePlacesToPutMark(board);
		int[][] possiblePlacesWith2Marks = checkPossiblePlacesWith2Marks(PossiblePlacesToPutMark, board, mark);
		if(isArrayEmpty(possiblePlacesWith2Marks)){
			//randomize an empty cell from PossiblePlacesToPutMark
//			randomizeCoorAndPutMark(PossiblePlacesToPutMark, board, mark);
			WhateverPlayer randomPlayer = new WhateverPlayer();
			randomPlayer.playTurn(board, mark);
		}
		// //randomize an empty cell from possiblePlacesWith2Marks
		randomizeCoorAndPutMark(possiblePlacesWith2Marks, board, mark);
	}
	private boolean checkLeftMark(int[] coordinate, Board board, Mark mark){
		int row = coordinate[0];
		int newCol = coordinate[1] - 1;
		if(board.getMark(row, newCol).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkRightMark(int[] coordinate, Board board, Mark mark){
		int row = coordinate[0];
		int newCol = coordinate[1] + 1;
		if(board.getMark(row, newCol).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkUpMark(int[] coordinate, Board board, Mark mark){
		int newRow = coordinate[0] - 1;
		int col = coordinate[1];
		if(board.getMark(newRow, col).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkDownMark(int[] coordinate, Board board, Mark mark){
		int newRow = coordinate[0] + 1;
		int col = coordinate[1];
		if(board.getMark(newRow, col).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkUpLeftMark(int[] coordinate, Board board, Mark mark){
		int newRow = coordinate[0] - 1;
		int newCol = coordinate[1] - 1;
		if(board.getMark(newRow, newCol).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkUpRightMark(int[] coordinate, Board board, Mark mark){
		int newRow = coordinate[0] - 1;
		int newCol = coordinate[1] + 1;
		if(board.getMark(newRow, newCol).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkDownRightMark(int[] coordinate, Board board, Mark mark){ //TODO MAYBE SIMPLIFY
		int newRow = coordinate[0] + 1;
		int newCol = coordinate[1] + 1;
		if(board.getMark(newRow, newCol).equals(mark)){
			return true;
		}
		return false;
	}
	private boolean checkDownLeftMark(int[] coordinate, Board board, Mark mark){
		int newRow = coordinate[0] + 1;
		int newCol = coordinate[1] - 1;
		if(board.getMark(newRow, newCol).equals(mark)){
			return true;
		}
		return false;
	}


	private void randomizeCoorAndPutMark(int[][] CoorArray, Board board, Mark mark){
		Random random = new Random();
		int randomIdx = random.nextInt(CoorArray.length);
		int[] randomCoor = CoorArray[randomIdx];
		int coorRow = randomCoor[0];
		int coorCol = randomCoor[1];
		board.putMark(mark, coorRow, coorCol);
	}
	private boolean isArrayEmpty(int[][] array) {
		return array.length == 0;
	}
	private int[][] checkPossiblePlacesWith2Marks(int[][] PossiblePlacesToPutMark,
												  Board board, Mark mark){
		int arraySize = PossiblePlacesToPutMark.length * 8;
		int[][] possiblePlacesWith2Marks = new int[arraySize][2];
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
				possiblePlacesWith2Marks[count] = coordinate;
				count++;
			}
			if(isRightCoorWithMark){
				possiblePlacesWith2Marks[count] = coordinate;
				count++;
			}
			if(isUpCoorWithMark){
				possiblePlacesWith2Marks[count] = coordinate;
				count++;
			}
			if(isDownCoorWithMark){
				possiblePlacesWith2Marks[count] = coordinate;
				count++;
			}
			if(isUpRightCoorWithMark){
				possiblePlacesWith2Marks[count] = coordinate;
				count++;
			}
			if(isUpLeftCoorWithMark){
				possiblePlacesWith2Marks[count] = coordinate;
				count++;
			}
			if(isDownRightCoorWithMark){
				possiblePlacesWith2Marks[count] = coordinate;
				count++;
			}
			if(isDownLeftCoorWithMark){
				possiblePlacesWith2Marks[count] = coordinate;
				count++;
			}

		}
		return possiblePlacesWith2Marks;
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
}

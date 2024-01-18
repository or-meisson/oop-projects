public class CleverPlayer {
	public CleverPlayer() {
	}
	private checkLeft(){

	}
	public void playTurn(Board board, Mark mark){
		int[][] PossiblePlacesToPutMark = checkPossiblePlacesToPutMark(board);
		int[][] possiblePlacesWith2Marks = checkPossiblePlacesWith2Marks(PossiblePlacesToPutMark)

	}
	private int[][] checkPossiblePlacesWith2Marks(int[][] PossiblePlacesToPutMark){
		int arraySize = PossiblePlacesToPutMark.length;

	}
	private int[][] checkPossiblePlacesToPutMark(Board board){
		int boardSize = board.getSize();
		int maxSize = boardSize * boardSize;

		int[][] PossiblePlacesToPutMark = new int[maxSize][2];
		int count = 0;

		for(int row=0; row<boardSize; row++){
			for (int col=0; col< boardSize; col++){
				Mark curr_mark = board.getMark(row, col);
				if (curr_mark.equals(Mark.BLANK)){
					PossiblePlacesToPutMark[count][0] = row;
					PossiblePlacesToPutMark[count][1] = col;
					count++;

				}
			}
		}
	}
}

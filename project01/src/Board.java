public class Board {
	int DEFAULT_BOARD_SIZE = 4;
	public static final int FIRST_INDEX_OF_BOARD = 0;

	public int getFirstIndex() {
		return FIRST_INDEX_OF_BOARD;
	}

	int size;
	Mark[][] myBoard;

	public Board() {
		this.myBoard = new Mark[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
		this.size = DEFAULT_BOARD_SIZE;
		for(int i=0; i< size; i++){
			for(int j=0; j<size;j++){
				myBoard[i][j] = Mark.BLANK;
			}
		}
	}

	public Board(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}
	public boolean putMark(Mark mark, int row, int col){
		if (myBoard[row][col].equals(Mark.BLANK)){
			myBoard[row][col] = mark;
			return true;
		}
		return false;
//		else{
//			System.out.println(OCCUPIED_SPOT_ERROR_MESSAGE); //not here
//		}
	}

	public Mark getMark(int row, int col){
		if(row<0 || row > size || col <0 || col>size){
			return Mark.BLANK;
		}
		return myBoard[row][col];
	}
}

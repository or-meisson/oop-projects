public class Board {
	private static int DEFAULT_BOARD_SIZE = 4;
//	public static final int FIRST_INDEX_OF_BOARD = 0;

//	public int getFirstIndex() {
//		return FIRST_INDEX_OF_BOARD;
//	}

	private int size;
	private Mark[][] myBoard;

	public Board() {
	this(DEFAULT_BOARD_SIZE);
	}

	public Board(int size) {
		this.myBoard = new Mark[size][size];
		this.size = size;
		for(int i=0; i< size; i++){
			for(int j=0; j<size;j++){
				myBoard[i][j] = Mark.BLANK;
			}
		}

	}

	public int getSize() {
		return size;
	}
	public boolean putMark(Mark mark, int row, int col){ //todo check validity??
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

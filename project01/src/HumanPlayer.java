public class HumanPlayer implements Player{
	private String X_PLAYER_TYPE_COOR_MESSAGE = "Player X, type coordinates: "; //todo check constants
	private String O_PLAYER_TYPE_COOR_MESSAGE = "Player O, type coordinates: ";
	private String INVALID_COOR_MESSAGE = "Invalid mark position, please choose a different" +
			"position.\n Invalid coordinate, type again: ";
	private String OCCUPIED_SPOT_ERROR_MESSAGE = "Mark position is already occupied.\n" +
			" Invalid coordinate, type again: ";

	public HumanPlayer() {
	}
	@Override
	public void playTurn(Board board, Mark mark){
		switch (mark.toString()){
			case "O":
				System.out.println(O_PLAYER_TYPE_COOR_MESSAGE);
				break;
			case "X":
				System.out.println(X_PLAYER_TYPE_COOR_MESSAGE);
				break;
		}

		int coorInput = KeyboardInput.readInt();
		int[] boardCoordinates = getCoorNum(coorInput);
		boolean isValidCoor = checkCoorValidity(boardCoordinates, board, mark);
		while (!isValidCoor){

			coorInput = KeyboardInput.readInt();
			boardCoordinates = getCoorNum(coorInput);
			isValidCoor = checkCoorValidity(boardCoordinates, board, mark);
		}


	}
	private int[] getCoorNum(int coor){
		int[] boardCoordinates = new int[2];
		int firstDigit = coor / 10;
		int secondDigit = coor % 10;
		boardCoordinates[0] = firstDigit;
		boardCoordinates[1] = secondDigit;
		return boardCoordinates;
	}
	private boolean checkCoorValidity(int[] boardCoordinates, Board board, Mark mark) {
		int boardSize = board.getSize();
		int firstCoor = boardCoordinates[0];
		int secondCoor = boardCoordinates[1];
		if (firstCoor < 0 || firstCoor >= boardSize || secondCoor < 0 || secondCoor >= boardSize) {
			System.out.println(INVALID_COOR_MESSAGE);
			return false;
		}
		else if (!board.putMark(mark, firstCoor, secondCoor)) {
			System.out.println(OCCUPIED_SPOT_ERROR_MESSAGE);
			return false;
		}
		return true;
	}


}

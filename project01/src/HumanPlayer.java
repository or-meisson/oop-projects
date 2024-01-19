public class HumanPlayer implements Player {
	//todo why when i enter coordinates that are taken it doesnt print msg?

	public HumanPlayer() {
	}

	@Override
	public void playTurn(Board board, Mark mark) {
		String markInString = mark.toString();
		System.out.println(Constants.playerRequestInputString(markInString));
//		switch (markInString){
//			case "O":
//				System.out.println(Constants.playerRequestInputString(markInString));
//				break;
//			case "X":
//				System.out.println(X_PLAYER_TYPE_COOR_MESSAGE);
//				break;
//		}

		int coorInput = KeyboardInput.readInt();
		int[] boardCoordinates = getCoorNum(coorInput);
		boolean isValidCoor = checkCoorValidity(boardCoordinates, board, mark);
		while (!isValidCoor) {

			coorInput = KeyboardInput.readInt();
			boardCoordinates = getCoorNum(coorInput);
			isValidCoor = checkCoorValidity(boardCoordinates, board, mark);
		}


	}

	private int[] getCoorNum(int coor) {
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
			System.out.println(Constants.INVALID_COORDINATE);
			return false;
		} else if (!board.putMark(mark, firstCoor, secondCoor)) {
			System.out.println(Constants.OCCUPIED_COORDINATE);
			return false;
		}
		return true;
	}


}

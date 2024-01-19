import java.util.Random;

public class WhateverPlayer implements Player {
	public WhateverPlayer() {
	}

	@Override
	public void playTurn(Board board, Mark mark) {
		Random random = new Random();
//		int min = board.DEFAULT_BOARD_SIZE;
		int max = board.getSize();
		int randomRow = random.nextInt(max);
		int randomCol = random.nextInt(max);

		while (!board.putMark(mark, randomRow, randomCol)) {
			randomRow = random.nextInt(max);
			randomCol = random.nextInt(max);
		}
//		board.putMark(mark, randomRow, randomCol);

	}


}

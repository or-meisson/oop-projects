import java.util.Random;

public class WhateverPlayer implements Player{
	public WhateverPlayer() {
	}
	@Override
	public void playTurn(Board board, Mark mark) {
		Random random = new Random();
		int min = board.DEFAULT_BOARD_SIZE;
		int max = board.getSize();
		int randomRow = random.nextInt(max - min) + min;
		int randomCol = random.nextInt(max - min) + min;
		board.putMark(mark, randomRow, randomCol);

	}


}

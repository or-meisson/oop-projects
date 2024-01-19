public class Tournament {
	private int rounds;
	private Renderer renderer;
	private Player player1;
	private Player player2;
	private String RESULTS_MESSAGE = "######### Results #########";

	public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
		this.rounds = rounds;
		this.renderer = renderer;
		this.player1 = player1;
		this.player2 = player2;
	}

	public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
		int winFor1 = 0;
		int winFor2 = 0;
		int ties = 0;
		for (int i = 0; i < rounds; i++) {
			boolean isPlayer1X = (i % 2 == 0);
			Game game = new Game(player1, player2, size, winStreak, renderer); //todo what about the default constructor here?
			Mark winner = game.run();
			if ((winner.equals(Mark.X) && isPlayer1X) || (!isPlayer1X && winner.equals(Mark.O))) {
				winFor1++;
			} else if (winner.equals(Mark.BLANK)) {
				ties++;
			} else {
				winFor2++;
			}
			Player temp = player1;
			player1 = player2;
			player2 = temp;
			//change X - O

		}
		System.out.println(RESULTS_MESSAGE);
		System.out.println("Player 1, " + playerName1 + " won: " + winFor1 + " rounds");
		System.out.println("Player 2, " + playerName2 + " won: " + winFor2 + " rounds");
		System.out.println("Ties: " + ties);

	}

	public static void main(String[] args) {
		int roundsNum = Integer.parseInt(args[0]);
		int size = Integer.parseInt(args[1]);
		int winStreak = Integer.parseInt(args[2]);
		RendererFactory rendererFactory = new RendererFactory();
		Renderer renderer1 = rendererFactory.buildRenderer(args[3], size);
		if (renderer1 == null) {
			System.out.println(Constants.UNKNOWN_RENDERER_NAME);
			return;
		}
		PlayerFactory playerFactory = new PlayerFactory();
		Player player1 = playerFactory.buildPlayer(args[4].toLowerCase());
		Player player2 = playerFactory.buildPlayer(args[5].toLowerCase());
		if (player1 == null || player2 == null) {
			System.out.println(Constants.UNKNOWN_PLAYER_NAME);
			return;
		}
		Tournament tournament = new Tournament(roundsNum, renderer1, player1, player2);
		tournament.playTournament(size, winStreak, args[4].toLowerCase(), args[5].toLowerCase());
	}
}


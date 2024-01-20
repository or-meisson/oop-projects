/**
 * The {@code Tournament} class represents a series of tic-tac-toe games played between two players.
 * It manages the overall tournament structure, tracks wins, ties, and prints the results.
 *
 * @author Or Meissonnier
 */
public class Tournament {
	/**
	 * The number of rounds to be played in the tournament.
	 */
	private final int rounds;

	/**
	 * The renderer used to display game boards during the tournament.
	 */
	private final Renderer renderer;

	/**
	 * The first player participating in the tournament.
	 */
	private Player player1;
	/**
	 * The second player participating in the tournament.
	 */
	private Player player2;

	/**
	 * Constructs a new Tournament instance with the specified number of rounds,
	 * renderer, and players.
	 *
	 * @param rounds   The number of rounds in the tournament.
	 * @param renderer The renderer used to display game boards.
	 * @param player1  The first player participating in the tournament.
	 * @param player2  The second player participating in the tournament.
	 */
	public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
		this.rounds = rounds;
		this.renderer = renderer;
		this.player1 = player1;
		this.player2 = player2;
	}

	/**
	 * Plays the tournament with the specified game parameters and prints the results.
	 *
	 * @param size        The size of the game board.
	 * @param winStreak   The required streak of marks for a win.
	 * @param playerName1 The name of the first player.
	 * @param playerName2 The name of the second player.
	 */
	public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
		int winFor1 = 0;
		int winFor2 = 0;
		int ties = 0;
		for (int i = 0; i < rounds; i++) {
			boolean isPlayer1X = (i % 2 == 0);
			Game game = new Game(player1, player2, size, winStreak, renderer);
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
		}
		printResults(playerName1, playerName2, winFor1, winFor2, ties);

	}

	/**
	 * Prints the results of the tournament, including the number of wins for each player and ties.
	 *
	 * @param playerName1 The name of the first player.
	 * @param playerName2 The name of the second player.
	 * @param winFor1     The number of rounds won by the first player.
	 * @param winFor2     The number of rounds won by the second player.
	 * @param ties        The number of tied rounds.
	 */
	private void printResults
	(String playerName1, String playerName2, int winFor1, int winFor2, int ties) {
		System.out.println(Constants.RESULTS_MESSAGE);
		System.out.println("Player 1, " + playerName1 + " won: " + winFor1 + " rounds");
		System.out.println("Player 2, " + playerName2 + " won: " + winFor2 + " rounds");
		System.out.println("Ties: " + ties);
	}

	/**
	 * The main method that runs the tournament based on command-line arguments.
	 * Expected arguments: [roundsNum size winStreak rendererType1 playerType1
	 * rendererType2 playerType2]
	 *
	 * @param args Command-line arguments specifying the parameters for the tournament.
	 */
	public static void main(String[] args) {
		int roundsNum = Integer.parseInt(args[0]);
		int size = Integer.parseInt(args[1]);
		int winStreak = Integer.parseInt(args[2]);
		RendererFactory rendererFactory = new RendererFactory();
		Renderer renderer1 = rendererFactory.buildRenderer(args[3].toLowerCase(), size);
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


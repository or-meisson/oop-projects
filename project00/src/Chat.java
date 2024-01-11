import java.util.Scanner;

/**
 * Main class for the ChatterBot chat simulation.
 * It creates two ChatterBot instances and simulates a conversation between them.
 * The ChatterBots respond to legal and illegal requests, handling placeholders and randomness.
 *
 * @author Or Meissonnier
 */

class Chat {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String PLACEHOLDER_FOR_REQUESTED_PHRASE = ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE;
		String PLACEHOLDER_FOR_ILLEGAL_REQUEST = ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST;


		String[] CHAT_1_ILLEGAL_RESPONSES_ARR = new String[]{"what " + PLACEHOLDER_FOR_ILLEGAL_REQUEST,
				"say what? " + PLACEHOLDER_FOR_ILLEGAL_REQUEST + "? what's " +
						PLACEHOLDER_FOR_ILLEGAL_REQUEST + "?"};
		String[] CHAT_2_ILLEGAL_RESPONSES_ARR = new String[]{"whaaat " + PLACEHOLDER_FOR_ILLEGAL_REQUEST,
				"sorry, i can't say " +
						PLACEHOLDER_FOR_ILLEGAL_REQUEST};


		String[] CHAT_1_LEGAL_RESPONSES_ARR = new String[]{"say " + PLACEHOLDER_FOR_REQUESTED_PHRASE +
				"? okay: " + PLACEHOLDER_FOR_REQUESTED_PHRASE + "."
				, PLACEHOLDER_FOR_REQUESTED_PHRASE + ". happy?"};
		String[] CHAT_2_LEGAL_RESPONSES_ARR = new String[]{"You want me to say " +
				PLACEHOLDER_FOR_REQUESTED_PHRASE +
				" do you? alright: " + PLACEHOLDER_FOR_REQUESTED_PHRASE + ".",
				PLACEHOLDER_FOR_REQUESTED_PHRASE + ". anything else your majesty?"};

		ChatterBot bot1 = new ChatterBot("Frank", CHAT_1_LEGAL_RESPONSES_ARR, CHAT_1_ILLEGAL_RESPONSES_ARR);
		ChatterBot bot2 = new ChatterBot("Claire", CHAT_2_LEGAL_RESPONSES_ARR, CHAT_2_ILLEGAL_RESPONSES_ARR);

		ChatterBot[] bots = {bot1, bot2};

		String statement = "sup?";
		System.out.print(bot2.getName() + ": " + statement);
		scanner.nextLine();

		while (true) {
			for (ChatterBot bot : bots) {
				String curBotName = bot.getName();
				statement = bot.replyTo(statement);
				System.out.print(curBotName + ": " + statement);
				scanner.nextLine();
			}
		}
	}
}
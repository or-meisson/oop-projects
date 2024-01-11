import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 *
 * @author Dan Nirel
 */
class ChatterBot {
	static final String REQUEST_PREFIX = "say ";
	String name;
	static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
	static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";


	Random rand = new Random();
	String[] repliesToIllegalRequest;
	String[] legalRequestsReplies;

	/**
	 * ChatterBot constructor.
	 *
	 * @param name                    The name of the ChatterBot.
	 * @param repliesToLegalRequest   Array of legal request replies.
	 * @param repliesToIllegalRequest Array of illegal request replies.
	 */
	ChatterBot(String name, String[] repliesToLegalRequest, String[] repliesToIllegalRequest) {
		this.name = name;
		this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
		this.legalRequestsReplies = new String[repliesToLegalRequest.length];
		for (int i = 0; i < repliesToIllegalRequest.length; i = i + 1) {
			this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
			this.legalRequestsReplies[i] = repliesToLegalRequest[i];
		}
	}

	/**
	 * Replies to the given statement, handling legal and illegal requests.
	 *
	 * @param statement The input statement.
	 * @return The ChatterBot's reply.
	 */
	String replyTo(String statement) {
		if (statement.startsWith(REQUEST_PREFIX)) {
			//we don’t repeat the request prefix, so delete it from the reply
			return replyToLegalRequest(statement);
		}
		return replyToIllegalRequest(statement);
	}

	/**
	 * Replies to a legal request, handling placeholders and randomness.
	 *
	 * @param phrase The requested phrase.
	 * @return The ChatterBot's reply to a legal request.
	 */
	String replyToLegalRequest(String phrase) {
		int randomIndex = rand.nextInt(legalRequestsReplies.length);
		String[] patternsAfterReplacement = replacePlaceholderInARandomPattern(this.legalRequestsReplies,
				PLACEHOLDER_FOR_REQUESTED_PHRASE, phrase);
		String responsePattern = patternsAfterReplacement[randomIndex];

		return responsePattern;
	}

	/**
	 * Replies to an illegal request, handling placeholders and randomness.
	 *
	 * @param request The illegal request.
	 * @return The ChatterBot's reply to an illegal request.
	 */
	String replyToIllegalRequest(String request) {
		int randomIndex = rand.nextInt(repliesToIllegalRequest.length);
		String[] patternsAfterReplacement = replacePlaceholderInARandomPattern(this.repliesToIllegalRequest,
				PLACEHOLDER_FOR_ILLEGAL_REQUEST, request);
		String responsePattern = patternsAfterReplacement[randomIndex];

		return responsePattern;
	}

	/**
	 * Replaces placeholders in a random pattern array.
	 *
	 * @param patterns    The array of patterns.
	 * @param placeholder The placeholder to replace.
	 * @param replacement The replacement string.
	 * @return The array of patterns after replacement.
	 */
	String[] replacePlaceholderInARandomPattern(String[] patterns, String placeholder, String replacement) {
		String[] newPatterns = new String[patterns.length];
		for (int i = 0; i < repliesToIllegalRequest.length; i = i + 1) {
			newPatterns[i] = patterns[i].replaceAll(placeholder, replacement);
		}
		return newPatterns;
	}

	/**
	 * Gets the name of the ChatterBot.
	 *
	 * @return The ChatterBot's name.
	 */
	String getName() {
		return this.name;
	}
}

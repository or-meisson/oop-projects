package ascii_art.exceptions;

/**
 * The IncorrectCommandException class is responsible for the exception to an incorrect command.
 */
public class IncorrectCommandException extends RuntimeException{

	/**
	 * Constructor for the IncorrectCommandException class.
	 * @param message The message of the exception.
	 */
	public IncorrectCommandException(String message) {
		super(message);
	}
}

package ascii_art.exceptions;


/**
 * The AddIncorrectFormatException class is responsible for the exception to
 * an incorrect format in the add command.
 */
public class AddIncorrectFormatException extends Exception {

	/**
	 * Constructor for the AddIncorrectFormatException class.
	 * @param message The message of the exception.
	 */
	public AddIncorrectFormatException(String message) {
		super(message);
	}

}

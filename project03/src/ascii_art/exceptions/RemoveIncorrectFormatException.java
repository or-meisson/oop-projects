package ascii_art.exceptions;

/**
 * The OutputIncorrectFormatException class is responsible for the exception to
 * an incorrect format in the output command.
 */
public class RemoveIncorrectFormatException extends Exception{

	/**
	 * Constructor for the RemoveIncorrectFormatException class.
	 * @param message The message of the exception.
	 */
	public RemoveIncorrectFormatException(String message) {
		super(message);
	}
}

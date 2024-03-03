package ascii_art.exceptions;

/**
 * The OutputIncorrectFormatException class is responsible for the exception to
 * an incorrect format in the output command.
 */
public class OutputIncorrectFormatException extends Exception{

	/**
	 * Constructor for the OutputIncorrectFormatException class.
	 * @param message The message of the exception.
	 */
	public OutputIncorrectFormatException(String message) {
		super(message);
	}

}

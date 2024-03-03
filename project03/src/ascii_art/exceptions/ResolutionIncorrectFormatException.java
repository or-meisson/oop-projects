package ascii_art.exceptions;

/**
 * The ResolutionIncorrectFormatException class is responsible for the exception to an incorrect
 * format in the resolution command.
 */
public class ResolutionIncorrectFormatException extends Exception{

	/**
	 * Constructor for the ResolutionIncorrectFormatException class.
	 * @param message The message of the exception.
	 */
	public ResolutionIncorrectFormatException(String message) {
		super(message);
	}
}

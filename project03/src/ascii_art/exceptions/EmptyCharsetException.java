package ascii_art.exceptions;

/**
 * The EmptyCharsetException class is responsible for the exception to
 * an empty charset.
 */
public class EmptyCharsetException extends Exception {

	/**
	 * Constructor for the EmptyCharsetException class.
	 * @param message The message of the exception.
	 */
	public EmptyCharsetException(String message) {
		super(message);
	}
}

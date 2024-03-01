package ascii_art.exceptions;

/**
 * The ResolutionExceedingBoundariesException class is responsible for the exception
 * to an exceeding resolution.
 */
public class ResolutionExceedingBoundariesException extends RuntimeException{

	/**
	 * Constructor for the ResolutionExceedingBoundariesException class.
	 * @param message The message of the exception.
	 */
	public ResolutionExceedingBoundariesException(String message) {
		super(message);
	}
}

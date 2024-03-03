package ascii_art;

import ascii_art.exceptions.*;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image.ImgPadder;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;


/**
 * The Shell class is responsible for the user interface of the program.
 */

public class Shell {
	private static final String INVALID_IMAGE_FILE = "Did not execute due to problem with " +
			"image file.";
	private static final String DEFAULT_IMAGE = "cat.jpeg";
	private static final String PROMPT = ">>> ";
	private static final String EXIT_INPUT = "exit";
	private static final String TO_SHOW_CHARS = "chars";
	private static final String TO_ADD_CHARS = "add";
	private static final String TO_REMOVE_CHARS = "remove";
	private static final String TO_CHANGE_RESOLUTION = "res";
	private static final String TO_CHANGE_IMAGE = "image";
	private static final String TO_CHANGE_OUTPUT = "output";
	private static final String TO_RUN_ASCII_ART = "asciiArt";
	private static final String INCORRECT_COMMAND = "Did not execute due to incorrect command.";
	private static final String TO_CHANGE_TO_CONSOLE = "console";
	private static final String TO_CHANGE_TO_HTML = "html";
	private static final String OUTPUT_FONT = "Courier New";
	private static final String OUTPUT_FILENAME = "out.html";
	private static final String INCORRECT_FORMAT_FOR_OUTPUT = "Did not change output method due" +
			" to incorrect format.";
	private static final String TO_INCREASE_RES = "up";
	private static final String TO_DECREASE_RES = "down";
	private static final String RESOLUTION_SET_TO_MSG = "Resolution set to ";
	private static final String EXCEEDING_BOUNDARIES = "Did not change resolution due to " +
			"exceeding boundaries.";
	private static final String INCORRECT_FORMAT_FOR_RES = "Did not change resolution due to" +
			" incorrect format.";
	private static final String TO_ADD_OR_REMOVE_ALL_CHARS = "all";
	private static final String INCORRECT_FORMAT_TO_REMOVE_CHARS_MSG = "Did not remove due to" +
			" incorrect format.";
	private static final char RANGE_CHARS_DASH = '-';
	private static final int ASCII_START = 32;
	private static final int ASCII_END = 126;
	private static final String INCORRECT_FORMAT_TO_ADD_CHARS_MSG = "Did not add due to" +
			" incorrect format.";
	private static final String REGEX = "\\s+";
	private static final String EMPTY_CHARSET_ERROR_MSG = "Did not execute. Charset is empty.";
	private static final int RESOLUTION_MULT_FACTOR = 2;
	private final Set<Character> sortedCharset = new TreeSet<>(Arrays.asList
			('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

	private int resolution = 128;

	private AsciiOutput output = new ConsoleAsciiOutput();
	private AsciiArtAlgorithm asciiArtAlgorithm;
	private SubImgCharMatcher subImgCharMatcher;
	private Image image = null;


	/**
	 * Constructor for the Shell class.
	 */
	public Shell() {
		try {
			image = new Image(DEFAULT_IMAGE);
		} catch (IOException e) {
			System.out.println(INVALID_IMAGE_FILE);
		}

	}


	/**
	 * Run the shell.
	 */
	public void run(){
		initializeAsciiAlgorithm(image);

		System.out.print(PROMPT);
		String input = KeyboardInput.readLine();
		while (!input.equals(EXIT_INPUT)) {
			String[] parts = input.split(REGEX);
			try {
				if (input.equals(TO_SHOW_CHARS)) {
					printCharset();
				} else if (parts[0].equals(TO_ADD_CHARS)) {
					handleAdds(parts);
				} else if (parts[0].equals(TO_REMOVE_CHARS)) {
					handleRemoves(parts);
				} else if (parts[0].equals(TO_CHANGE_RESOLUTION)) {
					handleResolution(parts);
				} else if (parts[0].equals(TO_CHANGE_IMAGE)) {
					image = handleImageChange(parts);
				} else if (parts[0].equals(TO_CHANGE_OUTPUT)) {
					handleOutputs(parts);
				} else if (parts[0].equals(TO_RUN_ASCII_ART)) {
					runAsciiArtAlgorithm();
				} else {
					throw new IncorrectCommandException(INCORRECT_COMMAND);
				}
			}
			catch (ResolutionIncorrectFormatException | ResolutionExceedingBoundariesException |
					IncorrectCommandException | OutputIncorrectFormatException |
					RemoveIncorrectFormatException | AddIncorrectFormatException |
				   EmptyCharsetException e) {
				System.out.println(e.getMessage());
			}
			System.out.print(PROMPT);
			input = KeyboardInput.readLine();
		}
	}

	/**
	 * Initialize the ascii algorithm.
	 * @param image The image to convert to ascii.
	 */
	private void initializeAsciiAlgorithm(Image image) {
		char[] charArray = new char[sortedCharset.size()];
		int index = 0;
		for (char c : sortedCharset) {
			charArray[index++] = c;
		}
		subImgCharMatcher = new SubImgCharMatcher(charArray);
		asciiArtAlgorithm = new AsciiArtAlgorithm(image, resolution, subImgCharMatcher);



	}


	/**
	 * Handle the change of the image.
	 * @param parts The parts of the input.
	 * @return The new image.
	 */
	private Image handleImageChange(String[] parts) {
		try{
			this.image = new Image(parts[1]);
//			this.image = ImgPadder.padImage(this.image);
			updateAlgorithm();
	}
		catch (IOException e){
			System.out.println(INVALID_IMAGE_FILE);
		}
		return this.image;
	}

	/**
	 * Update the ascii algorithm.
	 */
	private void updateAlgorithm(){
		asciiArtAlgorithm = new AsciiArtAlgorithm(image, resolution, subImgCharMatcher);
	}

	/**
	 * Run the ascii art algorithm.
	 */
	private void runAsciiArtAlgorithm() throws EmptyCharsetException{
		if (sortedCharset.isEmpty()){
			throw new EmptyCharsetException(EMPTY_CHARSET_ERROR_MSG);
		}
		char[][] asciiPixelArray = asciiArtAlgorithm.run();
		output.out(asciiPixelArray);
	}

	/**
	 * Handle the change of the output.
	 * @param parts The parts of the input.
	 * @throws OutputIncorrectFormatException If the format of the input is incorrect.
	 */
	private void handleOutputs (String[] parts) throws OutputIncorrectFormatException {
		if (parts.length == RESOLUTION_MULT_FACTOR) {
			if (parts[1].equals(TO_CHANGE_TO_CONSOLE)) {
				output = new ConsoleAsciiOutput();
			} else if (parts[1].equals(TO_CHANGE_TO_HTML)) {
				output = new HtmlAsciiOutput(OUTPUT_FILENAME, OUTPUT_FONT);
			} else {
				throw new OutputIncorrectFormatException(INCORRECT_FORMAT_FOR_OUTPUT);
			}
		} else {
			throw new OutputIncorrectFormatException(INCORRECT_FORMAT_FOR_OUTPUT);		}
	}

	/**
	 * Handle the change of the resolution.
	 * @param parts The parts of the input.
	 * @throws ResolutionIncorrectFormatException If the format of the input is incorrect.
	 * @throws ResolutionExceedingBoundariesException If the resolution exceeds the boundaries.
	 */
	private void handleResolution(String[] parts) throws ResolutionIncorrectFormatException,
			ResolutionExceedingBoundariesException{
		if (parts.length == RESOLUTION_MULT_FACTOR) {
			if (parts[1].equals(TO_INCREASE_RES)) {
				if (resolution* RESOLUTION_MULT_FACTOR <= ImgPadder.getDimAfterPadding
						(image.getWidth())){
					resolution *= RESOLUTION_MULT_FACTOR;
					updateAlgorithm();
					System.out.println(RESOLUTION_SET_TO_MSG + resolution + ".");
				}
				else{
					throw new ResolutionExceedingBoundariesException(EXCEEDING_BOUNDARIES);
				}
			} else if (parts[1].equals(TO_DECREASE_RES)) {
				if (resolution/ RESOLUTION_MULT_FACTOR >= Math.max(1,
						ImgPadder.getDimAfterPadding(this.image.getWidth()) /
								ImgPadder.getDimAfterPadding(this.image.getHeight()))){
					resolution /= RESOLUTION_MULT_FACTOR;
					updateAlgorithm();
					System.out.println(RESOLUTION_SET_TO_MSG + resolution+ ".");
				}
				else{
					throw new ResolutionExceedingBoundariesException(EXCEEDING_BOUNDARIES);
				}
			} else {
				throw new ResolutionIncorrectFormatException(INCORRECT_FORMAT_FOR_RES);
			}
		}
		else {
			throw new ResolutionIncorrectFormatException(INCORRECT_FORMAT_FOR_RES);
		}
	}

	/**
	 * Handle the removal of characters from the charset.
	 * @param parts The parts of the input.
	 * @throws RemoveIncorrectFormatException If the format of the input is incorrect.
	 */
	private void handleRemoves(String[] parts) throws RemoveIncorrectFormatException {
		if (parts.length == RESOLUTION_MULT_FACTOR) {
			if (parts[1].length() == 1) {
				char charToRemove = parts[1].charAt(0);
				if (checkIfNotContainsChar(charToRemove)) return;
				sortedCharset.remove(charToRemove);
				subImgCharMatcher.removeChar(charToRemove);
			} else if (parts[1].equals(TO_ADD_OR_REMOVE_ALL_CHARS)) {
				sortedCharset.clear();
				subImgCharMatcher.clear();
			} else if (parts[1].length() == 3 && parts[1].charAt(1) == RANGE_CHARS_DASH) {
				char start = parts[1].charAt(0);
				char end = parts[1].charAt(RESOLUTION_MULT_FACTOR);
				if(start <= end){
					for (char c = start; c <= end; c++) {
						if (checkIfNotContainsChar(c)) return;
						sortedCharset.remove(c);
						subImgCharMatcher.removeChar(c);
					}
				}
				else{
					for (char c = end; c <= start; c++) {
						if (checkIfNotContainsChar(c)) return;
						sortedCharset.remove(c);
						subImgCharMatcher.removeChar(c);
					}
				}
			} else {
				throw new RemoveIncorrectFormatException(INCORRECT_FORMAT_TO_REMOVE_CHARS_MSG);
			}
		}
		else {
			throw new RemoveIncorrectFormatException(INCORRECT_FORMAT_TO_REMOVE_CHARS_MSG);
		}
	}

	/**
	 * Check if the charset contains a character.
	 * @param charToCheck The character to remove.
	 * @return True if the charset contains the character, false otherwise.
	 */
	private boolean checkIfNotContainsChar(char charToCheck) {
		return !sortedCharset.contains(charToCheck);
	}

	/**
	 * Handle the addition of characters to the charset.
	 * @param parts The parts of the input.
	 * @throws AddIncorrectFormatException If the format of the input is incorrect.
	 */
	private void handleAdds(String[] parts) throws AddIncorrectFormatException {
		if (parts.length == RESOLUTION_MULT_FACTOR) {
			if (parts[1].length() == 1) {
				char newChar = parts[1].charAt(0);
				sortedCharset.add(newChar);
				subImgCharMatcher.addChar(newChar);
			} else if (parts[1].equals(TO_ADD_OR_REMOVE_ALL_CHARS)) {
				sortedCharset.clear();
				for (char c = ASCII_START; c <= ASCII_END; ++c) {
					sortedCharset.add(c);
					subImgCharMatcher.addChar(c);
				}
			} else if (parts[1].length() == 3 && parts[1].charAt(1) == RANGE_CHARS_DASH) {
				char start = parts[1].charAt(0);
				char end = parts[1].charAt(RESOLUTION_MULT_FACTOR);
				if(start <= end){
				for (char c = start; c <= end; c++) {
					sortedCharset.add(c);
					subImgCharMatcher.addChar(c);
				}
				}
				else{
					for (char c = end; c <= start; c++) {
						sortedCharset.add(c);
						subImgCharMatcher.addChar(c);
					}
				}
			} else {
				throw new AddIncorrectFormatException(INCORRECT_FORMAT_TO_ADD_CHARS_MSG);
			}
		}
		else {
			throw new AddIncorrectFormatException(INCORRECT_FORMAT_TO_ADD_CHARS_MSG);
		}
	}

	/**
	 * Print the charset.
	 */
	private void printCharset() {
		for (char c : sortedCharset) {
			System.out.print(c + " ");
		}
		System.out.println();
	}


	/**
	 * The main method.
	 * @param args The arguments.
	 */
	public static void main(String[] args){
		Shell shell = new Shell();
		shell.run();
	}
}

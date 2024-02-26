package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.max;

/**
 * The Shell class is responsible for the user interface of the program.
 */
public class Shell {
	private static final String INVALID_IMAGE_FILE = "Did not execute due to problem with image file.";
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
	private static final String INCORRECT_FORMAT_FOR_OUTPUT = "Did not change output method due to incorrect format.";
	private static final String TO_INCREASE_RES = "up";
	private static final String TO_DECREASE_RES = "down";
	private static final String RESOLUTION_SET_TO_MSG = "Resolution set to ";
	private static final String EXCEEDING_BOUNDARIES = "Did not change resolution due to exceeding boundaries.";
	private static final String INCORRECT_FORMAT_FOR_RES = "Did not change resolution due to incorrect format.";
	private static final String TO_ADD_ALL_CHARS = "all";
	private static final String INCORRECT_FORMAT_TO_REMOVE_CHARS_MSG = "Did not remove due to incorrect format.";
	private static final char RANGE_CHARS_DASH = '-';
	private static final int ASCII_START = 32;
	private static final int ASCII_END = 126;
	private static final String INCORRECT_FORMAT_TO_ADD_CHARS_MSG = "Did not add due to incorrect format.";
	private static final String REGEX = "\\s+";
	private final Set<Character> sortedCharset = new TreeSet<>(Arrays.asList
			('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
	private int resolution = 128;
	private int imgHeight = 128;
	private int maxCharsInRow = 128;
	private int minCharsInRow = 1;
	private AsciiOutput output;


	/**
	 * Constructor for the Shell class.
	 */
	public Shell() {
	}


	/**
	 * Run the shell.
	 */
	public void run(){
		Image image = null;
		try {
			image = new Image(DEFAULT_IMAGE);
		} catch (IOException e) {
			System.out.println(INVALID_IMAGE_FILE);
		}
		System.out.print(PROMPT);
		String input = KeyboardInput.readLine();
		while (!input.equals(EXIT_INPUT)) {
			String[] parts = input.split(REGEX);

			if (input.equals(TO_SHOW_CHARS)) {
				printCharset();
			}
			else if (parts[0].equals(TO_ADD_CHARS)){
				handleAdds(parts);
			} else if (parts[0].equals(TO_REMOVE_CHARS)) {
				handleRemoves(parts);
			}
			else if (parts[0].equals(TO_CHANGE_RESOLUTION)){
				handleResolution(parts);
			}
			else if (parts[0].equals(TO_CHANGE_IMAGE)){
				image = handleImageChange(image, parts);
			}
			else if (parts[0].equals(TO_CHANGE_OUTPUT)) {
				handleOutputs(parts);
			}
			else if(parts[0].equals(TO_RUN_ASCII_ART)){
				runAsciiArtAlgorithm(image);
			}
			else{
				System.out.println(INCORRECT_COMMAND);
			}

			System.out.print(PROMPT);
			input = KeyboardInput.readLine();
		}
	}


	/**
	 * Handle the change of the image.
	 * @param image The current image.
	 * @param parts The parts of the input.
	 * @return The new image.
	 */
	private Image handleImageChange(Image image, String[] parts) {
		try{
			image = new Image(parts[1]);
			maxCharsInRow = image.getWidth();
			minCharsInRow = max(1, image.getWidth()/ image.getHeight());
	}
		catch (IOException e){
			System.out.println(INVALID_IMAGE_FILE);
		}
		return image;
	}


	/**
	 * Run the ascii art algorithm.
	 * @param image The image to convert to ascii.
	 */
	private void runAsciiArtAlgorithm(Image image) {
		char[] charArray = new char[sortedCharset.size()];
		int index = 0;
		for (char c : sortedCharset) {
			charArray[index++] = c;
		}
		AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(image, charArray, resolution);
		char[][] asciiPixelArray = asciiArtAlgorithm.run();
		output.out(asciiPixelArray);
	}

	/**
	 * Handle the change of the output.
	 * @param parts  The parts of the input.
	 */
	private void handleOutputs(String[] parts) {
		if (parts.length == 2) {
			if (parts[1].equals(TO_CHANGE_TO_CONSOLE)) {
				output = new ConsoleAsciiOutput();
			} else if (parts[1].equals(TO_CHANGE_TO_HTML)) {
				output = new HtmlAsciiOutput(OUTPUT_FILENAME, OUTPUT_FONT);

			} else {
				System.out.println(INCORRECT_FORMAT_FOR_OUTPUT);
			}
		} else {
			System.out.println(INCORRECT_FORMAT_FOR_OUTPUT);
		}
	}

	/**
	 * Handle the change of the resolution.
	 * @param parts The parts of the input.
	 */
	private void handleResolution(String[] parts) {
		if (parts.length == 2) {
			if (parts[1].equals(TO_INCREASE_RES)) {
				if (resolution*2 <= maxCharsInRow){
					resolution *= 2;
					maxCharsInRow = resolution;
					minCharsInRow = max(1, resolution/imgHeight);
					System.out.println(RESOLUTION_SET_TO_MSG + resolution);
				}
				else{
					System.out.println(EXCEEDING_BOUNDARIES);
				}
			} else if (parts[1].equals(TO_DECREASE_RES)) {
				if (resolution/2 >= minCharsInRow){
					resolution /= 2;
					maxCharsInRow = resolution;
					minCharsInRow = max(1, resolution/imgHeight);
					System.out.println(RESOLUTION_SET_TO_MSG + resolution);
				}
				else{
					System.out.println(EXCEEDING_BOUNDARIES);
				}

			} else {
				System.out.println(INCORRECT_FORMAT_FOR_RES);

			}
		}
		else {
			System.out.println(INCORRECT_FORMAT_FOR_RES);
		}
	}

	/**
	 * Handle the removal of characters from the charset.
	 * @param parts The parts of the input.
	 */
	private void handleRemoves(String[] parts) {
		if (parts.length == 2) {
			if (parts[1].length() == 1) {
				char newChar = parts[1].charAt(0);
				sortedCharset.remove(newChar);
			} else if (parts[1].equals(TO_ADD_ALL_CHARS)) {
				sortedCharset.clear();
			} else if (parts[1].length() == 3 && parts[1].charAt(1) == RANGE_CHARS_DASH) { //todo what happens
				// if a part of the charset is already in the set
				char start = parts[1].charAt(0);
				char end = parts[1].charAt(2);
				if(start <= end){
					for (char c = start; c <= end; c++) {
						sortedCharset.remove(c);
					}
				}
				else{
					for (char c = end; c <= start; c++) {
						sortedCharset.remove(c);
					}
				}

			} else {
				System.out.println(INCORRECT_FORMAT_TO_REMOVE_CHARS_MSG);
			}
		}
		else {
			System.out.println(INCORRECT_FORMAT_TO_REMOVE_CHARS_MSG);
		}
	}

	/**
	 * Handle the addition of characters to the charset.
	 * @param parts The parts of the input.
	 */
	private void handleAdds(String[] parts) {
		if (parts.length == 2 ) {
			if (parts[1].length() == 1) {
				char newChar = parts[1].charAt(0);
				sortedCharset.add(newChar);
			} else if (parts[1].equals(TO_ADD_ALL_CHARS)) {
				sortedCharset.clear();
				for (char c = ASCII_START; c <= ASCII_END; ++c) {
					sortedCharset.add(c);
				}
			} else if (parts[1].length() == 3 && parts[1].charAt(1) == RANGE_CHARS_DASH) { //todo what happens
				// if a part of the charset is already in the set
				char start = parts[1].charAt(0);
				char end = parts[1].charAt(2);
				if(start <= end){
				for (char c = start; c <= end; c++) {
					sortedCharset.add(c);
				}
				}
				else{
					for (char c = end; c <= start; c++) {
						sortedCharset.add(c);
					}
				}
			} else {
				System.out.println(INCORRECT_FORMAT_TO_ADD_CHARS_MSG);
			}
		}
		else {
			System.out.println(INCORRECT_FORMAT_TO_ADD_CHARS_MSG);
		}
	}

	/**
	 * Print the charset.
	 */
	private void printCharset() {
		for (char c : sortedCharset) {
			System.out.print(c + " ");
		}
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

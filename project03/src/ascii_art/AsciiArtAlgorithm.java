package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image.ImgBrightnessCalculator;
import image.ImgPadder;
import image.ImgSplitter;
import image_char_matching.SubImgCharMatcher;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The AsciiArtAlgorithm class is responsible for converting an image to ascii art.
 */
public class AsciiArtAlgorithm {
	private Image image;
	private char[] charset;
	private int numberOfImgInRow;



	/**
	 * Constructor for the AsciiArtAlgorithm class.
	 * @param image The image to convert to ascii.
	 * @param charset The charset to use for the conversion.
	 * @param numberOfImgInRow The number of images in a row.
	 */
	public AsciiArtAlgorithm(Image image, char[] charset, int numberOfImgInRow) {

		this.image = image;
		this.charset = charset;
		this.numberOfImgInRow = numberOfImgInRow;

	}

	/**
	 * Run the algorithm.
	 * @return The ascii representation of the image.
	 */
	public char[][] run() {

		//pre level
		SubImgCharMatcher subImgCharMatcher = new SubImgCharMatcher(charset);

		//level 1 - padding the image
		Image paddedImage = ImgPadder.padImage(image);

		//level 2 - split the image
		ArrayList<Image> images = ImgSplitter.splitImage(paddedImage, numberOfImgInRow);


		//level 3 - converting each sub-image to ascii
		char[][] asciiPixelArray = new char[numberOfImgInRow][numberOfImgInRow];



		for (int i = 0; i < images.size(); i++) {
			double currImgBrightness = ImgBrightnessCalculator.calculateImgBrightness(images.get(i));
			char asciiCharToFill = subImgCharMatcher.getCharByImageBrightness(currImgBrightness);
			asciiPixelArray[i/numberOfImgInRow][i%numberOfImgInRow] = asciiCharToFill;

		}
		return asciiPixelArray;


	}



}

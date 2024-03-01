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
	private final Image image;
	private final int numberOfImgInRow;
	private final SubImgCharMatcher subImgCharMatcher;

	private double[][] subImagesCache = null;


	/**
	 * Constructor for the AsciiArtAlgorithm class.
	 * @param image The image to convert to ascii.
	 * @param numberOfImgInRow The number of images in a row.
	 * @param subImgCharMatcher The sub-image character matcher.
	 */
	public AsciiArtAlgorithm(Image image, int numberOfImgInRow, SubImgCharMatcher subImgCharMatcher) {
		this.image = image;
		this.numberOfImgInRow = numberOfImgInRow;
		this.subImgCharMatcher = subImgCharMatcher;

	}

	/**
	 * Run the algorithm.
	 * @return The ascii representation of the image.
	 */
	public char[][] run() {

		//pre level


		//level 1 - padding the image
		Image paddedImage = ImgPadder.padImage(image);

		//level 2 - split the image
		Image[][] images = ImgSplitter.splitImage(paddedImage, numberOfImgInRow);





		//level 3 - converting each sub-image to ascii
		int numOfCols = images[0].length;
		int numOfRows =  images.length;
		char[][] asciiPixelArray = new char[numOfRows][numOfCols];


		boolean isCacheEmpty = this.subImagesCache == null;

		if (isCacheEmpty) {
			subImagesCache = new double[numOfRows][numOfCols];
		}


		for (int i = 0; i < numOfRows; i++) {
			for(int j = 0; j < numOfCols; j++) {
				if (isCacheEmpty) {
					double currImgBrightness =
							ImgBrightnessCalculator.calculateImgBrightness(images[i][j]);
					subImagesCache[i][j] = currImgBrightness;
				}
				char asciiCharToFill =
						subImgCharMatcher.getCharByImageBrightness(subImagesCache[i][j]);
				asciiPixelArray[i][j] = asciiCharToFill;
			}
		}

	return asciiPixelArray;


	}



}

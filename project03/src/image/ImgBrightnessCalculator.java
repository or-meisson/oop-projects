package image;


/**
 * A class for calculating the brightness of an image.
 */
public class ImgBrightnessCalculator {
	private static final double RED_MULT_FACTOR = 0.2126;
	private static final double GREEN_MULT_FACTOR = 0.7152;
	private static final double BLUE_MULT_FACTOR = 0.0722;
	private static final int MAX_RGB_VALUE = 255;

	/**
	 * Constructor for the ImgBrightnessCalculator class.
	 */
	public ImgBrightnessCalculator() {
	}

	/**
	 * Calculate the brightness of an image.
	 * @param img The image to calculate the brightness of.
	 * @return The brightness of the image.
	 */
	public static double calculateImgBrightness(Image img) {
		double sumOfGreyPixels = 0;
		int width = img.getWidth();
		int height = img.getHeight();

		for (int i = 0; i <height; i++) {
			for (int j = 0; j < width; j++) {
				double red = img.getPixel(i, j).getRed();
				double green = img.getPixel(i, j).getGreen();
				double blue = img.getPixel(i, j).getBlue();
				double greyPixel = RED_MULT_FACTOR * red + GREEN_MULT_FACTOR * green
						+ BLUE_MULT_FACTOR * blue;
				sumOfGreyPixels += greyPixel;
			}
		}
		return sumOfGreyPixels/(width*height* MAX_RGB_VALUE);
	}
}

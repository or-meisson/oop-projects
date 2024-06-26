package image;

import java.awt.*;

/**
 * The ImgPadder class is responsible for padding an image to a power of 2.
 */
public class ImgPadder {


	private static final Color WHITE_COLOR = new Color(255, 255, 255);
	private static final int POWER_TO_REACH = 2;
	private static final int PADDING_DIVIDER_FACTOR = 2;

	/**
	 * Constructor for the ImgPadder class.
	 */
	public ImgPadder() {

	}

	/**
	 * Pad an image to a power of 2.
	 * @param image The image to pad.
	 * @return The padded image.
	 */
	public static Image padImage(Image image){

		int width = image.getWidth();
		int height = image.getHeight();
		int newWidth = (int) Math.pow(POWER_TO_REACH, Math.ceil(Math.log(width)/Math.log
				(POWER_TO_REACH)));
		int newHeight = (int) Math.pow(POWER_TO_REACH, Math.ceil(Math.log(height)/Math.log
				(POWER_TO_REACH)));
		int leftPadding = (newWidth - width) / PADDING_DIVIDER_FACTOR;
		int topPadding = (newHeight - height) / PADDING_DIVIDER_FACTOR;
		Color[][] newPixelArray = new Color[newHeight][newWidth];
		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				if (i>=topPadding && i< topPadding + height && j>=leftPadding &&
						j<leftPadding + width){
					newPixelArray[i][j] = image.getPixel(i - topPadding, j - leftPadding);
				} else {
					newPixelArray[i][j] = WHITE_COLOR;
				}
			}
		}
		return new Image(newPixelArray, newWidth, newHeight);
	}

	/**
	 * Get the dimension after padding.
	 * @param dim The dimension.
	 * @return The dimension after padding.
	 */
	public static int getDimAfterPadding(int dim){
		return (int) Math.pow(POWER_TO_REACH, Math.ceil(Math.log(dim)/Math.log
				(POWER_TO_REACH)));
	}
}

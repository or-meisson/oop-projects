package image;

import java.awt.*;
import java.util.ArrayList;

/**
 * The ImgSplitter class is responsible for splitting an image to sub-images.
 */
public class ImgSplitter {

	/**
	 * Constructor for the ImgSplitter class.
	 */
	public ImgSplitter() {
	}


	/**
	 * Split an image to sub-images.
	 * @param image The image to split.
	 * @param numberOfImgInRow The number of images in a row.
	 * @return The sub-images.
	 */
	public static ArrayList<Image> splitImage(Image image, int numberOfImgInRow){

		int newWidth = image.getWidth()/numberOfImgInRow;
		int newHeight = image.getHeight()/numberOfImgInRow;
		ArrayList<Image> images = new ArrayList<>();
		for (int i = 0; i < numberOfImgInRow; i++) {
			for (int j = 0; j < numberOfImgInRow; j++) {
				Color[][] newPixelArray = new Color[newHeight][newWidth];
				for (int k = 0; k < newHeight; k++) {
					for (int l = 0; l < newWidth; l++) {
						newPixelArray[k][l] = image.getPixel(i*newHeight+k, j*newWidth+l);
					}
				}
				images.add(new Image(newPixelArray, newWidth, newHeight));
			}
		}
		return images;
	}
}

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
	public static Image[][] splitImage(Image image, int numberOfImgInRow){
		int newSubImageDim = image.getWidth()/numberOfImgInRow;
		int numOfCols = numberOfImgInRow;
		int numOfRows =  image.getHeight() / newSubImageDim;

		Image[][] subImages = new Image[numOfRows][numOfCols];

		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfCols; j++) {
				Color[][] newPixelArray = new Color[newSubImageDim][newSubImageDim];
				for (int k = 0; k < newSubImageDim; k++) {
					for (int l = 0; l < newSubImageDim; l++) {
						newPixelArray[k][l] = image.getPixel(i * newSubImageDim + k,
								j * newSubImageDim + l);
					}
				}
				subImages[i][j] = (new Image(newPixelArray, newSubImageDim, newSubImageDim));
			}
		}
		return subImages;
	}
}

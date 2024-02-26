package image;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
public class Image {

    private final String IMG_SUFFIX = ".jpeg";
    private final Color[][] pixelArray;
    private final int width;
    private final int height;

    /**
     * Constructor for the Image class.
     * @param filename The filename of the image.
     * @throws IOException If the file is not found.
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();


        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j]=new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Constructor for the Image class.
     * @param pixelArray The pixel array of the image.
     * @param width The width of the image.
     * @param height The height of the image.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Get the width of the image.
     * @return The width of the image.
     */
    public int getWidth() {
        return width;
    }


    /**
     * Get the height of the image.
     * @return The height of the image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the pixel array of the image.
     * @return The pixel array of the image.
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }



    /**
     * Save the image to a file.
     * @param fileName The name of the file to save the image to.
     */
    public void saveImage(String fileName){
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName+ IMG_SUFFIX);
        try {
            ImageIO.write(bufferedImage, IMG_SUFFIX, outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }











}

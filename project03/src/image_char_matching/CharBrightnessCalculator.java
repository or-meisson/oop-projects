package image_char_matching;


/**
 * The CharBrightnessCalculator class is responsible for calculating the brightness of a character.
 */
public class CharBrightnessCalculator {


	/**
	 * Constructor for the CharBrightnessCalculator class.
	 */
	public CharBrightnessCalculator() {
	}



	/**
	 * Calculate the current brightness of a character.
	 * @param c The character to calculate the brightness of.
	 * @return The brightness of the character.
	 */
	public static double calculateCurrentBrightness(char c) {
		boolean[][] boolArray = CharConverter.convertToBoolArray(c);
		double sum = 0;
		for (boolean[] booleans : boolArray) {
			for (boolean aBoolean : booleans) {
				if (aBoolean) {
					sum++;
				}
			}
		}
		return ( sum / (boolArray.length * boolArray[0].length));
	}


}

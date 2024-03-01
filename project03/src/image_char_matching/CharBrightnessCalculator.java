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
	 * Calculate the brightness of a character.
	 * @param c The character to calculate the brightness of.
	 * @param minBrightness The minimum brightness.
	 * @param maxBrightness The maximum brightness.
	 * @return The brightness of the character.
	 */
	public static double calculateBrightness(char c, double minBrightness, double maxBrightness) {
		// Calculate the brightness of the character
		double currentBrightness = calculateCurrentBrightness(c);
		return (currentBrightness - minBrightness) / (maxBrightness - minBrightness);
	}

	/**
	 * Calculate the current brightness of a character.
	 * @param c The character to calculate the brightness of.
	 * @return The brightness of the character.
	 */
	static double calculateCurrentBrightness(char c) {
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

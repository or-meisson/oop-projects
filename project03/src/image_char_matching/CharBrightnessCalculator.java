package image_char_matching;

public class CharBrightnessCalculator {
	private  double minBrightness = 1.0;
	private double maxBrightness = 0.0;

	/**
	 * Constructor for the CharBrightnessCalculator class.
	 */
	public CharBrightnessCalculator() {
	}

	/**
	 * Calculate the brightness of a character.
	 * @param c The character to calculate the brightness of.
	 * @return The brightness of the character.
	 */
	public  double calculateBrightness(char c) {
		// Calculate the brightness of the character
		boolean[][] boolArray = CharConverter.convertToBoolArray(c);
		double sum = 0;
		for (boolean[] booleans : boolArray) {
			for (boolean aBoolean : booleans) {
				if (aBoolean) {
					sum++;
				}
			}
		}
		double currentBrightness = sum / (boolArray.length * boolArray[0].length);
		return (currentBrightness - minBrightness) / (maxBrightness - minBrightness);
	}


	public void initializeMinMaxBrightness(char[] charset) {
		for (char c : charset) {
			boolean[][] boolArray = CharConverter.convertToBoolArray(c);
			double sum = 0;
			for (boolean[] booleans : boolArray) {
				for (boolean aBoolean : booleans) {
					if (aBoolean) {
						sum++;
					}
				}
			}
			double currentBrightness = sum / (boolArray.length * boolArray[0].length);
			if (currentBrightness < minBrightness) {
				minBrightness = currentBrightness;
			}
			if (currentBrightness > maxBrightness) {
				maxBrightness = currentBrightness;
			}
		}
	}
}

package image_char_matching;

import java.util.HashMap;
import java.util.Map;

public class SubImgCharMatcher {

	private Map<Character, Double> charBrightnessHashMap;
	private CharBrightnessCalculator charBrightnessCalculator = new CharBrightnessCalculator();


	/**
	 * Constructor for the SubImgCharMatcher class.
	 * @param charset The charset to use for the matching.
	 */
	public SubImgCharMatcher(char[] charset){
		this.charBrightnessHashMap = new HashMap<>();
		this.charBrightnessCalculator.initializeMinMaxBrightness(charset);
		for (char c : charset) {
			this.addChar(c);
		}

	}

	/**
	 * Get the character that is closest to the given brightness.
	 * @param brightness The brightness to find the closest character to.
	 * @return The character that is closest to the given brightness.
	 */
	public char getCharByImageBrightness(double brightness) {
		char closestChar =  '\0'; // Initialize with a default value
		double minDifference = Double.MAX_VALUE; // Initialize with a very large value
		char minAscii = (char) Integer.MAX_VALUE; // Initialize with a very large value
		for (Map.Entry<Character, Double> entry : charBrightnessHashMap.entrySet()) {
			double currentDifference = Math.abs(entry.getValue() - brightness);
			char currentAscii = entry.getKey();
			// If brightness difference is equal, compare ASCII values
			if (currentDifference == minDifference) {
				if (currentAscii < minAscii) {
					minAscii = currentAscii;
					closestChar = entry.getKey();
				}
			} else if (currentDifference < minDifference) {
				minDifference = currentDifference;
				minAscii = currentAscii;
				closestChar = entry.getKey();
			}
		}
		return closestChar;
	}


	public void addChar (char c ){
		double brightness = charBrightnessCalculator.calculateBrightness(c);
//		System.out.println(brightness);
		charBrightnessHashMap.put(c, brightness);

	}

	public void removeChar (char c){
		charBrightnessHashMap.remove(c);
	}




}

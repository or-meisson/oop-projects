package image_char_matching;

import java.util.HashMap;
import java.util.Map;

/**
 * The SubImgCharMatcher class is responsible for matching sub-images to characters.
 */
public class SubImgCharMatcher {

	private Map<Character, Double> charBrightnessHashMap;

	private  double minBrightness = 1.0;
	private double maxBrightness = 0.0;


	/**
	 * Constructor for the SubImgCharMatcher class.
	 * @param charset The charset to use for the matching.
	 */
	public SubImgCharMatcher(char[] charset){
		this.charBrightnessHashMap = new HashMap<>();
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


	/**
	 * Calculate the minimum and maximum brightness of the characters.
	 */
	private void calculateMinMaxBrightness() {
		for (char c : charBrightnessHashMap.keySet()) {
			double currentBrightness = CharBrightnessCalculator.calculateCurrentBrightness(c);
			if (currentBrightness < minBrightness) {
				minBrightness = currentBrightness;
			}
			if (currentBrightness > maxBrightness) {
				maxBrightness = currentBrightness;
			}
		}
	}

	/**
	 * Recalculate the brightnesses of the characters.
	 */
	private void recalculateBrightnesses(){
		HashMap<Character, Double> updatedMap = new HashMap<>();
		for (char c : charBrightnessHashMap.keySet()) {
			double brightness = CharBrightnessCalculator.calculateBrightness(c, minBrightness,
					maxBrightness);
			updatedMap.put(c, brightness);
		}
		charBrightnessHashMap = updatedMap; // Replace the original map with the updated one
	}

	/**
	 * Add a character to the matching.
	 * @param c The character to add.
	 */

	public void addChar (char c ){
		double brightness = CharBrightnessCalculator.calculateBrightness(c, minBrightness,
				maxBrightness);
		if(brightness < minBrightness || brightness > maxBrightness){
			//calculate again the brightnesses
			charBrightnessHashMap.put(c, brightness);
			recalculateBrightnesses();
			calculateMinMaxBrightness();

		}
		else {
			charBrightnessHashMap.put(c, brightness);
		}
	}

	/**
	 * Remove a character from the matching.
	 * @param c The character to remove.
	 */
	public void removeChar (char c){
		if (charBrightnessHashMap.get(c) == minBrightness || charBrightnessHashMap.get(c) == maxBrightness){
			//calculate again the brightnesses
			charBrightnessHashMap.remove(c);
			recalculateBrightnesses();
			calculateMinMaxBrightness();
		}
		else{
		charBrightnessHashMap.remove(c);}
	}


	/**
	 * Clear the matching.
	 */
	public void clear(){
		charBrightnessHashMap.clear();
		minBrightness = 1.0;
		maxBrightness = 0.0;
	}




}

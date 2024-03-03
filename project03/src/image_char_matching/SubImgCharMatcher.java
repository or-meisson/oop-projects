package image_char_matching;

import java.util.HashMap;
import java.util.Map;

/**
 * The SubImgCharMatcher class is responsible for matching sub-images to characters.
 */
public class SubImgCharMatcher {

	private final Map<Character, Double> charBrightnessHashMap;

	private  double minBrightness = Double.MAX_VALUE;
	private double maxBrightness = Double.MIN_VALUE;


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
		for (Map.Entry<Character, Double> entry : charBrightnessHashMap.entrySet()) {
			double normalizedBrightness =
					(entry.getValue() - minBrightness) / (maxBrightness - minBrightness);
			double currentDifference = Math.abs(normalizedBrightness - brightness);
			char currentAscii = entry.getKey();
			// If brightness difference is equal, compare ASCII values

			if (currentDifference == minDifference) {
				if (currentAscii < closestChar) {
					closestChar = currentAscii;
				}
			} else if (currentDifference < minDifference) {
				minDifference = currentDifference;
				closestChar = currentAscii;
			}
		}
		return closestChar;
	}

	/**
	 * Calculate the minimum and maximum brightness of the characters.
	 */
	private void calculateMinMaxBrightness() {
		if(charBrightnessHashMap.isEmpty()){
			minBrightness = Double.MAX_VALUE;
			maxBrightness = Double.MIN_VALUE;
		}
		for (Map.Entry<Character, Double> entry : charBrightnessHashMap.entrySet()) {
			if (entry.getValue() < minBrightness) {
				minBrightness = entry.getValue();
			}
			if (entry.getValue() > maxBrightness) {
				maxBrightness = entry.getValue();
			}
		}
	}


	/**
	 * Add a character to the matching.
	 * @param c The character to add.
	 */

	public void addChar (char c ){
		double brightness = CharBrightnessCalculator.calculateCurrentBrightness(c);
		if(brightness < minBrightness){
			minBrightness = brightness;
					}
		else if (brightness > maxBrightness){
			maxBrightness = brightness;
		}
			charBrightnessHashMap.put(c, brightness);
	}

	/**
	 * Remove a character from the matching.
	 * @param c The character to remove.
	 */
	public void removeChar (char c){
		if (charBrightnessHashMap.get(c) == minBrightness || charBrightnessHashMap.get(c)
				== maxBrightness){
			charBrightnessHashMap.remove(c);
			calculateMinMaxBrightness();

		}
		else{
		charBrightnessHashMap.remove(c);}
	}


	/**
	 * Clear the hashmap.
	 */
	public void clear(){
		charBrightnessHashMap.clear();
		minBrightness = Double.MAX_VALUE;
		maxBrightness = Double.MIN_VALUE;

	}




}

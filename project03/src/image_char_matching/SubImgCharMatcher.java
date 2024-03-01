package image_char_matching;

import java.util.HashMap;
import java.util.Map;

/**
 * The SubImgCharMatcher class is responsible for matching sub-images to characters.
 */
public class SubImgCharMatcher {

	private Map<Character, Double> charBrightnessHashMap;

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
		char minAscii = (char) Integer.MAX_VALUE; // Initialize with a very large value //todo is
		// this the same
		for (Map.Entry<Character, Double> entry : charBrightnessHashMap.entrySet()) { //todo
			// normalize here?
//			double normalizedGottenBrightness = (brightness - minBrightness) / (maxBrightness - minBrightness);
			double normalizedBrightness =
					(entry.getValue() - minBrightness) / (maxBrightness - minBrightness);
			double currentDifference = Math.abs(normalizedBrightness - brightness);
			char currentAscii = entry.getKey();
			// If brightness difference is equal, compare ASCII values
			System.out.println("currentAscii: " + currentAscii + " normalizesBrightness " + normalizedBrightness + " normalizedGottenBrightness " + brightness   );

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
//todo chaevk in aquarium computer

	/**
	 * Calculate the minimum and maximum brightness of the characters.
	 */
	private void calculateMinMaxBrightness() {
		if(charBrightnessHashMap.isEmpty()){
			minBrightness = Double.MAX_VALUE;
			maxBrightness = Double.MIN_VALUE;
		}
		for (Map.Entry<Character, Double> entry : charBrightnessHashMap.entrySet()) {
//			double currentBrightness = CharBrightnessCalculator.calculateCurrentBrightness(c);

			if (entry.getValue() < minBrightness) {
				minBrightness = entry.getValue();
			}
			if (entry.getValue() > maxBrightness) {
				maxBrightness = entry.getValue();
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
		double brightness = CharBrightnessCalculator.calculateCurrentBrightness(c);
//		System.out.println(brightness);
		if(brightness < minBrightness){
			minBrightness = brightness;
					}
		else if (brightness > maxBrightness){
			maxBrightness = brightness;
		}
			//calculate again the brightnesses
			charBrightnessHashMap.put(c, brightness);
//			calculateMinMaxBrightness(); //todo more efficient - replace with if statement

//			recalculateBrightnesses();

//		}
//		else {
//			charBrightnessHashMap.put(c, brightness);
//		}
		System.out.println(charBrightnessHashMap);
	}

	/**
	 * Remove a character from the matching.
	 * @param c The character to remove.
	 */
	public void removeChar (char c){
		if (charBrightnessHashMap.get(c) == minBrightness || charBrightnessHashMap.get(c) == maxBrightness){
			//calculate again the brightnesses
			charBrightnessHashMap.remove(c);
			calculateMinMaxBrightness();
//			recalculateBrightnesses();

		}
		else{
		charBrightnessHashMap.remove(c);}
	}


	/**
	 * Clear the matching.
	 */
	public void clear(){
		charBrightnessHashMap.clear();
		minBrightness = Double.MAX_VALUE;
		maxBrightness = Double.MIN_VALUE;

	}




}

/**
 * The {@code PlayerFactory} class provides a factory method to create instances of various player types
 * for the tic-tac-toe game.
 * It allows dynamic creation of player objects based on the specified type.
 *
 * @author Or Meissonnier
 */
public class PlayerFactory {
	/**
	 * Constructs a new PlayerFactory instance.
	 */
	public PlayerFactory() {
	}

	/**
	 * Builds and returns a player object based on the specified type.
	 *
	 * @param type The type of player to be created ("whatever", "human", "clever", "genius").
	 * @return A player object corresponding to the specified type, or null if the type is invalid.
	 */
	public Player buildPlayer(String type) {
		Player player;
		switch (type) {
			case "whatever":
				player = new WhateverPlayer();
				break;
			case "human":
				player = new HumanPlayer();
				break;
			case "clever":
				player = new CleverPlayer();
				break;
			case "genius":
				player = new GeniusPlayer();
				break;
			default:
				player = null;
		}
		return player;
	}
}

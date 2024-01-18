public class PlayerFactory {

	public PlayerFactory() {

	}
	public Player buildPlayer(String type){
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

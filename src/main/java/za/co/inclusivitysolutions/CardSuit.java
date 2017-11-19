package za.co.inclusivitysolutions;

public enum CardSuit {
	DIAMONDS("d"), HEARTS("h"), SPADES("s"), CLUBS("c");

	private String suitString;

	CardSuit(String suitString) {
		this.suitString = suitString;
	}

	public String getSuitString() {
		return this.suitString;
	}

	public static CardSuit fromSuitString(String suitString) {
		for (CardSuit c : CardSuit.values()) {
			if (c.getSuitString().equalsIgnoreCase(suitString)) {
				return c;
			}
		}
		return null;
	}
}

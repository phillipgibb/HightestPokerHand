package za.co.inclusivitysolutions;

public enum CardRank {
	TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), JACK(
			"J"), QUEEN("Q"), KING("K"), ACE("A");

	private String rankString;

	CardRank(String rankString) {
		this.rankString = rankString;
	}

	public String getRankString() {
		return this.rankString;
	}

	public static CardRank fromRankString(String rankString) {
		for (CardRank c : CardRank.values()) {
			if (c.getRankString().equalsIgnoreCase(rankString)) {
				return c;
			}
		}
		return null;
	}
}

package za.co.inclusivitysolutions;

public class Card implements Comparable<Card> {

	private CardRank cardRank;
	private String rank;
	private String suit;

	public Card(String rank, String suit) {
		this.rank = rank;
		if(rank.equalsIgnoreCase("T")){
			this.rank = "10";
		}
		this.suit = suit;
		this.cardRank = CardRank.fromRankString(this.rank);
	}

	public String getRank() {
		return rank;
	}

	public String getSuit() {
		return suit;
	}

	public int getRankValue() {
		return cardRank.ordinal();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Card) {
			Card c = (Card) obj;
			if (c.getRank().equalsIgnoreCase(this.getRank()) && c.getSuit().equalsIgnoreCase(this.getSuit())) {
				return true;
			}
		}
		return false;

	}

	public int compareTo(Card otherCard) {
		if (this.getRankValue() > otherCard.getRankValue()) {
			return 1;
		} else if (this.getRankValue() < otherCard.getRankValue()) {
			return -1;
		}
		return 0;
	}

	public String toString() {
		return this.getRank() + this.getSuit();
	}
}

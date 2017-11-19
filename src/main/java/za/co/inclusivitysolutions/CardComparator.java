package za.co.inclusivitysolutions;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {
		public int compare(Card card1, Card card2) {
			return card1.compareTo(card2);
	}
}

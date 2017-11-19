package za.co.inclusivitysolutions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import reactor.core.publisher.Flux;

public class CardHelper {

	static String determineBestHand(List<Card> cardList) {

		Card[] cardArray = new Card[cardList.size()];
		cardArray = cardList.toArray(cardArray);
		// flush
		// straight
		// 2 pair
		if (isStraightFlush(cardArray)) {
			return "Straight Flush " + CardSuit.fromSuitString(cardArray[0].getSuit());
		} else if (isMultipleOfaKind(cardArray, 4)) {
			return "Four of a Kind";
		} else if (isFullHouse(cardArray)) {
			return "Full House";
		} else if (isFlush(cardArray)) {
			return "Flush";
		} else if (isStraight(cardArray)) {
			return "Straight";
		} else if (isMultipleOfaKind(cardArray, 3)) {
			return "Three of a Kind";
		} else if (isTwoPair(cardArray)) {
			return "Two Pair";
		} else if (isMultipleOfaKind(cardArray, 2)) {
			return "Two of a Kind";
		} else {
			return "High card " + CardRank.fromRankString(cardArray[cardArray.length - 1].getRank());
		}
	}

	private static boolean isTwoPair(Card[] cardArray) {
		int count = 1;
		boolean twoOfaKind = false;
		boolean secondTwoOfaKind = false;

		for (int i = 0; i < cardArray.length - 1; i++) {
			if (cardArray[i].getRankValue() == cardArray[i + 1].getRankValue()) {
				count++;
				if (i + 1 == cardArray.length - 1) {
					if (count == 2) {
						secondTwoOfaKind = true;
					} 
					if (twoOfaKind && secondTwoOfaKind) {
						return true;
					}
				}
			} else {
				if (count == 2 && !twoOfaKind) {
					twoOfaKind = true;
					count = 1;
				} else if (count == 2) {
					secondTwoOfaKind = true;
				}
				if (twoOfaKind && secondTwoOfaKind) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isStraight(Card[] cardArray) {
		boolean straight = false;
		for (int i = 0; i < cardArray.length - 1; i++) {
			if (cardArray[i].getRankValue() + 1 == cardArray[i + 1].getRankValue()) {
				straight = true;
			} else {
				return false;
			}
		}
		return straight;
	}

	private static boolean isFlush(Card[] cardArray) {
		boolean flush = false;
		for (int i = 0; i < cardArray.length - 1; i++) {
			if (cardArray[i].getSuit().equalsIgnoreCase(cardArray[i + 1].getSuit())) {
				flush = true;
			} else {
				return false;
			}
		}
		return flush;
	}

	private static boolean isFullHouse(Card[] cardArray) {
		int count = 1;
		boolean twoOfaKind = false;
		boolean threeOfaKind = false;

		for (int i = 0; i < cardArray.length - 1; i++) {
			if (cardArray[i].getRankValue() == cardArray[i + 1].getRankValue()) {
				count++;
				if (i + 1 == cardArray.length - 1) {
					if (count == 2) {
						twoOfaKind = true;
					} else if (count == 3) {
						threeOfaKind = true;
					}
					if (twoOfaKind && threeOfaKind) {
						return true;
					}
				}
			} else {
				if (count == 2) {
					twoOfaKind = true;
					count = 1;
				} else if (count == 3) {
					threeOfaKind = true;
					count = 1;
				}
				if (twoOfaKind && threeOfaKind) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isMultipleOfaKind(Card[] cardArray, int numberOfRank) {
		boolean multipleOfaKind = false;
		for (int i = 0; i < numberOfRank - 1; i++) {
			if (cardArray[i].getRankValue() == cardArray[i + 1].getRankValue()) {
				multipleOfaKind = true;
			} else {
				return false;
			}
		}
		return multipleOfaKind;
	}

	private static boolean isStraightFlush(Card[] cardArray) {
		boolean straightFlush = false;
		for (int i = 0; i < cardArray.length - 1; i++) {
			if (cardArray[i].getRankValue() + 1 == cardArray[i + 1].getRankValue()
					&& cardArray[i].getSuit().equals(cardArray[i + 1].getSuit())) {
				straightFlush = true;
			} else {
				return false;
			}
		}
		return straightFlush;
	}

	static boolean validateInput(String input) {
		String[] cardStringArray = input.split(",");

		for (int i = 0; i < cardStringArray.length; i++) {
			for (int j = i + 1; j < cardStringArray.length; j++) {
				if (cardStringArray[i].equalsIgnoreCase(cardStringArray[j])) {
					System.out.println("There is a duplicate(" + cardStringArray[j] + ") in your poker hand");
					return false;
				}
			}
		}

		if (cardStringArray.length != 5) {
			System.out.println("There are 5 Cards in a poker hand");
			return false;
		}
		String pokerRegex = "([2-9TJQKA][SHDC],?)*";
		Pattern p = Pattern.compile(pokerRegex, Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(input);
		boolean r = m.matches();
		if (r) {
			return true;
		} else {
			System.out.println("Only Valid Cards Allowed");
			return false;
		}
	}

	public static List<Card> parseHand(String cardString) {
		String[] cardStringArray = cardString.split(",");
		return Flux.fromArray(cardStringArray).map(card -> new Card(card.substring(0, 1), card.substring(1)))
				.collectSortedList(new CardComparator()).block();

	}

}

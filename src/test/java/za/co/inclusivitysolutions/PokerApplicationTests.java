package za.co.inclusivitysolutions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;

public class PokerApplicationTests {

	@Test
	public void testCardSorting() {
		List<Card> cards = CardHelper.parseHand("5C,QH,2D,3D,5S");
		List<Card> expectedOrder = Flux.just("2D", "3D", "5C", "5S", "QH")
				.map(card -> new Card(card.substring(0, 1), card.substring(1))).collectList().block();
		System.out.println(cards);
		assertTrue(cards.equals(expectedOrder));
	}

	@Test
	public void testIncorrectFormats() {
		assertFalse(CardHelper.validateInput("1C,QH,2D,3D,5S"));
		assertFalse(CardHelper.validateInput("5G,QH,2D,3D,5S"));
	}

	@Test
	public void testCorrectFormats() {
		assertTrue(CardHelper.validateInput("3C,QH,2D,3D,5S"));
		assertTrue(CardHelper.validateInput("TC,QH,2D,3D,5S"));
		assertTrue(CardHelper.validateInput("tC,QH,2D,3D,AS"));
		assertTrue(CardHelper.validateInput("tc,QH,2D,KD,5S"));
	}

	@Test
	public void testStraightFlush() {
		List<Card> cards = CardHelper.parseHand("3C,4C,5C,6C,7C");
		assertEquals("Should be a Straight Flush", "Straight Flush " + CardSuit.CLUBS, CardHelper.determineBestHand(cards));
	}
	
	@Test
	public void testDuplicate() {
		assertFalse(CardHelper.validateInput("1C,1C,2D,3D,5S"));
	}
	
	@Test
	public void testFourOfaKind() {
		List<Card> cards = CardHelper.parseHand("3C,3H,3S,3D,7C");
		assertEquals("Should be a Four Of a kind", "Four of a Kind", CardHelper.determineBestHand(cards));
	}
	
	@Test
	public void testThreeOfaKind() {
		List<Card> cards = CardHelper.parseHand("3C,3H,3S,6H,7C");
		assertEquals("Should be a Three Of a kind", "Three of a Kind", CardHelper.determineBestHand(cards));
	}
	
	@Test
	public void testTwoOfaKind() {
		List<Card> cards = CardHelper.parseHand("3C,3H,8S,6H,7C");
		assertEquals("Should be a Two Of a kind", "Two of a Kind", CardHelper.determineBestHand(cards));
	}
	
	@Test
	public void testHighCard() {
		List<Card> cards = CardHelper.parseHand("3C,AH,QS,6H,7C");
		assertEquals("Should be a High Card Queen", "High card "+CardRank.ACE, CardHelper.determineBestHand(cards));
	}
	
	@Test
	public void testFullHouse() {
		List<Card> cards = CardHelper.parseHand("3C,3H,3S,QH,QC");
		assertEquals("Should be a Full House", "Full House", CardHelper.determineBestHand(cards));
	}
	
	@Test
	public void testFlush() {
		List<Card> cards = CardHelper.parseHand("3C,kc,4c,5c,QC");
		assertEquals("Should be a Flush", "Flush", CardHelper.determineBestHand(cards));
	}
	
	@Test
	public void testStraight() {
		List<Card> cards = CardHelper.parseHand("3C,4h,5c,6h,7d");
		assertEquals("Should be a Straight", "Straight", CardHelper.determineBestHand(cards));
	}
	
	@Test
	public void testTwoPair() {
		List<Card> cards = CardHelper.parseHand("3C,3H,4S,4H,QC");
		assertEquals("Should be a Two Pair", "Two Pair", CardHelper.determineBestHand(cards));
	}
	
	@Test
	public void testWithaT() {
		List<Card> cards = CardHelper.parseHand("TC,3H,3S,QH,kC");
		assertEquals("Should be a Two of a Kind", "Two of a Kind", CardHelper.determineBestHand(cards));
	}

}

package za.co.inclusivitysolutions;

public class PokerApplication {

	public static void main(String[] args) {
		PokerConsole pokerConsole = new PokerConsole();
		Thread t = new Thread(pokerConsole);
		t.start();
	}

}

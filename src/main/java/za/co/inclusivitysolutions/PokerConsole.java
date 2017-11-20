package za.co.inclusivitysolutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PokerConsole implements Runnable {

	public PokerConsole() {
	}

	public void run() {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("This app determines the best poker hand you can obtain from the entered list");
			printHelp();
			System.out.print("Enter 5 poker cards (2-9, T = 10, Suits=  S,C,D,H) : ");
			String input;
			try {
				input = bufferedReader.readLine();

				input = input.toLowerCase();
				switch (input) {
				case "q":
				case "quit":
				case "exit":
					exit();
				case "h":
				case "help":
					printHelp();
					break;
				default:
					if(input.equalsIgnoreCase("r")){
						input = CardHelper.randomHand();
						System.out.println(input);
					}else{
						input = input.toUpperCase();
					}
					if (CardHelper.validateInput(input)) {
						String result = CardHelper.determineBestHand(CardHelper.parseHand(input));
						System.out.println("You have a " + result);
						tryAgain(bufferedReader);
					} else {
						System.out.println("Only Valid Cards may be inputted");
						printHelp();
						tryAgain(bufferedReader);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

	}

	private void exit() {
		System.out.println("Exiting. Thank you for playing");
		System.exit(0);
	}

	private void tryAgain(BufferedReader bufferedReader) throws IOException {
		System.out.print("Try Again[y|n]? ");
		String input = bufferedReader.readLine();
		input = input.toLowerCase();
		switch (input) {
		case "n":
			exit();
		case "y":
			break;
		default:
			printHelp();
			tryAgain(bufferedReader);
		}

	}

	private void printHelp() {
		System.out.println("Pick 5 comma spererated poker cards defined by Rank followed by Suit where: ");
		System.out.println("\tRank = 2,3,4,5,6,7,8,9,T,J,Q,K,A");
		System.out.println("\tSuit = H(Heart),S(Spade),C(Club),D(Diamond)");
		System.out.println("\nOther Options:");
		System.out.println("\tr = random list of cards");
		System.out.println("\th\\help = This Help Output");
		System.out.println("\tq\\quit\\exit = Quit");
		System.out.println();

	}

}

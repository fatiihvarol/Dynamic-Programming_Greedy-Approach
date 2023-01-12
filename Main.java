import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Heros {
	private String name;
	private String type;
	private int gold;
	private int attackpoint;
	private Heros heros;

	public Heros(String name, String type, int gold, int attackpoint) { // constructor
		this.name = name;
		this.type = type;
		this.gold = gold;
		this.attackpoint = attackpoint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getAttackpoint() {
		return attackpoint;
	}

	public void setAttackpoint(int attackpoint) {
		this.attackpoint = attackpoint;
	}

	public void display() {
		System.out.println("Name: " + this.name + " Type : " + this.type + "Gold : " + this.gold + "AttackPoint : "
				+ this.attackpoint);
	}

}

public class Fatih_Varol_2019510081 {
	static int index = 0;
	static int dynamic_index = 0;
	static Heros[] herosArray = new Heros[10000];
	static Heros[] herosArrayDynamic;

	public static void main(String[] args) throws FileNotFoundException {

		readFile();// reading file

		// inputs

		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter gold amount");
		int goldAmount = sc.nextInt();
		while (!(goldAmount >= 5 && goldAmount <= 1200)) {

			System.out.println("Please enter gold amount");
			goldAmount = sc.nextInt();
		}

		System.out.println("Please enter max level allowed");
		int maxLevelAllowed = sc.nextInt();
		while (!(maxLevelAllowed >= 1 && maxLevelAllowed <= 9)) {

			System.out.println("Please enter max level allowed");
			maxLevelAllowed = sc.nextInt();
		}

		System.out.println("Please enter number of pieces");
		int numberOfPieces = sc.nextInt();

		while (!(numberOfPieces >= 1 && numberOfPieces <= 25)) {

			System.out.println("Please enter number of pieces");
			 numberOfPieces = sc.nextInt();
		}

		////// TRIAL#1
		System.out.println("**************TRIAL#1****************\n");
		System.out.println("Computer Greedy Approach\n");

		double startTime = System.nanoTime();
		GreedyApproach(goldAmount, maxLevelAllowed, numberOfPieces);
		double estimatedTime = (System.nanoTime() - startTime);
		System.out.println("estimatedTime Random Approach : " + estimatedTime / 1000000 + "ms\n\n"); // estimatedTime
																										// greedy
		System.out.println("User's Dynamic Approach\n");

		herosArrayDynamic = new Heros[maxLevelAllowed * numberOfPieces];
		for (int i = 1; i <= maxLevelAllowed; i++) {
			for (int j = 0; j < numberOfPieces; j++) {
				int dynamic_index = (i - 1) * 10 + j;
				Heros heros_dynamic = new Heros(herosArray[dynamic_index].getName(),
						herosArray[dynamic_index].getType(), herosArray[dynamic_index].getGold(),
						herosArray[dynamic_index].getAttackpoint());
				addHerosDynamic(heros_dynamic);
			}

		}
		startTime = System.nanoTime();
		System.out.println("Attack Point :" + DynamicApproach(goldAmount, herosArrayDynamic, herosArrayDynamic.length));
		estimatedTime = (System.nanoTime() - startTime);
		System.out.println("estimatedTime Dynamic Approach  : " + estimatedTime / 1000000 + "ms\n\n");

		System.out.println("**************TRIAL#2****************\n");
		System.out.println("Computer Random Approach\n");
		startTime = System.nanoTime();
		randomApproach(goldAmount, maxLevelAllowed, numberOfPieces);
		estimatedTime = (System.nanoTime() - startTime);
		System.out.println("estimatedTime Random Approach  : " + estimatedTime / 1000000 + "ms\n\n");

		System.out.println("User's Dynamic Approach\n");

		startTime = System.nanoTime();
		System.out.println("Attack Point :" + DynamicApproach(goldAmount, herosArrayDynamic, herosArrayDynamic.length));
		estimatedTime = (System.nanoTime() - startTime);
		System.out.println("estimatedTime Dynamic Approach  : " + estimatedTime / 1000000 + "ms\n\n");

	}

	public static void readFile() throws FileNotFoundException { // for reading file
		Scanner sc = new Scanner(new File("input_1.csv"));
		int counter = 0;
		String[] delim;

		while (sc.hasNextLine()) // returns a boolean value
		{

			String temp = sc.nextLine().toString();

			delim = temp.split(",");
			// System.out.println(delim[0]+" "+delim[1]+" "+delim[2]+" "+delim[3]);
			if (counter != 0) {
				addHeros(new Heros(delim[0], delim[1], Integer.parseInt(delim[2].toString()),
						Integer.parseInt(delim[3].toString())));
			}

			counter++;

		}
		sc.close(); // closes the scanner
	}

	public static int addHeros(Heros heros) { // adding variables to array as Hero type

		herosArray[index] = heros;
		index++;
		return index;

	}

	public static int addHerosDynamic(Heros heros) { // adding variables to array as Hero type

		herosArrayDynamic[dynamic_index] = heros;
		dynamic_index++;
		return dynamic_index;

	}

	public static void randomApproach(int goldAmount, int maxLevelAllowed, int numberOfPieces) { // Random Approach
																									// Procedure

		ArrayList<Integer> levelarray = new ArrayList<>();
		Random rnd = new Random();
		boolean flagOnePiecesPerLevel;
		int levelRandom;
		int numberOfpiece;
		int totalgold = 0;
		int attack_point = 0;
		while (levelarray != null && levelarray.size() < maxLevelAllowed) {
			do {
				flagOnePiecesPerLevel = true;

				levelRandom = rnd.nextInt(maxLevelAllowed) + 1;

				if (levelarray.contains(levelRandom)) {
					flagOnePiecesPerLevel = false;
				} else
					levelarray.add(levelRandom);

			} while (flagOnePiecesPerLevel == false);
			int minGold = goldAmount;

			boolean flag_enough_gold = false;
			for (int i = 0; i < numberOfPieces; i++) {
				int index = (levelRandom - 1) * 10 + i;
				if (minGold >= herosArray[index].getGold()) {
					flag_enough_gold = true;
					break;
				}

			}
			int index = 0;
			while (flag_enough_gold) {
				numberOfpiece = rnd.nextInt(numberOfPieces) + 1;
				index = (levelRandom - 1) * 10 + numberOfpiece - 1;
				if (herosArray[index].getGold() <= goldAmount) {
					flag_enough_gold = false;
					goldAmount = goldAmount - herosArray[index].getGold();
					totalgold = herosArray[index].getGold() + totalgold;
					attack_point = attack_point + herosArray[index].getAttackpoint();
					System.out.println(herosArray[index].getName() + " (" + herosArray[index].getType() + ","
							+ herosArray[index].getGold() + "," + herosArray[index].getAttackpoint() + ")");

					goldAmount = goldAmount - herosArray[index].getGold();

				}
			}

		}

		// print total gold and attack point for random approach

		System.out.println("\nSpent Gold :" + totalgold);
		System.out.println("Attack Point : " + attack_point);

	}

	public static void GreedyApproach(int goldAmount, int maxLevelAllowed, int numberOfPieces) {
		int maxAttackPoint;
		int greedyindex = 0;
		int totalgold = 0;
		int attack_point = 0;
		for (int i = 1; i <= maxLevelAllowed; i++) {
			maxAttackPoint = 0;
			for (int j = 0; j < numberOfPieces; j++) {
				int index = (i - 1) * 10 + j;
				if (herosArray[index].getAttackpoint() > maxAttackPoint && herosArray[index].getGold() <= goldAmount) {
					maxAttackPoint = herosArray[index].getAttackpoint();
					greedyindex = index;
				}
			}
			if (herosArray[greedyindex].getGold() <= goldAmount) {
				System.out.println(herosArray[greedyindex].getName() + " (" + herosArray[greedyindex].getType() + ","
						+ herosArray[greedyindex].getGold() + "," + herosArray[greedyindex].getAttackpoint() + ")");
				goldAmount = goldAmount - herosArray[greedyindex].getGold();
				totalgold = totalgold + herosArray[greedyindex].getGold();
				attack_point = attack_point + herosArray[greedyindex].getAttackpoint();
			}
		}

		System.out.println("\nSpent Gold :" + totalgold);
		System.out.println("Attack Point : " + attack_point);

	}

	static int max(int a, int b) {
		return (a > b) ? a : b;
	}

	// Returns the maximum value that can be put in a knapsack
	// of capacity W
	static int DynamicApproach(int goldAmount, Heros[] heros, int lenght) {
		int i, w;
		int K[][] = new int[lenght + 1][goldAmount + 1];

		// Build table K[][] in bottom up manner
		for (i = 0; i <= lenght; i++) {
			for (w = 0; w <= goldAmount; w++) {
				if (i == 0 || w == 0)
					K[i][w] = 0;
				else if (heros[i - 1].getGold() <= w)
					K[i][w] = max(heros[i - 1].getAttackpoint() + K[i - 1][w - heros[i - 1].getGold()], K[i - 1][w]);
				else
					K[i][w] = K[i - 1][w];
			}
		}

		return K[lenght][goldAmount];
	}

	// Driver program to test above function

}

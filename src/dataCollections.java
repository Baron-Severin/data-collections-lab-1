import com.sun.deploy.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by mgkan on 2016-06-29.
 */
public class dataCollections {
	static ArrayList<String> products = new ArrayList<>();
	static String[][] battleBoard = new String[5][5];
	final static int EMPTY_HIT = 2; // an empty cell that has been hit (missed)
	final static int OCCUPIED = 3; // a cell that has been occupied by a ship
	final static int OCCUPIED_HIT = 4; // a cell that has been occupied and hit
	static Random rand = new Random();
	static int guesses = 0;
	static String player = "";
	static HashMap<String,String> list = new HashMap();

	public static void main(String[] args) {
		while (true) {
			homeScreen();
			String name = scan();
			if (name.contains("add")) {
				addProduct(name);
			} else if (name.contains("list")) {
				listProduct();
			} else if (name.contains("delete")) {
				removeProduct(name);
			} else if (name.contains("help")) {
				helpDianna();
			} else if (name.contains("play")) {
			if(products.size()>0){
				try {
					playGame();
				} catch (IOException e) {
					e.printStackTrace();
				}}else{
				System.out.println("There are no items in the inventory, please add some items before playing");
			}
			} else if (name.contains("exit")) {
				System.exit(0);
			} else if (name.contains("history")){
				try {
					System.out.println("-----The Records-----");
					readHistory();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void addProduct(String userInput) {
		if (userInput.length() > 3 && userInput.charAt(3) != ' ') {
			String item = userInput.substring(3);
			System.out.println(item + " is added to the inventory");
			products.add(item);
		} else if (userInput.length() > 4) {
			String item = userInput.substring(4);
			System.out.println(item + " is added to the inventory");
			products.add(item);
		} else {
			System.out.println("Try again.");
		}
	}

	public static void removeProduct(String userInput) {
		try {
			if (userInput.length() > 6 && userInput.charAt(6) != ' ') {
				String item = userInput.substring(6);
				int x = Integer.parseInt(item);
				if (x >= 0 && x <= products.size()) {
					System.out.println(item + " is removed from the inventory");
					products.remove(x - 1);
				} else if (userInput.length() > 7) {
					item = userInput.substring(7);
					x = Integer.parseInt(item);
					if (x >= 0 && x <= products.size()) {
						System.out.println(item + " is removed from the inventory");
						products.remove(x - 1);
					}
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("This is not a number");
		}
	}

	public static void helpDianna() {
		System.out.println("'Add _____' command is used to add a product to your inventory.");
		System.out.println("'List' command displays the list of items in your inventory.");
		System.out.println("'Delete #' command gets rid of an item from your inventory.");
		System.out.println("'Play' command will let you play a fun game of battleship!");
		System.out.println("'History' command will show you the game record");
		System.out.println("IF YOU DID NOT GET A CONFIRMATION PROMPT, THE CHANGE DID NOT HAPPEN.");
	}

	public static void listProduct() {
		if (products.isEmpty() == true) {
			System.out.println("The inventory is empty");
		} else {
			ArrayList<String> inven = new ArrayList<>();
			for (int i = 0; i < products.size(); i++) {
				String item = "";
				String[] itemP = products.get(i).split(" ");
				for (int j = 0; j < itemP.length; j++) {
					if (itemP[j].length() > 0) {
						item += (itemP[j].substring(0, 1).toUpperCase() + itemP[j].substring(1).toLowerCase() + " ");
					}
				}
				inven.add(item);

			}
			Collections.sort(inven);
			for (String item : inven) {
				System.out.println(item);
			}
		}
	}

	//	Write a function for a home screen to welcome users to Diannas every time they insert a command. The screen should contain the name "Dianna's Dinosaur & Donut Emporium" and the list of possible commands (Help gives a more detailed explanation).
	public static void homeScreen() {
		System.out.println("---------------------------");
		System.out.println("Welcome to Dianna's Dinosaur & Donut Emporium. How may I help you?");
		System.out.println("Your choices are, 'Add 'item name'', 'List', 'Delete #', 'Play', 'History' and 'Help'");
		System.out.println("Please type your choice or type 'exit' to exit out of the program");
		System.out.println("---------------------------");
	}

	public static String scan() {
		Scanner input = new Scanner(System.in);
		String userInput = input.nextLine();
		return userInput.toLowerCase();

	}

	//build board
	public static void buildBattleBoard() {
		String[][] board = battleBoard;

		for (int i = 0; i < battleBoard.length; ++i) {
			String[] row = board[i];
			Arrays.fill(row, "*");
		}
	}

	public static void redrawBoard() {
		int k;
		for (k = 1; k <= 15; ++k) {
			System.out.print('-');
		}

		System.out.println();

		for (int i = 0; i < battleBoard.length; ++i) {
			for (int j = 0; j < battleBoard[i].length; ++j) {
				System.out.print("|" + battleBoard[i][j] + "|");
			}

			System.out.println();
		}

		for (k = 1; k <= 15; ++k) {
			System.out.print('-');
		}

		System.out.println();
	}


	public static String[][] boardState(String[][] board) {
		String[][] bo = new String[5][5];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].equals(2)) {
					bo[i][j] = "X";
				} else if (board[i][j].equals(OCCUPIED_HIT)) {
					bo[i][j] = "O";
				} else {
					bo[i][j] = "*";
				}
			}
		}

		return bo;
	}

	public static boolean fireAt(String[][] board, int x, int y) {
		if(list.containsKey(x+","+y)) {
			board[y][x] = OCCUPIED_HIT;
			return true;
		}

		System.out.println("Board[y][x] = " + board[y][x]);
		board[y][x] = EMPTY_HIT;
		return false;
	}

	//play game
	public static void playGame() throws IOException {

		int rand =0;
		int randN=0;
		for (int i = 0; i < products.size(); ++i) {
			rand = (int) (Math.random() * 5);
			randN = (int) (Math.random() * 5);
			list.put(rand+","+randN, products.get(i));
		}
		buildBattleBoard();
		redrawBoard();
		Scanner s2 = new Scanner(System.in);

		System.out.println("Lets play a game of Battleship with your favorite items!!");
		System.out.println("Please enter the x and y coordinates by \"x,y\"");

		boolean won = false;
		do {
			System.out.println("Fire to a position:");
			String attemptString = s2.nextLine();

			String[] splitAttemptString = attemptString.split(",");

			int attemptX = Integer.parseInt(splitAttemptString[0]);
			int attemptY = Integer.parseInt(splitAttemptString[1]);
			guesses++;
			boolean fi = fireAt(battleBoard, attemptX, attemptY);
			if (fi) {
				System.out.println("YES! YOU HAVE HIT A "+ list.get(attemptString));
			} else {
				System.out.println("You have not hit anything please try again");
			}

			System.out.println("Board state:");
			String[][] sBoard = boardState(battleBoard);
//redraw grid every turn
			for (int i = 0; i < sBoard.length; i++) {
				String thing = "";
				for (int j = 0; j < sBoard[i].length; j++) {
					thing += ("|" + sBoard[i][j] + "|");
				}

				System.out.println(thing);
			}
//win to end game
			boolean occupied = false;
			for (int i = 0; i < battleBoard.length; i++) {
				for (int j = 0; j < battleBoard[i].length; j++) {
				String tempString = i + "," + j;
					if (battleBoard[i][j].equals(OCCUPIED)) {
						occupied = true;
					}
				}
			}

			if (!occupied) {
				won = true;
			}
		} while (!won);

		System.out.println("Congrats!!! You have won the game. it took " + guesses + " guesses.");
		System.out.println("Please Enter Your Name");
		Scanner s3 = new Scanner(System.in);
		player = s3.nextLine();
		try {
			writeHistory();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//store game history
	public static void writeHistory()throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("gameHistory.txt",true));
			writer.write(guesses + "  -   " + player);
			writer.newLine();

		} catch (IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}
	}

	//read game history
	public static void readHistory()throws IOException {
		ArrayList<String> record = new ArrayList<>();
		Scanner s4 = new Scanner(new File("gameHistory.txt"));
		while (s4.hasNextLine()) {
			record.add(s4.nextLine());
		}
		Comparator<String> comparator = new MyComparator();
		Collections.sort(record,comparator);
		for (int i = 0; i < record.size(); i++) {
			System.out.println(record.get(i));
		}
	}
}
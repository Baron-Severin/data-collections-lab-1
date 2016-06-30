import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by mgkan on 2016-06-29.
 */
public class dataCollections {
  static String[] nameW = {"dog","cat","sun","Moon Walk","How are you"};
	static ArrayList<String> products = new ArrayList<>(Arrays.asList(nameW));
	public static void main(String[] args) {
	while(true) {
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
		}else if(name.contains("exit")) {
			System.exit(0);
		}

	}
	}

	//	Write a function that will allow a user to add Dinosaur or Donut products to the inventory when they type in "Add". For example: "Add Creme Filled Dino Donut" should add the string "Creme Filled Dino Donut" to the inventory.
	public static void addProduct(String userInput) {
	 if(userInput.length()>3 && userInput.charAt(3) !=' '){
		String item = userInput.substring(3);
		System.out.println(item + " is added to the inventory");
		products.add(item);
	}else if(userInput.length()>4) {
			String item = userInput.substring(4);
			System.out.println(item + " is added to the inventory");
			products.add(item);
		}else{
		 System.out.println("Try again.");
	 }
	}
	//	Write a function that removes a product from the inventory when they type in "Delete". For example: "Delete 3" should remove the third item in the collection (not the fourth!).
	public static void removeProduct(String userInput){
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
		}catch (NumberFormatException e) {
			System.out.println("This is not a number");
		}
		}
	//	Dianna has trouble remembering things after her tragic Brontosaurus accident. Write a function for a "Help" command so that she can see all the different actions she can perform in the program. This should print out explanations for the commands in steps 1-3.
	public static void helpDianna(){
		System.out.println("'Add _____' command is used to add a product to your inventory.");
		System.out.println("'List' command displays the list of items in your inventory.");
		System.out.println("'Delete #' command gets rid of an item from your inventory.");
		System.out.println("IF YOU DID NOT GET A CONFIRMATION PROMPT, THE CHANGE DID NOT HAPPEN.");
	}
  //	Write a function that will allow a user to see the list of contents of the inventory of the Emporium when they type in "List". Make sure the list is sorted so they can find what they want! Make sure that the first letter of each word is capitalized and every other letter is lowercase. We want to make sure everything looks great for Dianna!
  public static void listProduct(){
  	if(products.isEmpty()==true){
			System.out.println("The inventory is empty");
		}else {
			ArrayList<String> inven = new ArrayList<>();
			for (int i = 0; i < products.size(); i++) {
				String item = "";
				String[] itemP = products.get(i).split(" ");
				for (int j = 0; j < itemP.length; j++) {
				  if(itemP[j].length()>0) {
						item += (itemP[j].substring(0, 1).toUpperCase() + itemP[j].substring(1).toLowerCase() + " ");
					}
				}
				inven.add(item);

			}
			Collections.sort(inven);
			for(String item:inven) {
				System.out.println(item);
			}
		}
  }
  //	Write a function for a home screen to welcome users to Diannas every time they insert a command. The screen should contain the name "Dianna's Dinosaur & Donut Emporium" and the list of possible commands (Help gives a more detailed explanation).
  public static void homeScreen(){
		System.out.println("---------------------------");
		System.out.println("Welcome to Dianna's Dinosaur & Donut Emporium. How may I help you?");
		System.out.println("Your choices are, 'Add 'item name'', 'List', 'Delete #', and 'Help'");
		System.out.println("Please type your choice or type 'exit' to exit out of the program");
		System.out.println("---------------------------");
	}
	public static String scan(){
		Scanner input = new Scanner(System.in);
		String userInput = input.nextLine();
		return userInput.toLowerCase();

	}
}
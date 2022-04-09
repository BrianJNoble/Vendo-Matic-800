package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";

	// add the exit option to the main menu
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";

	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };

	// add the purchase menu options
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}
	String filePath = "C:\\Users\\zanka\\Desktop\\meritamerica\\repos\\module-1-capstone\\capstone\\vendingmachine.csv";
	VendingMachine vendingMachine = new VendingMachine(filePath);

	// add a method to display the vending machine menu
//	public static void displayMenuItems() {
//		File inventory = new File("C:\\Users\\zanka\\Desktop\\meritamerica\\repos\\module-1-capstone\\capstone\\vendingmachine.csv");
//		try(Scanner fileScanner = new Scanner(inventory)) {
//			while(fileScanner.hasNextLine()) {
//				System.out.println(fileScanner.nextLine());
//			}
//		} catch(FileNotFoundException e) {
//			System.err.println("Vending Machine inventory not found.");
//		}
//	}
//
//	public static String selectProduct(String productCode) {
//		File inventory = new File("C:\\Users\\zanka\\Desktop\\meritamerica\\repos\\module-1-capstone\\capstone\\vendingmachine.csv");
//		try(Scanner fileScanner = new Scanner(inventory)) {
//			while(fileScanner.hasNextLine()) {
//				String currentLine = fileScanner.nextLine();
//				if(productCode.equals(currentLine.substring(0, 2))) {
//					return currentLine;
//				}
//			}
//			if (!fileScanner.hasNextLine()) {
//				return "No such product found.";
//			}
//		}catch(FileNotFoundException e) {
//			System.err.println("Vending Machine inventory not found.");
//		}
//		return null;
//	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				vendingMachine.displayMenuItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				String choice2 = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
				boolean purchasing = true;
				while(purchasing) {
					if (choice2.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						Scanner userInput = new Scanner(System.in);
						System.out.println("Enter a valid whole bill amount ($1, $2, $5, $10, $20");
						vendingMachine.feedMoney(userInput.nextInt());

					} else if (choice2.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						vendingMachine.displayMenuItems();
						Scanner userInput = new Scanner(System.in);
						System.out.println("Enter a valid item code");
						String itemCode = userInput.next().toUpperCase(Locale.ROOT);

						vendingMachine.dispenseItem(itemCode);

					} else if (choice2.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
						purchasing = false;

					}
					System.out.println("Current money provided: " + "$" + vendingMachine.getCurrentMoney());
					choice2 = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
				}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				// do exit
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}

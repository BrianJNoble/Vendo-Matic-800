package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	// added the exit option to the main menu
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };

	// added the purchase menu options
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

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine inventory
				vendingMachine.displayMenuItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// display purchase menu options
				String choice2 = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
				boolean purchasing = true;
				while(purchasing) {
					if (choice2.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) { // if feed money is selected, it prompts the user and then calls the feedMoney function

						Scanner userInput = new Scanner(System.in);
						System.out.println("Enter a valid whole bill amount ($1, $2, $5, $10, $20)");
						vendingMachine.feedMoney(Integer.parseInt(userInput.next().replaceAll("[^0-9]", "")));
						System.out.println("Current money provided: " + "$" + vendingMachine.getCurrentMoney());
						choice2 = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					} else if (choice2.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) { // if select product is selected, displays the current inventory and prompts the user for an item code to dispense the item

						vendingMachine.displayMenuItems();
						Scanner userInput = new Scanner(System.in);
						System.out.println("Enter a valid item code");
						String itemCode = userInput.next().toUpperCase(Locale.ROOT);

						if(vendingMachine.getItemCost(itemCode) <= vendingMachine.getCurrentMoney()) { // checks to make sure the user has enough money
							vendingMachine.dispenseItem(itemCode);
						} else {
							System.out.println("Not enough money.");
						}

						System.out.println("Current money provided: " + "$" + vendingMachine.getCurrentMoney());
						choice2 = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					} else if (choice2.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) { // finishes the transaction and goes back to the main menu

						vendingMachine.finishTransaction();
						purchasing = false;

					}
				}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) { // exits the application
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

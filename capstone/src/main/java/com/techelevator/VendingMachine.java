package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {
    private File inventory = null;
    private List<String> inventoryList = new ArrayList<>();
    private double currentMoney;

    public VendingMachine(String filePath) {
        inventory = new File(filePath);
        try(Scanner fileScanner = new Scanner(inventory)) { // populates the inventory list with each line from the inventory file
            while(fileScanner.hasNextLine()) {
                String currentLine = fileScanner.nextLine();
                inventoryList.add(currentLine);
            }
        } catch(FileNotFoundException e) {
            System.err.println("Vending Machine inventory not found.");
        }
    }

    public VendingMachine(String filePath, double currentMoney) {
        inventory = new File(filePath);
        try(Scanner fileScanner = new Scanner(inventory)) {
            while(fileScanner.hasNextLine()) {
                String currentLine = fileScanner.nextLine();
                inventoryList.add(currentLine);
            }
        } catch(FileNotFoundException e) {
            System.err.println("Vending Machine inventory not found.");
        }
        this.currentMoney = currentMoney;
    }

    public void feedMoney(int amount) {
        if(amount == 1 || amount == 2 || amount == 5 || amount == 10 || amount == 20) { // only accepts valid bill sizes
            currentMoney += amount;
            Log.log("FEED MONEY: " + "$" + amount + ".00 " + "$" + currentMoney);
        } else {
            System.out.println("Please enter a valid whole bill amount ($1, $2, $5, $10, $20)");
        }
    }

    public double getCurrentMoney() {
        return currentMoney;
    }

    public String[] getInventory() {
        return inventoryList.toArray(new String[0]);
    }

    public void displayMenuItems() {
        // prints out each line from the inventory list
        for (String item : inventoryList) {
            System.out.println(item);
        }
    }

    public void dispenseItem(String itemCode) {
        // check if item exists, if it does print the appropriate message while decrementing the stock, otherwise indicate that the item doesn't exist.
        boolean productExists = false;
        for(int i = 0; i < inventoryList.size(); i++) {

            // Takes the individual line and splits it at the delimiter |
            String item = inventoryList.get(i);
            String[] itemSplit = item.split("\\|");

            if(itemCode.equals(itemSplit[0]) && !itemSplit[4].equals("Sold out!")) {
                if(Integer.parseInt(itemSplit[4]) > 1) { // Checks the stock of the selected item, if > 1 decrement the stock on purchase
                    if(item.contains("Chip")) {
                        System.out.println("Crunch Crunch, Yum");
                    } else if (item.contains("Candy")) {
                        System.out.println("Munch Munch, Yum");
                    } else if (item.contains("Drink")) {
                        System.out.println("Glug Glug, Yum!");
                    } else if (item.contains("Gum")) {
                        System.out.println("Chew Chew, Yum!");
                    }
                    productExists = true;

                    itemSplit[4] = String.valueOf(Integer.parseInt(itemSplit[4]) - 1);
                    String str = String.join("|", itemSplit);
                    inventoryList.set(i, str);
                    // Log the purchase. "Potato Crisps A1 $Before $After"
                    Log.log(itemSplit[1] + " " + itemSplit[0] + " $" + currentMoney + " $" + (Math.round((currentMoney-this.getItemCost(itemCode))*100.0)/100.0));
                    currentMoney -= this.getItemCost(itemCode);
                    currentMoney = Math.round(currentMoney * 100.0)/100.0;
                }
                else if(Integer.parseInt(itemSplit[4]) == 1) { // If stock of selected item == 1, it changes the stock to "Sold out!"
                    if(item.contains("Chip")) {
                        System.out.println("Crunch Crunch, Yum");
                    } else if (item.contains("Candy")) {
                        System.out.println("Munch Munch, Yum");
                    } else if (item.contains("Drink")) {
                        System.out.println("Glug Glug, Yum!");
                    } else if (item.contains("Gum")) {
                        System.out.println("Chew Chew, Yum!");
                    }
                    productExists = true;

                    itemSplit[4] = "Sold out!";
                    String str = String.join("|", itemSplit);
                    inventoryList.set(i, str);
                    currentMoney -= this.getItemCost(itemCode);
                    currentMoney = Math.round(currentMoney*100.0)/100.0;
                }

            }
            else if (itemCode.equals(itemSplit[0]) && itemSplit[4].equals("Sold out!")) { // If the item is sold out, prints the appropriate message
                System.out.println("Item is sold out!");
            }
        }
        if (!productExists) {
            System.out.println("No such item found.");
        }
    }

    // return the cost of an item based on the item code provided
    public double getItemCost(String itemCode) {
        boolean productExists = false;
        double itemCost = 0;
        for(int i = 0; i < inventoryList.size(); i++) {
            String item = inventoryList.get(i);
            String[] itemSplit = item.split("\\|");
            if(itemCode.equals(itemSplit[0])) {
                productExists = true;
                itemCost = Double.parseDouble(itemSplit[2]);
            }
        }
        if (!productExists) {
            System.out.println("No such item found.");
        }
        return itemCost;
    }

    // dispenses change and sets current money to 0
    public void finishTransaction() {
        Log.log("GIVE CHANGE: $" + currentMoney + " $0");
        int quarters = (int)Math.floor((currentMoney*100)/25); // multiply by 100 to make sure there's a whole number before dividing by 25 to get the amount of quarters
        currentMoney = (Math.round((currentMoney%0.25)*100.0))/100.0; // set currentMoney to the remaining balance (using Math.round to avoid floating point errors)
        int dimes = (int)Math.floor((currentMoney*100)/10); // same as above but with dimes
        currentMoney = (Math.round((currentMoney%0.10)*100.0))/100.0;
        int nickels = (int)Math.floor((currentMoney*100)/5);

        currentMoney = 0; // Set money to 0 after dispensing change
        System.out.println("Dispensing " + quarters + " quarters");
        System.out.println("Dispensing " + dimes + " dimes");
        System.out.println("Dispensing " + nickels + " nickels");
    }

}

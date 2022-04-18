package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class VendingMachineTest {

    public ByteArrayOutputStream output = new ByteArrayOutputStream();
    public String filePath = "C:\\Users\\zanka\\Desktop\\meritamerica\\repos\\module-1-capstone\\capstone\\vendingmachine.csv";



    @Test
    public void feedMoney_only_accepts_valid_bills() {
        //arrange
        System.setOut(new PrintStream(output));
        VendingMachine vendingMachine = new VendingMachine(filePath);

        String expected = "Please enter a valid whole bill amount ($1, $2, $5, $10, $20)" + System.lineSeparator();
        //act

        vendingMachine.feedMoney(6);
        //assert

        Assert.assertEquals(expected, output.toString());
    }

    @Test
    public void getCurrentMoney_return_correct_amount() {
        int money = 32;
        VendingMachine vendingMachine = new VendingMachine(filePath, money);

        Assert.assertEquals(money, vendingMachine.getCurrentMoney(), 0);
    }

    @Test
    public void displayMenuItems_updates_after_dispensing() {
        System.setOut(new PrintStream(output));
        VendingMachine vendingMachine = new VendingMachine(filePath, 69);

        vendingMachine.dispenseItem("A1");
        vendingMachine.dispenseItem("A1");

        vendingMachine.displayMenuItems();
        String[] result = output.toString().split(System.lineSeparator());

        Assert.assertEquals(result[2], "A1|Potato Crisps|3.05|Chip|3");

    }

    @Test
    public void dispenseItem_fails_if_out_of_stock() {
        System.setOut(new PrintStream(output));
        VendingMachine vendingMachine = new VendingMachine(filePath, 69);

        vendingMachine.dispenseItem("A1");
        vendingMachine.dispenseItem("A1");
        vendingMachine.dispenseItem("A1");
        vendingMachine.dispenseItem("A1");
        vendingMachine.dispenseItem("A1");
        vendingMachine.dispenseItem("A1");

        String[] result = output.toString().split(System.lineSeparator());

        Assert.assertEquals(result[5], "Item is sold out!");
    }

    @Test
    public void finishTransaction_gives_appropriate_change() {
        System.setOut(new PrintStream(output));
        VendingMachine vendingMachine = new VendingMachine(filePath, 69);

        vendingMachine.dispenseItem("A1");
        vendingMachine.dispenseItem("A1");

        vendingMachine.finishTransaction();
        String[] result = output.toString().split(System.lineSeparator());
        String finalResult = result[2] + System.lineSeparator() + result[3] + System.lineSeparator() + result[4];
        Assert.assertEquals("Dispensing 251 quarters" + System.lineSeparator() + "Dispensing 1 dimes" + System.lineSeparator() + "Dispensing 1 nickels", finalResult);
    }

}

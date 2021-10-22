package com.mthree.c130.zuhaa.ui;

import com.mthree.c130.zuhaa.dao.Change;
import com.mthree.c130.zuhaa.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void welcomeBanner() {
        io.print("Welcome to the Vending Machine!");
    }

    public int getItemSelection(List<Item> itemList) {
        io.print("Choose your item:");
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getInventory() > 0) {
                io.print("      " + (i + 1) + ". " + itemList.get(i).getName() + " (£" + itemList.get(i).getCost() + ")");
            }
        }
        io.print("      " + (itemList.size() + 1) + ". Exit");
        return io.readInt("Please select from the above choices.", 1, itemList.size() + 1);
    }

    public BigDecimal getMoneyAmount() {
        return io.readBigDecimal("Please enter the amount of money you have: £");
    }


    public Boolean checkValidAmount(BigDecimal amount, Item chosenItem) {
        Boolean cont = true;
        if (amount.compareTo(chosenItem.getCost()) < 0) {
            io.print("Insufficient funds. You needed: £" + chosenItem.getCost());
            io.readString("Please hit enter to continue.");
            cont = false;
        }
        return cont;
    }

    public void showChange(Map<Change, Integer> changeDue) {
        io.print("Your change is: ");
        for (Change c : changeDue.keySet()) {
            io.print("      Return " + c + " coin(s) " + changeDue.get(c));
        }
        io.readString("Please hit enter to continue.");
    }

    public void exitBanner() {
        io.print("Good bye!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("Error!");
        io.print(errorMsg);
    }

}

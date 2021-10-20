package com.mthree.c130.zuhaa.dao;

import com.mthree.c130.zuhaa.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class VendingMachineDaoFileImpl implements VendingMachineDao{

    private final String INVENTORY_FILE;
    public static final String DELIMITER = "::";

    public VendingMachineDaoFileImpl(){
        INVENTORY_FILE = "inventory.txt";
    }

    public VendingMachineDaoFileImpl(String inventoryTextFile){
        INVENTORY_FILE = inventoryTextFile;
    }

    private Map<String, Item> items = new LinkedHashMap<>();

    public Item getItem(int choice) throws VendingMachinePersistenceException {
        loadInventory();
        List<Item> itemList = this.getAllItems();
        return itemList.get(choice);
    }

    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadInventory();
        return new ArrayList<Item>(items.values());
    }

    public Item sellItem(Item soldItem) throws VendingMachinePersistenceException {
        loadInventory();
        String soldName = soldItem.getName();
        int newInventory = soldItem.getInventory() - 1;
        soldItem.setInventory(newInventory);
        items.replace(soldName, soldItem);
        writeInventory();
        return null;
    }

    public Map<Change, Integer> calculateChange(Item soldItem, BigDecimal amountPaid) throws VendingMachinePersistenceException {
        loadInventory();
        BigDecimal itemCost = soldItem.getCost();
        BigDecimal change = amountPaid.subtract(itemCost);
        Map<Change, Integer> changeReturn = new LinkedHashMap<Change, Integer>();
        for (Change c : Change.values()){
            BigDecimal[] values = change.divideAndRemainder(c.value, MathContext.DECIMAL32);
            if (!(values[0].equals(BigDecimal.ZERO))&&!(values[1].equals(BigDecimal.ZERO))){
                changeReturn.put(c, values[0].intValue());
                change = values[1];
            }
        }
        return changeReturn;
    }

    private Item unmarshallItem(String itemAsText){
        String[] itemTokens = itemAsText.split(DELIMITER);
        String name = itemTokens[0];
        BigDecimal cost = new BigDecimal(itemTokens[1]);
        int inventory = Integer.parseInt(itemTokens[2]);
        Item itemFromFile = new Item(name, cost, inventory);
        return itemFromFile;
    }

    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e){
            throw new VendingMachinePersistenceException(
                    "-_- Could not load vending machine data into memory.",e);
        }

        String currentLine;
        Item currentItem;
        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);

            items.put(currentItem.getName(), currentItem);
        }

        scanner.close();
    }

    private String marshallItem(Item anItem){
        String itemAsText = anItem.getName() + DELIMITER;
        itemAsText += anItem.getCost() + DELIMITER;
        itemAsText += anItem.getInventory();
        return itemAsText;
    }

    private void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;

        try{
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e){
            throw new VendingMachinePersistenceException(
                    "Could not save vending machine data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList){
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }

        out.close();
    }
}

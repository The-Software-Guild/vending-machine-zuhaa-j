package com.mthree.c130.zuhaa.controller;

import com.mthree.c130.zuhaa.dao.Change;
import com.mthree.c130.zuhaa.dao.VendingMachinePersistenceException;
import com.mthree.c130.zuhaa.dto.Item;
import com.mthree.c130.zuhaa.service.InsufficientFundsException;
import com.mthree.c130.zuhaa.service.NoItemInventoryException;
import com.mthree.c130.zuhaa.service.VendingMachineServiceLayer;
import com.mthree.c130.zuhaa.ui.VendingMachineView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view){
        this.service = service;
        this.view = view;
    }

    public void run() throws VendingMachinePersistenceException {
        view.welcomeBanner();

        boolean keepGoing = true;
        int menuSelection = 0;
        int totalItemAmount = 0;
        try {
            while (keepGoing){

                menuSelection = getMenuSelection();
                totalItemAmount = getTotalItemAmount();

                if (menuSelection == totalItemAmount + 1){
                    keepGoing = false;
                    break;
                }

                BigDecimal amountPaid = gettingAmount();
                Item chosenItem = gettingItemChoice(menuSelection-1);

                if (checkAmount(chosenItem, amountPaid)){
                    sellTheItem(chosenItem, amountPaid);
                }
            }
            exitMessage();
        } catch (VendingMachinePersistenceException | NoItemInventoryException | InsufficientFundsException e){
            view.displayErrorMessage(e.getMessage());
        }

    }

    public int getMenuSelection() throws VendingMachinePersistenceException {
        List<Item> itemList = service.getAllItems();
        return view.getItemSelection(itemList);
    }

    public int getTotalItemAmount() throws VendingMachinePersistenceException {
        List<Item> itemList = service.getAllItems();
        return itemList.size();
    }

    public BigDecimal gettingAmount() throws VendingMachinePersistenceException{
        BigDecimal amount = view.getMoneyAmount();
        return amount;
    }

    public Item gettingItemChoice(int choice) throws VendingMachinePersistenceException{
        return service.getItem(choice);
    }

    public Boolean checkAmount(Item chosenItem, BigDecimal amountPaid) throws VendingMachinePersistenceException {
        Boolean result = view.checkValidAmount(amountPaid, chosenItem);
        return result;
    }

    public void sellTheItem(Item soldItem, BigDecimal amountPaid) throws VendingMachinePersistenceException, NoItemInventoryException, InsufficientFundsException {
        Boolean hasErrors;
        do {
            try {
                service.sellItem(soldItem, amountPaid);
                hasErrors = false;
            } catch (VendingMachinePersistenceException | NoItemInventoryException | InsufficientFundsException e){
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);

        Map<Change,Integer> changeDue = service.calculateChange(soldItem, amountPaid);
        view.showChange(changeDue);
    }

    public void exitMessage(){
        view.exitBanner();
    }

}

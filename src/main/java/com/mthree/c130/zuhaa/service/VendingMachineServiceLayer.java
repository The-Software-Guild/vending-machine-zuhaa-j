package com.mthree.c130.zuhaa.service;

import com.mthree.c130.zuhaa.dao.Change;
import com.mthree.c130.zuhaa.dao.VendingMachinePersistenceException;
import com.mthree.c130.zuhaa.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface VendingMachineServiceLayer {

    void sellItem(Item buyingItem, BigDecimal amount) throws
            InsufficientFundsException,
            NoItemInventoryException,
            VendingMachinePersistenceException;

    List<Item> getAllItems() throws
            VendingMachinePersistenceException;

    Item getItem(int choice) throws
            VendingMachinePersistenceException;

    Map<Change, Integer> calculateChange(Item soldItem, BigDecimal amountPaid) throws
            VendingMachinePersistenceException;

}

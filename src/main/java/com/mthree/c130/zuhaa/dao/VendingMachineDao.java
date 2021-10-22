package com.mthree.c130.zuhaa.dao;

import com.mthree.c130.zuhaa.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface VendingMachineDao {

    Item getItem(int choice) throws VendingMachinePersistenceException;

    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Item sellItem(Item soldItem) throws VendingMachinePersistenceException;

    Map<Change, Integer> calculateChange(Item soldItem, BigDecimal amountPaid) throws VendingMachinePersistenceException;
}

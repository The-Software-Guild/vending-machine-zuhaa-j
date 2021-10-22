package com.mthree.c130.zuhaa.service;

import com.mthree.c130.zuhaa.dao.Change;
import com.mthree.c130.zuhaa.dao.VendingMachineAuditDao;
import com.mthree.c130.zuhaa.dao.VendingMachineDao;
import com.mthree.c130.zuhaa.dao.VendingMachinePersistenceException;
import com.mthree.c130.zuhaa.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    public void sellItem(Item buyingItem, BigDecimal amount) throws
            InsufficientFundsException,
            NoItemInventoryException,
            VendingMachinePersistenceException {

        validateMoneyAmount(buyingItem, amount);
        checkInventory(buyingItem);
        dao.sellItem(buyingItem);
        auditDao.writeAuditEntry(buyingItem.getName() + " SOLD.");
    }

    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    public Item getItem(int choice) throws VendingMachinePersistenceException {
        return dao.getItem(choice);
    }

    public Map<Change, Integer> calculateChange(Item soldItem, BigDecimal amountPaid) throws VendingMachinePersistenceException {
        return dao.calculateChange(soldItem, amountPaid);
    }

    private void validateMoneyAmount(Item buyingItem, BigDecimal amount) throws
            InsufficientFundsException {
        if (amount.compareTo(buyingItem.getCost()) < 0) {
            throw new InsufficientFundsException("Error: Insufficient Funds. You only entered: £" + amount);
        }
    }

    private void checkInventory(Item buyingItem) throws NoItemInventoryException {
        if (buyingItem.getInventory() <= 0) {
            throw new NoItemInventoryException("Error: Out of stock.");
        }
    }
}

package com.mthree.c130.zuhaa.service;

import com.mthree.c130.zuhaa.dao.VendingMachineAuditDao;
import com.mthree.c130.zuhaa.dao.VendingMachineDao;
import com.mthree.c130.zuhaa.dao.VendingMachineDaoFileImpl;
import com.mthree.c130.zuhaa.dao.VendingMachinePersistenceException;
import com.mthree.c130.zuhaa.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class VendingMachineServiceLayerImplTest {

    VendingMachineDao testDao;
    VendingMachineAuditDao testAuditDao;
    VendingMachineServiceLayer testService;

    public VendingMachineServiceLayerImplTest() {
    }

    @BeforeEach
    void setUp() throws Exception {
        String testFile = "testinventory.txt";
        testDao = new VendingMachineDaoFileImpl(testFile);
        testService = new VendingMachineServiceLayerImpl(testDao, testAuditDao);
    }

    @Test
    public void testNoItemInventory() throws Exception {
        // testinventory has two items with inventory 0 and -10
        // these will be stored in testItemList[1] and testItemList[2]
        List<Item> testItemList = testService.getAllItems();
        // using some valid amount of money
        BigDecimal amount = new BigDecimal("10.00");

        // want to test if we can sell items that are below the valid inventory level
        try {
            testService.sellItem(testItemList.get(1), amount);
            testService.sellItem(testItemList.get(2), amount);
            fail("Expected NoItemInventoryException not thrown.");
        } catch (VendingMachinePersistenceException
                | InsufficientFundsException e) {
            fail("Incorrect exception was thrown.");
        } catch (NoItemInventoryException e) {
            return;
        }
    }

    @Test
    public void testInsufficientFunds() throws Exception {
        // testItemList[0] has a valid inventory level
        List<Item> testItemList = testService.getAllItems();
        // using some invalid amount of money
        BigDecimal amount = new BigDecimal("0.00");

        // want to test if we can sell item with insufficient funds
        try {
            testService.sellItem(testItemList.get(0), amount);
            fail("Expected InsufficientFundsException was not thrown.");
        } catch (VendingMachinePersistenceException
                | NoItemInventoryException e) {
            fail("Incorrect exception was thrown.");
        } catch (InsufficientFundsException e) {
            return;
        }
    }


}
package com.mthree.c130.zuhaa.dao;

import com.mthree.c130.zuhaa.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineDaoFileImplTest {

    VendingMachineDao testDao;

    public VendingMachineDaoFileImplTest(){
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testinventory.txt";
        testDao = new VendingMachineDaoFileImpl(testFile);
    }

    @Test
    public void testGetAllItems() throws Exception{
        // testinventory has 3 items total already stored
        List<Item> testItemList = testDao.getAllItems();

        // testinventory should not be null
        assertNotNull(testItemList, "The list of items must not be null.");
        // size of testinventory should be equal to 3
        assertEquals(3, testItemList.size(), "Should have three items.");
    }

    @Test
    public void testGetItem() throws Exception{
        // know that first item in testinventory is:
        // Pringles::3.50::20
        // this will be presented with option 1.

        Item shouldBePringles = testDao.getItem(0);
        List<Item> testItemList = testDao.getAllItems();

        assertNotNull(shouldBePringles, "Getting the first item should not be null");
        assertEquals(testItemList.get(0), shouldBePringles,
                "Getting choice 1 item should be the same as getting first item in getAllItems()");
    }

    @Test
    public void testSellItem() throws Exception{
        // once an item is sold, the inventory should decrease by one
        // first item in inventory is valid
        Item validItem = testDao.getItem(0);
        int startingInventory = validItem.getInventory();
        int expectedFinalInventory = startingInventory - 1;
        testDao.sellItem(validItem);
        int finalInventory = validItem.getInventory();

        assertNotNull(finalInventory, "Inventory should not be null.");
        assertEquals(finalInventory, expectedFinalInventory,
                "Inventory after selling should decrease by 1.");
    }

}
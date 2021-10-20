package com.mthree.c130.zuhaa.dao;

public interface VendingMachineAuditDao {

    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;

}

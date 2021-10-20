package com.mthree.c130.zuhaa;

import com.mthree.c130.zuhaa.controller.VendingMachineController;
import com.mthree.c130.zuhaa.dao.*;
import com.mthree.c130.zuhaa.service.VendingMachineServiceLayer;
import com.mthree.c130.zuhaa.service.VendingMachineServiceLayerImpl;
import com.mthree.c130.zuhaa.ui.UserIO;
import com.mthree.c130.zuhaa.ui.UserIOConsoleImpl;
import com.mthree.c130.zuhaa.ui.VendingMachineView;

public class App {

    public static void main(String[] args) throws VendingMachinePersistenceException {
        UserIO myIo = new UserIOConsoleImpl();
        VendingMachineView myView = new VendingMachineView(myIo);
        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
        VendingMachineController controller = new VendingMachineController(myService, myView);
        controller.run();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.BrokerViewModel;

/**
 *
 * @author aliha_000
 */
public class BrokerRepository {

    public boolean Create(BrokerViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO Broker(BankAccountId) values ('" + vm.BankAccountId + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }
}

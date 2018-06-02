/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.BankTransactionViewModel;

/**
 *
 * @author aliha_000
 */
public class BankTransactionRepository {

    public boolean Create(BankTransactionViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO BankTransaction(Type,Amount,BankAccountId) values ('" + vm.Type + "','" + vm.Amount + "','" + vm.BankAccountid + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }

}

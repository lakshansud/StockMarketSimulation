/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.StockTransactionViewModel;

/**
 *
 * @author aliha_000
 */
public class StockTransactionRepository {

    public boolean Create(StockTransactionViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO StockTransaction(Price,Type,TurnId,BankAccountId,StockId) values ('" + vm.Price + "','" + vm.Type + "','" + vm.TurnId + "','" + vm.BankAccountId + "','" + vm.StockId + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }
}

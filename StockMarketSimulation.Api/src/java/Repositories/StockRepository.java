/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.StockViewModel;

/**
 *
 * @author aliha_000
 */
public class StockRepository {

    public boolean Create(StockViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO Stock(Name,CurrentPrice,CurrentValue) values ('" + vm.Name + "','" + vm.CurrentPrice + "','" + vm.CurrentValue + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }
}

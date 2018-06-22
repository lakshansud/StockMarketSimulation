/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.StockPriceHistoryViewModel;

/**
 *
 * @author aliha_000
 */
public class StockPriceHistoryRepository {

    public boolean Create(StockPriceHistoryViewModel vm) {
//        boolean isSaved = true;
//        try {
//            String insertQry = "INSERT INTO StockPriceHistory(CreateDate,PreviousValue,PreviousPrice) values ('" + vm.CreateDate + "','" + vm.PreviousValue + "','" + vm.PreviousPrice + "')";
//            isSaved = DB.save(insertQry);
//        } catch (Exception e) {
//            isSaved = false;
//            e.printStackTrace();
//        }
        return true;
    }
}

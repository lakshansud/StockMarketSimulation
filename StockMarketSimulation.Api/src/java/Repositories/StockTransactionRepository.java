/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.StockTransactionViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockTransactionRepository {

    public boolean Create(StockTransactionViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO StockTransaction(Price,Type,TurnId,BankAccountId,StockId,Quantity) VALUES ('" + vm.Price + "','" + vm.Type + "','" + vm.TurnId + "','" + vm.BankAccountId + "','" + vm.StockId + "','" + vm.Quantity + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }
 
    public ArrayList<StockTransactionViewModel> GetSellingItem(int bankId, int turnId, int typeId) {
        ArrayList<StockTransactionViewModel> stockTransactionList = new ArrayList<StockTransactionViewModel>();
        ResultSet rs = null;
        try {
            String searchQry = "SELECT Id,Price,Type,TurnId,BankAccountId,StockId,Quantity FROM StockTransaction WHERE BankAccountId='" + bankId + "' AND TurnId='" + turnId + "' AND Type='" + typeId + "'";
            rs = DB.fetch(searchQry);
            while (rs.next()) {
                StockTransactionViewModel StockTransaction = new StockTransactionViewModel();
                StockTransaction.Id = rs.getInt(1);
                StockTransaction.Price = rs.getDouble(2);
                StockTransaction.Type = rs.getInt(3);
                StockTransaction.TurnId = rs.getInt(4);
                StockTransaction.BankAccountId = rs.getInt(5);
                StockTransaction.Quantity = rs.getInt(6);
                stockTransactionList.add(StockTransaction);
            }

        } catch (Exception e) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StockTransactionRepository.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return stockTransactionList;
    }
}

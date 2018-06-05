/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.StockTransactionFullViewModel;
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

    public ArrayList<StockTransactionFullViewModel> GetSellingItem(int bankId, int round) {
        ArrayList<StockTransactionFullViewModel> stockTransactionList = new ArrayList<StockTransactionFullViewModel>();
        ResultSet rs = null;
        try {
            String searchQry = "SELECT StockTransaction.Id,StockTransaction.Price,StockTransaction.Type,Turn.Turn,Stock.Name,Sector.Name, BankAccountId,Quantity FROM StockTransaction INNER JOIN Turn ON StockTransaction.TurnId=Turn.Id INNER JOIN Stock ON Stock.Id=StockTransaction.StockId INNER JOIN Sector ON Sector.Id=Stock.SectorId WHERE BankAccountId=1 AND Turn.GameRoundId=1 AND Type=1";
            rs = DB.fetch(searchQry);
            while (rs.next()) {
                StockTransactionFullViewModel StockTransaction = new StockTransactionFullViewModel();
                StockTransaction.Id = rs.getInt(1);
                StockTransaction.Price = rs.getDouble(2);
                StockTransaction.Type = rs.getInt(3);
                StockTransaction.TurnNo = rs.getInt(4);
                StockTransaction.StockName = rs.getString(5);
                StockTransaction.SectorName = rs.getString(6);
                StockTransaction.BankAccountName = rs.getString(7);
                StockTransaction.Quantity = rs.getInt(8);
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

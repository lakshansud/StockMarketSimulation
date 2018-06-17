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
import java.sql.Array;
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
            String searchQry = "SELECT StockTransaction.Id,StockTransaction.Price,StockTransaction.Type,Turn.Turn,Stock.Name,Sector.Name, BankAccountId,Quantity,Stock.CurrentPrice FROM StockTransaction INNER JOIN Turn ON StockTransaction.TurnId=Turn.Id INNER JOIN Stock ON Stock.Id=StockTransaction.StockId INNER JOIN Sector ON Sector.Id=Stock.SectorId WHERE BankAccountId='" + bankId + "' AND Turn.GameRoundId='" + round + "' AND Type=1";
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
                StockTransaction.CurrentPrice = rs.getInt(9);
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

    public void SellItem(int stockTransactionId, int qty, double sellingPrice, int turnId, int bankAccoundId) {
        StockTransactionViewModel StockTransaction = new StockTransactionViewModel();
        ResultSet rs = null;
        try {
            String searchQry = "SELECT Id,Price,Type,TurnId, BankAccountId, StockId, Quantity FROM StockTransaction WHERE Id='" + stockTransactionId + "'";
            rs = DB.fetch(searchQry);
            while (rs.next()) {
                StockTransaction.Id = rs.getInt(1);
                StockTransaction.Price = rs.getDouble(2);
                StockTransaction.Type = rs.getInt(3);
                StockTransaction.TurnId = rs.getInt(4);
                StockTransaction.BankAccountId = rs.getInt(5);
                StockTransaction.StockId = rs.getInt(6);
                StockTransaction.Quantity = rs.getInt(7);
            }
            rs.close();
            String insertQry = "INSERT INTO StockTransaction(Price,Type,TurnId,BankAccountId,StockId,Quantity) VALUES ('" + sellingPrice + "','" + 1 + "','" + turnId + "','" + bankAccoundId + "','" + StockTransaction.StockId + "','" + qty + "')";
            Boolean isSaved = DB.save(insertQry);

            if (isSaved) {
                String updateQry = "UPDATE StockTransaction SET Quantity = '" + (StockTransaction.Quantity - qty) + "' WHERE Id = '" + stockTransactionId + "'";
                DB.save(updateQry);
            }

            double currentBalance = 0;
            String searchQry2 = "SELECT Balance FROM BankAccount WHERE Id='" + bankAccoundId + "'";
            rs = DB.fetch(searchQry2);
            while (rs.next()) {
                currentBalance = rs.getInt(1);
            }
            rs.close();

            String updateQry = "UPDATE BankAccount SET Balance = '" + (currentBalance + sellingPrice) + "' WHERE Id = '" + bankAccoundId + "'";
            DB.save(updateQry);

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
    }

    public void BuyItem(int qty, int stockId, int turnId, int bankAccoundId) {
        StockTransactionViewModel StockTransaction = new StockTransactionViewModel();
        ResultSet rs = null;
        try {
            double currentPrice = 0;
            String searchQry = "SELECT Id,CurrentPrice FROM Stock WHERE Id='" + stockId + "'";
            rs = DB.fetch(searchQry);
            while (rs.next()) {
                currentPrice = rs.getDouble(2);
            }
            rs.close();
            String insertQry = "INSERT INTO StockTransaction(Price,Type,TurnId,BankAccountId,StockId,Quantity) VALUES ('" + currentPrice + "','" + 2 + "','" + turnId + "','" + bankAccoundId + "','" + stockId + "','" + qty + "')";
            Boolean isSaved = DB.save(insertQry);

            double currentBalance = 0;
            String searchQry2 = "SELECT Balance FROM BankAccount WHERE Id='" + bankAccoundId + "'";
            rs = DB.fetch(searchQry2);
            while (rs.next()) {
                currentBalance = rs.getInt(1);
            }
            rs.close();

            String updateQry = "UPDATE BankAccount SET Balance = '" + (currentBalance + currentPrice) + "' WHERE Id = '" + bankAccoundId + "'";
            DB.save(updateQry);

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
    }

    public ArrayList<StockTransactionFullViewModel> GetHistory(int bankAccoundId, int roundId) {
        ArrayList<StockTransactionFullViewModel> StockTransactionList = new ArrayList<StockTransactionFullViewModel>();
        ResultSet rs = null;
        try {
            double currentPrice = 0;
            String searchQry = "SELECT Stock.Name, StockTransaction.Type,StockTransaction.Quantity,StockTransaction.Price FROM StockTransaction Inner Join Stock ON Stock.Id = StockTransaction.StockId WHERE TurnId=(SELECT Id from Turn WHERE GameRoundId='" + roundId + "') AND BankAccountId='"+bankAccoundId+"'";
            rs = DB.fetch(searchQry);
            while (rs.next()) {
                StockTransactionFullViewModel stockTransactionViewModel = new StockTransactionFullViewModel();
                stockTransactionViewModel.StockName = rs.getString(1);
                stockTransactionViewModel.Type = rs.getInt(2);
                stockTransactionViewModel.Quantity = rs.getInt(3);
                stockTransactionViewModel.Price = rs.getDouble(4);
                StockTransactionList.add(stockTransactionViewModel);
            }
            rs.close();

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
        return StockTransactionList;
    }
}

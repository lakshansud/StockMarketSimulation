/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.AnalystViewModel;
import Models.BankAccountViewModel;
import Models.SectorViewModel;
import Models.StockViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aliha_000
 */
public class StockRepository {

    public boolean Create(StockViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO Stock(Name,CurrentPrice,CurrentValue,SectorId) values ('" + vm.Name + "','" + vm.CurrentPrice + "','" + vm.CurrentValue + "','" + vm.SectorId + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }
    
     public List<StockViewModel> GetBySectorId(int sectorId) {
         ArrayList<StockViewModel> stockViewModelList = new ArrayList<StockViewModel>();
      ResultSet rs = null;
        try {
            String selectQry = "SELECT Id,Name,CurrentValue,CurrentPrice FROM Stock WHERE SectorId='"+ sectorId +"'";
            rs = DB.fetch(selectQry);
             while (rs.next()) {
                StockViewModel stockViewModel = new StockViewModel();
                stockViewModel.Id = rs.getInt(1);
                 stockViewModel.Name = rs.getString(2);
                stockViewModel.CurrentValue = rs.getInt(3);
                stockViewModel.CurrentPrice = rs.getDouble(4);
                stockViewModelList.add(stockViewModel);
            }
            
        } catch (Exception e) {
           
            e.printStackTrace();
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StockTransactionRepository.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return stockViewModelList;
    }
    
      public ArrayList<AnalystViewModel> GetDataForPredicate() {
        ArrayList<AnalystViewModel> analystViewModelList = new ArrayList<AnalystViewModel>();
        ResultSet rs = null;
        try {
            String selectQry = "SELECT Id, Name, CurrentPrice from Stock";
            rs = DB.fetch(selectQry);
            while (rs.next()) {
                AnalystViewModel analystViewModel = new AnalystViewModel();
                analystViewModel.Id = rs.getInt(1);
                analystViewModel.Name = rs.getString(2);
                analystViewModel.CurrentPrice = rs.getString(3);
                analystViewModelList.add(analystViewModel);
            }
            rs.close();
            for (int i = 0; i < analystViewModelList.size(); i++) {
                AnalystViewModel analystViewModel = analystViewModelList.get(i);
                List<Integer> numbers = new ArrayList<Integer>();
                String selectQry2 = "SELECT PreviousPrice from StockPriceHistory Where StockId = '" + analystViewModel.Id + "'";
                rs = DB.fetch(selectQry2);
                while (rs.next()) {
                    numbers.add(rs.getInt(1));
                }
                analystViewModel.valus = numbers;
                rs.close();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return analystViewModelList;
    }
}

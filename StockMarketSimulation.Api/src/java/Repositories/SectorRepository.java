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
import Models.StockTransactionFullViewModel;
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
public class SectorRepository {

    public boolean Create(SectorViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO Sector(Name) values ('" + vm.Name + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }

    public List<SectorViewModel> GetAllSectors() {
        ArrayList<SectorViewModel> sectorViewModelList = new ArrayList<SectorViewModel>();
        ResultSet rs = null;
        try {
            String selectQry = "SELECT Id,Name FROM Sector";
            rs = DB.fetch(selectQry);
            while (rs.next()) {
                SectorViewModel sectorViewModel = new SectorViewModel();
                sectorViewModel.Id = rs.getInt(1);
                sectorViewModel.Name = rs.getString(2);
                sectorViewModelList.add(sectorViewModel);
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StockTransactionRepository.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return sectorViewModelList;
    }
}

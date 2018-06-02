/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.SectorViewModel;

/**
 *
 * @author aliha_000
 */
public class SectorRepository {

    public boolean Create(SectorViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO Sector(Name,CurrentValue,CurrentPrice) values ('" + vm.Name + "','" + vm.CurrentValue + "','" + vm.CurrentPrice + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }
}

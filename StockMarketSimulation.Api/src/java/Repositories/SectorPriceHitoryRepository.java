/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.SectorPriceHistoryViewModel;

/**
 *
 * @author aliha_000
 */
public class SectorPriceHitoryRepository {

    public boolean Create(SectorPriceHistoryViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO SectorPriceHistory(PreviousValue,CreatedDate,SectorId,PreviousPrice) values ('" + vm.PreviousValue + "','" + vm.CreatedDate + "','" + vm.SectorId + "','" + vm.PreviousPrice + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.MarksViewModel;

/**
 *
 * @author aliha_000
 */
public class MarksRepository {

    public boolean Create(MarksViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO Marks(GameRoundId,TurnId,BrokerId) values ('" + vm.GameRoundId + "','" + vm.TurnId + "','" + vm.BrokerId + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }
}

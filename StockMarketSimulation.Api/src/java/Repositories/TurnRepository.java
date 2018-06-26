/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.TurnViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;

/**
 *
 * @author aliha_000
 */
public class TurnRepository {

    public boolean Create(TurnViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO Turn(Turn) values ('" + vm.Turn + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }

    public TurnViewModel GetCurrentTurn() {
        TurnViewModel turnViewModel = new TurnViewModel();
        ResultSet rt = null;
        try {
            String qry = "SELECT * FROM Turn WHERE Id = (SELECT MAX(Id) from turn)";
            rt = DB.fetch(qry);
            if (rt.next()) {
                turnViewModel.Id = rt.getInt(1);
                turnViewModel.Turn = rt.getInt(2);
                turnViewModel.GameRoundId = rt.getInt(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rt.close();
            } catch (SQLException ex) {
                Logger.getLogger(TurnRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return turnViewModel;
    }
}

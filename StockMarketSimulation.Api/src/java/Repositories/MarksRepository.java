/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.AnalystViewModel;
import Models.MarksResponeViewModel;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Hasnat
 */
public class MarksRepository {

    public ArrayList<MarksResponeViewModel> GetUsersMarks(int roundId) {
        ArrayList<MarksResponeViewModel> vmList = new ArrayList<MarksResponeViewModel>();
        ResultSet rs = null;

        try {
            String selectQry = "SELECT BankAccount.Id, BankAccount.Name, BankAccount.Balance from BankAccount INNER JOIN StockTransaction ON StockTransaction.BankAccountId = BankAccount.Id INNER JOIN Turn on Turn.Id = StockTransaction.TurnId INNER JOIN GameRound on GameRound.Id=Turn.GameRoundId WHERE GameRound.Id = '"+ roundId +"'";
            rs = DB.fetch(selectQry);
            while (rs.next()) {
                MarksResponeViewModel marksResponeViewModel = new MarksResponeViewModel();
                marksResponeViewModel.UserId = rs.getInt(1);
                marksResponeViewModel.Name = rs.getString(2);
                marksResponeViewModel.CurrentBankAmount = rs.getDouble(3);
                vmList.add(marksResponeViewModel);
            }
        } catch (Exception e) {
        }
        return vmList;
    }
}

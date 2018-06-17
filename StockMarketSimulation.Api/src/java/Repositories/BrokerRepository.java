/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.BrokerViewModel;
import java.sql.ResultSet;

/**
 *
 * @author aliha_000
 */
public class BrokerRepository {

    public int Create(int BankAccountId) {
        ResultSet rs = null;
        try {
            String insertQry = "INSERT INTO Broker(BankAccountId) values ('" + BankAccountId + "')";
            DB.save(insertQry);

            String selectQry = "SELECT Id FROM Broker WHERE BankAccountId='" + BankAccountId + "'";
            rs = DB.fetch(selectQry);
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {

            e.printStackTrace();
            return 0;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lakshan
 */
public class BankAccountRepository {

    public boolean Create(BankAccountViewModel vm) {
        boolean isSaved = true;
        try {
            String insertQry = "INSERT INTO BankAccount(PlayerName,AccountNumber,Balance) values ('" + vm.PlayerName + "','" + vm.AccountNumber + "','" + vm.Balance + "')";
            isSaved = DB.save(insertQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }

    public boolean Update(BankAccountViewModel vm) {
        boolean isSaved = true;
        try {
            String updateQry = "UPDATE BankAccount SET PlayerName='" + vm.PlayerName + "', AccountNumber='" + vm.AccountNumber + "', Balance='" + vm.Balance + "' WHERE Id='" + vm.Id + "'";
            isSaved = DB.save(updateQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }
        return isSaved;
    }

    public BankAccountViewModel GetById(int id) {
        BankAccountViewModel vm = new BankAccountViewModel();
        ResultSet rs = null;
        try {
            String selectQry = "SELECT * FROM BankAccount WHERE Id='" + id + "'";
            rs = DB.fetch(selectQry);

            while (rs.next()) {
                vm.Id = rs.getInt(1);
                vm.PlayerName = rs.getString(2);
                vm.AccountNumber = Integer.parseInt(rs.getString(3));
                vm.Balance = Double.parseDouble(rs.getString(4));
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(BankAccountRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vm;
    }

    public BankAccountViewModel GetByName(String name) {
        BankAccountViewModel vm = new BankAccountViewModel();
        ResultSet rs = null;
        try {
            String selectQry = "SELECT * FROM BankAccount WHERE PlayerName='" + name + "'";
            rs = DB.fetch(selectQry);

            while (rs.next()) {
                vm.Id = rs.getInt(1);
                vm.PlayerName = rs.getString(2);
                vm.AccountNumber = Integer.parseInt(rs.getString(3));
                vm.Balance = Double.parseDouble(rs.getString(4));
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(BankAccountRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vm;
    }

    public boolean Delete(int id) {
        BankAccountViewModel vm = new BankAccountViewModel();
        boolean isDelete = false;
        try {
            String deleteQry = "DELETE FROM BankAccount WHERE Id='" + id + "'";
            isDelete = DB.save(deleteQry);

        } catch (Exception e) {
            isDelete = false;
            e.printStackTrace();
        }
        return isDelete;
    }
}

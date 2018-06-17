/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.CurrentBankInfoViewModel;
import Models.LoginResponceViewModel;
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

    public BankAccountViewModel Create(BankAccountViewModel vm) {
        boolean isSaved = true;
        ResultSet rs = null;
        BankAccountViewModel bankAccountViewModel = new BankAccountViewModel();
        try {

            String insertQry = "INSERT INTO BankAccount(PlayerName,AccountNumber,Balance,UserName,Password) values ('" + vm.PlayerName + "','" + vm.AccountNumber + "','" + vm.Balance + "','" + vm.UserName + "','" + vm.Password + "')";
            isSaved = DB.save(insertQry);

            String selectQry = "SELECT * FROM BankAccount WHERE PlayerName='" + vm.PlayerName + "'";
            rs = DB.fetch(selectQry);

            while (rs.next()) {
                bankAccountViewModel.Id = rs.getInt(1);
                bankAccountViewModel.PlayerName = rs.getString(2);
                bankAccountViewModel.AccountNumber = Integer.parseInt(rs.getString(3));
                bankAccountViewModel.Balance = Double.parseDouble(rs.getString(4));
            }

        } catch (Exception e) {
            isSaved = false;
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
        return bankAccountViewModel;
    }

    public CurrentBankInfoViewModel GetCurrentBalanceInfo(int BankAccountId) {
        boolean isSaved = true;
        ResultSet rs = null;
        CurrentBankInfoViewModel currentBankInfoViewModel = new CurrentBankInfoViewModel();

        try {
            String selectQry = "SELECT Balance FROM BankAccount WHERE Id='" + BankAccountId + "'";
            rs = DB.fetch(selectQry);

            if (rs.next()) {
                currentBankInfoViewModel.CurrentBaniBalance = rs.getInt(1);
            }
            rs.close();

            currentBankInfoViewModel.TotalBoughtItem = 0;
            currentBankInfoViewModel.TotalSoldItem = 0;

            String selectQry2 = "SELECT COUNT(*) FROM StockTransaction WHERE BankAccountId='" + BankAccountId + "' AND Type='1'";
            rs = DB.fetch(selectQry2);
            while (rs.next()) {
                currentBankInfoViewModel.TotalSoldItem = rs.getInt(1);
            }
            rs.close();

            String selectQry3 = "SELECT COUNT(*) FROM StockTransaction WHERE BankAccountId='" + BankAccountId + "' AND Type='2'";
            rs = DB.fetch(selectQry3);
            while (rs.next()) {
                currentBankInfoViewModel.TotalBoughtItem = rs.getInt(1);
            }
            rs.close();

        } catch (Exception e) {
            isSaved = false;
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

        return currentBankInfoViewModel;
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
                vm.UserName = (rs.getString(5));
                vm.Password = (rs.getString(6));
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
                vm.UserName = (rs.getString(5));
                vm.Password = (rs.getString(6));
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

    public BankAccountViewModel GetByUserName(String userName) {
        BankAccountViewModel vm = new BankAccountViewModel();
        ResultSet rs = null;
        try {
            String selectQry = "SELECT * FROM BankAccount WHERE UserName='" + userName + "'";
            rs = DB.fetch(selectQry);

            while (rs.next()) {
                vm.Id = rs.getInt(1);
                vm.PlayerName = rs.getString(2);
                vm.AccountNumber = Integer.parseInt(rs.getString(3));
                vm.Balance = Double.parseDouble(rs.getString(4));
                vm.UserName = (rs.getString(5));
                vm.Password = (rs.getString(6));
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

    public LoginResponceViewModel GetUserInfo(String userName) {
        LoginResponceViewModel vm = new LoginResponceViewModel();
        ResultSet rs = null;
        try {
            String selectQry = "SELECT BankAccount.Id,Broker.Id FROM BankAccount Inner Join Broker ON BankAccount.Id = Broker.BankAccountId  WHERE UserName='" + userName + "'";
            rs = DB.fetch(selectQry);

            while (rs.next()) {
                vm.BankAccountId = rs.getInt(1);
                vm.BrokerId = rs.getString(2);
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

    public boolean Login(String userName, String password) {
        ResultSet rs = null;
        try {
            String selectQry = "SELECT * FROM BankAccount WHERE UserName='" + userName + "' AND Password='" + password + "'";
            rs = DB.fetch(selectQry);

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(BankAccountRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public int GetMaxAccountNumber() {
        ResultSet rs = null;
        try {
            String selectQry = "SELECT MAX(AccountNumber) FROM BankAccount";
            rs = DB.fetch(selectQry);

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 1000000;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(BankAccountRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.StartGameResponceViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lakshan
 */
public class StartGameRepository {

    public StartGameResponceViewModel start() {
        boolean isSaved = true;
        ResultSet rs = null;
        StartGameResponceViewModel startGameResponceViewModel = new StartGameResponceViewModel();
        try {

            String selectQry = "SELECT Id, Round FROM GameRound WHERE Round=(SELECT MAX(Round) From GameRound)";
            rs = DB.fetch(selectQry);

            if (rs.next()) {
                int roundId = rs.getInt(1);
                rs.close();
                String selectQry6 = "SELECT MAX(Round) FROM GameRound";
                rs = DB.fetch(selectQry6);
                int maxRound = rs.getInt(1) + 1;
                rs.close();

                String selectQry2 = "SELECT MAX(Turn) FROM Turn WHERE GameRoundId='" + roundId + "'";
                rs = DB.fetch(selectQry2);
                if (rs.getInt(1) == 30) {
                    rs.close();

                    Date today = new Date();
                    today.setHours(0);
                    String insertQry = "INSERT INTO GameRound(Date, Round) values ('" + today + "','" + maxRound + "')";
                    isSaved = DB.save(insertQry);

                    String qry = "SELECT Id FROM GameRound WHERE Round='" + maxRound + "'";
                    rs = DB.fetch(qry);

                    startGameResponceViewModel.Round = maxRound;
                    startGameResponceViewModel.RoundId = rs.getInt(1);
                    rs.close();

                    String qry2 = "INSERT INTO Turn(Turn, GameRoundId) values ('1','" + startGameResponceViewModel.RoundId + "')";
                    isSaved = DB.save(qry2);

                    String qry3 = "SELECT Id FROM Turn WHERE GameRoundId='" + startGameResponceViewModel.RoundId + "'";
                    rs = DB.fetch(qry3);

                    startGameResponceViewModel.Turn = 1;
                    startGameResponceViewModel.TurnId = rs.getInt(1);
                    rs.close();
                } else {
                    rs.close();
                    String selectQry3 = "SELECT MAX(Turn) FROM Turn WHERE GameRoundId='" + roundId + "'";
                    rs = DB.fetch(selectQry3);
                    startGameResponceViewModel.Round = 1;
                    startGameResponceViewModel.RoundId = roundId;
                    startGameResponceViewModel.Turn = 1;
                    startGameResponceViewModel.TurnId = rs.getInt(1);
                    rs.close();
                }

            } else {
                rs.close();

                Date today = new Date();
                today.setHours(0);
                String insertQry = "INSERT INTO GameRound(Date, Round) values ('" + today + "','" + 1 + "')";
                isSaved = DB.save(insertQry);

                String qry = "SELECT Id FROM GameRound WHERE Round='1'";
                rs = DB.fetch(selectQry);

                startGameResponceViewModel.Round = 1;
                startGameResponceViewModel.RoundId = rs.getInt(1);
                rs.close();

                String qry2 = "INSERT INTO Turn(Turn, GameRoundId) values ('1','" + startGameResponceViewModel.RoundId + "')";
                isSaved = DB.save(qry2);

                String qry3 = "SELECT Id FROM Turn WHERE GameRoundId='" + startGameResponceViewModel.RoundId + "'";
                rs = DB.fetch(qry3);

                
                startGameResponceViewModel.Turn = 1;
                startGameResponceViewModel.TurnId = rs.getInt(1);
                rs.close();
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
        return startGameResponceViewModel;
    }
}

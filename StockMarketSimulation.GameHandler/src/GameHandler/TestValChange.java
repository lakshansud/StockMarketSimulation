/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameHandler;

import DatabaseConnection.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hasnat
 */
public class TestValChange {

    /**
     * @return
     */
    public int graphgeneratortrendvalues() //check from database values for increment or decrement
    {
        int randomInt1 = 0;
        Random randomGenerator = new Random();
        for (int idx = 1; idx <= 4; ++idx) {
            randomInt1 = randomGenerator.nextInt(4);
        }
        int trend = 0;

        switch (randomInt1) {
            case 1:
                trend = 1;
                break;
            case 2:
                trend = -1;
                break;
            case 3:
                trend = 0;
                break;
            case 4:
                trend = 0;

        }
        return trend;
    }

    public int getLimit(String type) {
        int limit = 0;
        switch (type) {
            case "random":
                limit = 2;
                break;
            case "sector":
                limit = 3;
                break;
            case "market":
                limit = 3;
                break;
        }
        return limit;

    }

    public TestValChange(String type, int turn, int stockId, double currentValue) {

        int limit = this.getLimit(type);

        int trend = this.graphgeneratortrendvalues();


        boolean isSaved = true;

        ///////////////price history table  ekata save wennath oni
        if (trend > 0) {

            if (currentValue >= limit) {

                try {
                    String updateQry = "UPDATE Stock SET CurrentValue='" + 0 + "', WHERE Id='" + stockId + "'";
                    isSaved = DB.save(updateQry);
                } catch (Exception e) {
                    isSaved = false;
                    e.printStackTrace();
                }

            } else {

                try {
                    String updateQry = "UPDATE Stock SET CurrentValue='" + currentValue + trend + "', WHERE Id='" + stockId + "'";
                    isSaved = DB.save(updateQry);
                } catch (Exception e) {
                    isSaved = false;
                    e.printStackTrace();
                }

            }

        } else {

            int negetiveLimit = limit * -1;
            if (currentValue < negetiveLimit) {

                try {
                    String updateQry = "UPDATE Stock SET CurrentValue='" + 0 + "', WHERE Id='" +  stockId + "'";
                    isSaved = DB.save(updateQry);
                } catch (Exception e) {
                    isSaved = false;
                    e.printStackTrace();
                }

            } else {

                try {
                    String updateQry = "UPDATE Stock SET CurrentValue='" + currentValue + trend + "', WHERE Id='" + stockId + "'";
                    isSaved = DB.save(updateQry);
                } catch (Exception e) {
                    isSaved = false;
                    e.printStackTrace();
                }

            }

        }

    }

    public StockViewModel GetById(int id) {
        StockViewModel vm = new StockViewModel();
        ResultSet rs = null;
        try {
            String selectQry = "SELECT * FROM Stock WHERE Id='" + id + "'";
            rs = DB.fetch(selectQry);

            while (rs.next()) {
                vm.Id = rs.getInt(1);
                vm.Name = rs.getString(2);
                vm.CurrentPrice = Double.parseDouble(rs.getString(3));
                vm.CurrentValue = rs.getInt(4);
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(TestValChange.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vm;
    }

}

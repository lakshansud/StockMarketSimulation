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

/**
 *
 * @author Hasnat
 */
public class ValueChange {

    /**
     * @return
     */
    public int graphgeneratortrendvalues() //return +1,-1,0,0
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

    public int getLimit(String type) {  //return limit for trends
        int limit = 0;
        switch (type) {
            case "random_value":
                limit = 2;
                break;
            case "sector_value":
                limit = 3;
                break;
            case "market_value":
                limit = 3;
                break;
        }
        return limit;

    }

    public int TestValChange(String type, int turn, int stockId) {

        int limit = this.getLimit(type);
        int trend = this.graphgeneratortrendvalues();
//        int previousValue = this.getPreviousTrendValue(type, turn, stockId);
int previousValue = 1;

        int changedValue = previousValue + trend;

        if (changedValue > 0) {
            if (changedValue > limit) {
                changedValue = limit;
            }
        } else {
            if (changedValue < (limit * -1)) {
                changedValue = (limit * -1);
            }
        }

        return changedValue;

    }

    public int getPreviousTrendValue(String type, int turn, int stockId) {

        int previousValue = 0;
        int typeColumnNumber = this.getTypeColumnnumber(type);
        ResultSet rs = null;
        try {
            if (turn > 0) {
                String selectQuery = "SELECT " + type + " FROM StockPriceHistory WHERE turn = " + (turn - 1) + " AND stock_id = " + stockId;
                rs = DB.fetch(selectQuery);
                previousValue = rs.getInt(typeColumnNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                
            }
        }
        
        return previousValue;

    }
    
    private int getTypeColumnnumber (String type) {
    
        int column = 0;
        switch (type) {
            case "random_value":
                column = 3;
                break;
            case "sector_value":
                column = 5;
                break;
            case "market_value":
                column = 4;
                break;
        }
        return column;
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameHandler;

import DatabaseConnection.DB;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hasnatrs
 */
public class PriceChangeStock {


    public void ChangeValue(int turn, int stockId) {


        double previousPrice = this.getPreviousPrice(turn, stockId);


        ValueChange vc = new ValueChange();
        

        int random_value = vc.TestValChange("random_value", turn, stockId);
        int sector_value = vc.TestValChange("sector_value", turn, stockId);
        int market_value = vc.TestValChange("market_value", turn, stockId);
        
        LocalDate date = java.time.LocalDate.now();
         
        int event_value = 0;
        
        if (turn % 10 == 0) {
            ReturnEventDetailsViewmodel event = new ReturnEventDetailsViewmodel();
            EventGenerator eg = new EventGenerator();
            event = eg.getstockeventtype();
            event_value = event.range;
        } else {
            event_value = 0;
        }
        

        System.out.println(random_value + " @@@ " +  sector_value + " @@@ " + market_value+ " @@@ " + event_value);
        int precentage = random_value + sector_value + market_value;
        double result = this.generateCurrentPrice(precentage, (int) previousPrice);

        String insertQuery = "INSERT INTO StockPriceHistory (turn, random_value, sector_value, market_value, event_value, StockId, price, CreateDate)" + " VALUES (" + turn + "," + random_value + "," + sector_value + "," + market_value + "," + event_value + "," + stockId + "," + result +"," + date.toString() + " )";
        DB.save(insertQuery);
        
        String updateQuery = "UPDATE Stock SET CurrentPrice='" + result + "' WHERE Id = " + stockId + "" ;
        DB.save(updateQuery);


    }
    
    public double getPreviousPrice(int turn, int stockId) {

        double previousPrice = 0;

        ResultSet rs = null;
       
        try {
            if (turn > 1) {
                String selectQuery = "SELECT price FROM StockPriceHistory WHERE turn = " + (turn - 1) + " AND StockId = " + stockId;
                rs = DB.fetch(selectQuery);    
                previousPrice = rs.getDouble("price");

            } else {
                String selectQuery = "SELECT * FROM stock WHERE Id = " + stockId;
                rs = DB.fetch(selectQuery);    
                previousPrice = rs.getDouble("CurrentPrice");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(ValueChange.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return previousPrice;

    }
    
    private double generateCurrentPrice(int precentage, double previousPrice) {

        double currentPrice = 0;
        if (precentage < 1) { 
            currentPrice = previousPrice;
        } else {
            currentPrice = (previousPrice/100) * (100 + precentage);
        }
//        System.out.println(precentage + " __ " + previousPrice + " __ " + currentPrice);
        return currentPrice;
    }

    public List getStockIds() {
        
        int previousPrice = 0;
        List<Integer> ids = new ArrayList<Integer>();
        
        ResultSet rs = null;
        try {
            String selectQuery = "SELECT Id FROM Stock";
            rs = DB.fetch(selectQuery);         
            
            while (rs.next()) {
                ids.add(rs.getInt(1));
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

        return ids;
    
    }
}

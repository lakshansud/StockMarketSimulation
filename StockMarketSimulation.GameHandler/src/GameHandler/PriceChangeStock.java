/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameHandler;

import DatabaseConnection.DB;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hasnatrs
 */
public class PriceChangeStock {

    //per one turn
    public int updatestock(int changevalue1, int changevalue2, int changevalue3) {

        ReturnEventDetailsViewmodel returnEventDetailsViewmodel = new ReturnEventDetailsViewmodel();
        StockViewModel vm = new StockViewModel();

        int res1 = returnEventDetailsViewmodel.range;
        int res2 = changevalue1;
        int res3 = changevalue1;
        int res4 = changevalue3;

        int result = (res1 + res2 + res3 + res4) / 100;

        return result;
    }

  /**  public StockViewModel GetById(int id, String name) {
        StockViewModel vm = new StockViewModel();
        ResultSet rs = null;
        try {
            String selectQry = "SELECT * FROM Stock WHERE Id='" + id + "' AND Name='" + name + "' ";
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
                Logger.getLogger(ValueChange.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vm;
    }
    **/

    public void ChangeValue(int turn, int stockId) {


        double previousPrice = this.getPreviousPrice(turn, stockId);


        ValueChange vc = new ValueChange();
        

        int random_value = vc.TestValChange("random_value", turn, stockId);
        int sector_value = vc.TestValChange("sector_value", turn, stockId);
        int market_value = vc.TestValChange("market_value", turn, stockId);
        int event_value = 0;

//                System.out.println(random_value + " @@@ " +  sector_value + " @@@ " + market_value);
        int precentage = random_value + sector_value + market_value;
        double result = this.generateCurrentPrice(precentage, (int) previousPrice);

        String insertQuery = "INSERT INTO StockPriceHistory (turn, random_value, sector_value, market_value, event_value, stock_id, price)" + " VALUES (" + turn + "," + random_value + "," + sector_value + "," + market_value + "," + event_value + "," + stockId + "," + result +")";
        DB.save(insertQuery);


    }
    
    public double getPreviousPrice(int turn, int stockId) {

        double previousPrice = 0;

        ResultSet rs = null;
       
        try {
            if (turn > 1) {
                String selectQuery = "SELECT price FROM StockPriceHistory WHERE turn = " + (turn - 1) + " AND stock_id = " + stockId;
                rs = DB.fetch(selectQuery);    
                previousPrice = rs.getDouble("price");

            } else {
                String selectQuery = "SELECT CurrentPrice FROM stock WHERE Id = " + stockId;
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

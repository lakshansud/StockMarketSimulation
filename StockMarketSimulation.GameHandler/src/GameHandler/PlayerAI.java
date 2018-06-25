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
public class PlayerAI {
    
    
    public void botPlayerInteraction() {
        
        Random randomGenerator = new Random();
        int randomTurn = randomGenerator.nextInt(30) + 1;
        int randomStockId = randomGenerator.nextInt(25) + 1;
        int randomType = randomGenerator.nextInt(2) + 1;
        int randomQunatity = randomGenerator.nextInt(10) + 1;
        double stockPrice = this.getStockPrice(randomStockId);
        double price = stockPrice*randomQunatity;

        
        String insertQuery = "INSERT INTO StockTransaction (Price,Type,TurnId,BankAccountId,StockId,Quantity)" + " VALUES ("+price+","+ randomType +","+ randomTurn +",6,"+randomStockId+","+randomQunatity+")";
        DB.save(insertQuery);
    
    }
    
    private double getStockPrice(int stockId){
        
        ResultSet rs = null;
        double currentPrice = 0;
        
        String selectQuery = "SELECT CurrentPrice FROM stock WHERE Id = " + stockId;
        rs = DB.fetch(selectQuery);    
        try {
            currentPrice =  rs.getDouble("CurrentPrice");
        } catch (SQLException ex) {
            Logger.getLogger(PlayerAI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentPrice;
        
    }
    
   // select id from stock and put that to integer array
 
    
       

//randomly selct an id

//random buy/sell from that id
    
}

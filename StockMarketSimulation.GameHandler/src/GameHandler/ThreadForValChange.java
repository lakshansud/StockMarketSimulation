/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameHandler;

import DatabaseConnection.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hasnat
 */
public class ThreadForValChange extends Thread {

    private int threadNo;
    int isttrue = 1;

    public ThreadForValChange(int no) {
        threadNo = no;
    }

    @Override
    public void run() {

//        try {
//            Thread.sleep(50);
        //call analyst prediction here
        //update stock prices
        PriceChangeStock pcs = new PriceChangeStock();
        List<Integer> ids = pcs.getStockIds();
//          

        for (int l = 0; l < ids.size(); l++) {
            pcs.ChangeValue(threadNo, l + 1);
        }

        LocalDate date = java.time.LocalDate.now();
        isttrue = 1;
        if (isttrue == 1) {
            String insertQuery = "INSERT INTO GameRound (Date,Round)" + " VALUES (" + date.toString() + "," + 1 + isttrue + " )";
            DB.save(insertQuery);
        }

        //update function here
//
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ThreadForValChange.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}

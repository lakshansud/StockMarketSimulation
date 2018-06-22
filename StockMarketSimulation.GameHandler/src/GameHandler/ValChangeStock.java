/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameHandler;

import DatabaseConnection.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hasnat
 */
public class ValChangeStock {

    //per one turn
    public int updatestock() {
        //res = eventgenerator eken ena range value + testvalchange eken ena , values table ekata save wena value ekai ,::saved by type = random/sector /market

        ReturnEventDetailsViewmodel returnEventDetailsViewmodel = new ReturnEventDetailsViewmodel();
        StockViewModel vm = new StockViewModel();

        //call TestValChange 3 times to get each current value for random/sector/market
        int res1 = returnEventDetailsViewmodel.range;
        int res2 = vm.CurrentValue;
        int res3 = vm.CurrentValue;
        int res4 = vm.CurrentValue;

        // result=res/100
        int result = (res1 + res2 + res3 + res4) / 100;

        return result;
    }
    //result*selected stock price

    public StockViewModel GetById(int id, String name) {
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
                Logger.getLogger(TestValChange.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vm;
    }

    public void update() {

        ///////////////price history table  ekata save wennath oni////////////////////////
        StockViewModel vm = new StockViewModel();
        boolean isSaved = true;

        int result = this.updatestock();

        try {
            String updateQry = "UPDATE Stock SET CurrentPrice='" + result * vm.CurrentPrice + "', WHERE Id='" + vm.Id + "'";
            isSaved = DB.save(updateQry);
        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        }

    }

    //valchangesector.java , same code as here only DB code is different ,and test val change code also should be optimised for sector table
}

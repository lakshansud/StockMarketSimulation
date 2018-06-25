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
public class ActiveUsers {
    
        public static boolean checkActivatedUsers() {
        ResultSet rs = null;
        boolean result = false;

        String selectQuery = "SELECT * FROM ActiveUsers";
        rs = DB.fetch(selectQuery);

        try {
            while (rs.next()) {
                if (rs.getBoolean("IsActive")) {
                    result = rs.getBoolean("IsActive");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlayerAI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void deactivateAllUsers() {
        ResultSet rs = null;

        String selectQuery = "SELECT * FROM ActiveUsers";
        String updateQuery = "UPDATE ActiveUsers SET IsActive='" + 0 + "'";
        rs = DB.fetch(selectQuery);

        try {
            while (rs.next()) {
                DB.save(updateQuery);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlayerAI.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
    
}

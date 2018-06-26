/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author lakshan
 */
public class DB {

    //static Connection con = null;
    public static ResultSet res;
    public static Connection con;

    public static Connection geCon() throws Exception {


        String url = "jdbc:sqlite:F:\\4th Year Lectures\\StockMarketSimulation\\StockMarketSimulation\\StockMarketSimulation.Api\\StockSimulation.db";
        Class.forName("org.sqlite.JDBC").newInstance();
        con = (Connection) DriverManager.getConnection(url);

        return con;
    }

    public static ResultSet fetch(String sql) {
        String workingDir = System.getProperty("user.dir");
        try {
            if (con == null) {
                geCon();
            }

            res = (ResultSet) con.createStatement().executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clear();
        }

        return res;
    }

    public static boolean save(String sql) {
        boolean b = true;
        try {
            //res.close();
            if (con == null) {
                geCon();
            }
            con.createStatement().executeUpdate(sql);
            clear();
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
        } finally {
            clear();

        }

        return b;

    }

    public static void clear() {
        System.gc();
    }
}
